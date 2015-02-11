package com.iiijiaban.hairstyle.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;








import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.etsy.android.grid.StaggeredGridView;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.iiijiaban.hairstyle.ui.FoldingCirclesDrawable;
import com.iiijiaban.hairstyle.ui.LocalImageDetailActivity;
import com.iiijiaban.hairstyle.utils.WidgetUtils;
import com.iiijiaban.hairstyle.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class LocalimgActivity extends Activity {
	
	private StaggeredGridView sgv;
	private DisplayImageOptions options;
	public ImageLoader loader;
	private Madapter adapter;
	private ArrayList<String> adapterlists;
	private RelativeLayout r;
	private boolean candelete = false;
	private ProgressBar pbloading;
	private String localpicturepath = Environment.getExternalStorageDirectory()	+ "/Hairstyle/download/";
	public SparseArray<Double> common_sPositionHeightRatios;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loader = ImageLoader.getInstance();
		adapterlists = new ArrayList<String>();
		common_sPositionHeightRatios = new SparseArray<Double>();
		setContentView(R.layout.staggergrid);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		String title = getIntent().getStringExtra("kind");
		pbloading = (ProgressBar) findViewById(R.id.loadingprogress);
		pbloading.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(this).build());
		if(title.equals("")){
			title = "Downloaded";
		}
		getActionBar().setTitle(title);
		sgv = (StaggeredGridView)findViewById(R.id.staggeredGridView1);
		sgv.setPadding(15, 0, 15, 0);
		sgv.setSelector(R.drawable.griditemselector);
		sgv.setDrawSelectorOnTop(true);
		sgv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				if (candelete) {
					candelete = false;
					adapter.notifyDataSetChanged();
				} else {
					DynamicHeightImageView imageview = (DynamicHeightImageView) view.findViewById(R.id.fuckimage);
					WidgetUtils.setImageColor(imageview);
					Intent intent = new Intent(LocalimgActivity.this,LocalImageDetailActivity.class);
					intent.putExtra("imagePosition", position);
					startActivity(intent);
				}
			}
		});

		sgv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO ³¤°´É¾³ý
				if (candelete == false) {
					candelete = true;
					adapter.notifyDataSetChanged();
					return true;
				} else {
					return false;
				}
			}
		});
		options = new DisplayImageOptions.Builder()
				.displayer(new RoundedBitmapDisplayer(10))
				.cacheInMemory(true)
				.resetViewBeforeLoading(true)
				.cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY)
				.build();
		
		
		
		
		adapter = new Madapter(adapterlists);
		setData(); 
		sgv.setAdapter(adapter);
	}
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.staggergrid, container, false);
//		
//		return view; 
//	}

	private class Madapter extends BaseAdapter { 
		private ArrayList<String> duitangs;
		private class Viewholder {
			public TextView text;
			public DynamicHeightImageView image;
			public ImageView deleteImage;
			public FrameLayout frame; 
		}

		public void updatedata(List<String> result) {
			this.duitangs = (ArrayList<String>) result;
			this.notifyDataSetChanged();
		}

		public Madapter(List<String> result) {
			this.duitangs = (ArrayList<String>) result;

		}

		@Override
		public int getCount() {
			return duitangs.size();
		}

		@Override
		public Object getItem(int position) {
			return duitangs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,ViewGroup parent) {
			View view = convertView;
			final Viewholder holder;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_stageredview_local, null);
				holder = new Viewholder();
				holder.image = (DynamicHeightImageView) view.findViewById(R.id.fuckimage);
				holder.deleteImage = (ImageView) view.findViewById(R.id.delete);
				holder.frame = (FrameLayout) view.findViewById(R.id.backgrounddelete);
				view.setTag(holder);
			} else
				holder = (Viewholder) view.getTag();
			if (candelete) {
				holder.frame.setMinimumHeight(view.getMeasuredHeight());
				holder.frame.setVisibility(View.VISIBLE);
			} else {
				holder.frame.setMinimumHeight(view.getMeasuredHeight());
				holder.frame.setVisibility(View.GONE);
			} 
			holder.deleteImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO µã»÷É¾³ý
					File file = new File((String) duitangs.get(position));
					file.delete();
					duitangs.remove(position);
					candelete = false;
					notifyDataSetChanged();
				}
			});
			// holder.text.setText(duitangs.get(position).getMsg());
			String url = "file:///" + duitangs.get(position);
			
			loader.displayImage(url, holder.image, options,
					new ImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
						}
						@Override
						public void onLoadingCancelled(String imageUri,
								View view) {
						}
						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
							// TODO Auto-generated method stub
						}
						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							// TODO Auto-generated method stub
							DynamicHeightImageView myview = (DynamicHeightImageView) view;
							double ratio = common_sPositionHeightRatios.get(
									position, 0.0);
							if (ratio == 0) {
								ratio = (double) loadedImage.getHeight()
										/ (double) loadedImage.getWidth();
								if (ratio > 4) {
									ratio = 4;
								}
								myview.setHeightRatio(ratio);
								common_sPositionHeightRatios.append(position,
										ratio);
							}
						}
					});
			double ratio = common_sPositionHeightRatios.get(position, 0.0);
			if (ratio != 0) {
				holder.image.setHeightRatio(common_sPositionHeightRatios.get(position, 0.0));
			}
			return view;
		}
	}
	public void setData() {
		
		new AsyncTask<Void,Integer, ArrayList<String>>() {

			@Override
			protected ArrayList<String> doInBackground(Void... params) {
				List<String> list = new ArrayList<String>();
				File filedir = new File(localpicturepath);
				if (!filedir.exists()) {
					filedir.mkdirs();
				}
				File[] files = filedir.listFiles();
				for (File file : files) {
					try {
						String path1 = file.getPath();
						list.add(path1);
					} catch (Exception e) {
						System.out.println(e.getMessage().toString());
					}
				}
				return (ArrayList<String>) list;
			} 
			@Override
			protected void onPostExecute(ArrayList<String> result) { 
				super.onPostExecute(result);
				pbloading.setVisibility(View.GONE);
				adapter.updatedata(result);
			}
		}.execute(); 
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
	@Override
	protected void onResume() {
		super.onResume();
		setData();
	}
}
