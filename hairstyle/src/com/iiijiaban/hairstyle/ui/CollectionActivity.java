package com.iiijiaban.hairstyle.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.iiijiaban.com.db.DbHelper;
import com.iiijiaban.hairstyle.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class CollectionActivity extends Activity{
	public List<Map<String, String>> lists ;// 装有图片的地址 描述
	private StaggeredGridView sgv;
	private DisplayImageOptions options;
	public ImageLoader loader;
	private MyAdapter adapter;
	private DbHelper dh;
	public SparseArray<Double> common_sPositionHeightRatios;
	private ProgressBar pbloading;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staggergrid);
		loader = ImageLoader.getInstance();
		dh = new DbHelper(this);
		lists = new ArrayList<Map<String,String>>();
		common_sPositionHeightRatios = new SparseArray<Double>();
		options = new DisplayImageOptions.Builder() 
		.displayer(new RoundedBitmapDisplayer(10)) 
		.cacheInMemory(true)
		.resetViewBeforeLoading()
		.cacheOnDisk(true)
		.build();
		pbloading = (ProgressBar) findViewById(R.id.loadingprogress);
		pbloading.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(this).build());
		initView(); 
	} 
	private void initView() {
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		String title = getIntent().getStringExtra("kind");
		if (title.equals("")) {
			title = "Collections";
		}
		getActionBar().setTitle(title);
		sgv = (StaggeredGridView) findViewById(R.id.staggeredGridView1);
		sgv.setPadding(15, 0, 15, 0);
//		sgv.setSelector(R.drawable.griditemselector);
		sgv.setDrawSelectorOnTop(true); 
		LoadData();
		sgv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent();
				intent.putExtra("url", lists.get(position).get("isrc"));
				intent.putExtra("msg", "Collections");
				intent.setClass(getApplicationContext(),CollectionDetailActivity.class);
				startActivity(intent);
			}
		});
		sgv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
				AlertDialog dialog = new AlertDialog.Builder(CollectionActivity.this).setMessage(getResources().getString(R.string.delete_pic)).setNegativeButton(getResources().getString(R.string.cancel),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int which) {
									}
								})
						.setPositiveButton(getResources().getString(R.string.ok),new DialogInterface.OnClickListener() { 
									@Override
									public void onClick(DialogInterface dialog,int which) {
										dh.del(lists.get(position).get("isrc")); 
										LoadData(); 
									}
								}).create();  
				dialog.show();
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = new MenuInflater(getApplicationContext());
		inflater.inflate(R.menu.collection, menu);
		MenuItem clear = (MenuItem) menu.findItem(R.id.clearall);
		clear.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AlertDialog log = new AlertDialog.Builder(CollectionActivity.this)
						.setMessage(getResources().getString(R.string.delete_all))
						.setNegativeButton(getResources().getString(R.string.cancel),new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int which) {
									}
								})
						.setPositiveButton(
								getResources().getString(R.string.ok),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dh.delAll(); 
										LoadData();

									}
								}).create();
				log.show();
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void LoadData() { 
		new AsyncTask<Void, Void, ArrayList<Map<String,String>>>() {

			@Override
			protected ArrayList<Map<String, String>> doInBackground(Void... params) {
				ArrayList<Map<String, String>> list = new ArrayList<Map<String,String>>();
				Cursor c = dh.query();  
				while (c.moveToNext()) {
					Map<String, String> map = new HashMap<String, String>();
					String s = c.getString(c.getColumnIndex("isrc"));
					String s1 = c.getString(c.getColumnIndex("msg"));
					map.put("isrc", s);
					map.put("msg", s1);
					list.add(map);
				}
				c.close(); 
				return list;
			}
			@Override
			protected void onPostExecute(ArrayList<Map<String, String>> result) { 
				super.onPostExecute(result);
				lists = result;
				adapter = new MyAdapter(lists);
				sgv.setAdapter(adapter);
				pbloading.setVisibility(View.GONE);
				adapter.updatedata((ArrayList<Map<String,String>>)lists);
			}
		}.execute(); 
	}

	public class MyAdapter extends BaseAdapter {
		private ArrayList<Map<String, String>> ts;

		public MyAdapter(List<Map<String, String>> list1) {
			super();
			this.ts = (ArrayList<Map<String, String>>) list1;
		}

		@Override
		public int getCount() {
			return ts.size();
		}

		@Override
		public Object getItem(int position) {
			return ts.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,ViewGroup parent) {
			final Viewholder holder;
			if (convertView == null) {
				convertView =getLayoutInflater().inflate(R.layout.fuckgrid, null);
				holder = new Viewholder();
				holder.image = (DynamicHeightImageView) convertView.findViewById(R.id.fuckimage);
//				holder.text = (TextView) convertView.findViewById(R.id.fucktext);
				convertView.setTag(holder);
			} else
				holder = (Viewholder) convertView.getTag();
//			holder.text.setText(ts.get(position).get("msg"));
			loader.displayImage(ts.get(position).get("isrc"), holder.image,options, new ImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
						}
						@Override
						public void onLoadingFailed(String imageUri, View view,	FailReason failReason) {
						}
						@Override
						public void onLoadingComplete(String imageUri,View view, Bitmap loadedImage) {
							// TODO 获取图片的位置，并设置宽高比
							DynamicHeightImageView myview = (DynamicHeightImageView) view;
							double newratio = common_sPositionHeightRatios.get(position, 0.0);
							if (newratio == 0) {
								newratio = (double) loadedImage.getHeight()/ (double) loadedImage.getWidth();
								if (newratio > 3.0) {
									newratio = 3.0;
								}
								myview.setHeightRatio(newratio);
								common_sPositionHeightRatios.append(position,newratio);
							} 
						}

						@Override
						public void onLoadingCancelled(String imageUri,
								View view) {
						}
					});
			double ratio = common_sPositionHeightRatios.get(position, 0.0);
			if (ratio != 0) {
				holder.image.setHeightRatio(common_sPositionHeightRatios.get(position, 0.0));
			}
			return convertView;
		}
		public void updatedata(ArrayList<Map<String, String>> result) {
			this.ts = result;
			notifyDataSetChanged();
		}

		private class Viewholder {
//			public TextView text;
			public DynamicHeightImageView image;
			public ProgressBar bar;
		} 
	} 
	
	@Override
	protected void onResume() {
		super.onResume();
		LoadData();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(dh!=null){
			dh.close();
		} 
	}
}
