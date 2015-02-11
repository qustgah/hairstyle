package com.iiijiaban.hairstyle.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.etsy.android.grid.StaggeredGridView;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.iiijiaban.hairstyle.R;
import com.iiijiaban.hairstyle.beans.DuitangImageBean;
import com.iiijiaban.hairstyle.dao.PictureDao;
import com.iiijiaban.hairstyle.ui.FoldingCirclesDrawable;
import com.iiijiaban.hairstyle.ui.ImageDetailActivity;
import com.ijiaban.actionbar.pulltorefreshlib.ActionBarPullToRefresh;
import com.ijiaban.actionbar.pulltorefreshlib.OnRefreshListener;
import com.ijiaban.actionbar.pulltorefreshlib.PullToRefreshLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class TestFragment2 extends Fragment implements OnRefreshListener {
	private PullToRefreshLayout ptrl;
	private StaggeredGridView sgv; //瀑布流
	private DisplayImageOptions options;
	public ImageLoader loader;
	private int pageindex = 1;
	private Madapter2 adapter2;
	private ProgressBar bar;
	DynamicHeightImageView imageview;
	Drawable mydrawable;
	private ArrayList<DuitangImageBean> images;
	public static final SparseArray<Double> common_sPositionHeightRatios = new SparseArray<Double>();
	private String kindname;
	private String EName;
	
	private ProgressBar pbloading;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.staggergrid, container, false);
		loader = ImageLoader.getInstance();
		kindname=getActivity().getIntent().getStringExtra("kind");
		if(kindname.equals("")){
			switch (getActivity().getIntent().getIntExtra("kindid", 2)) { 
			case 2:
				kindname = "马尾";
				EName = "Horsetail";
				break;
			case 3:
				kindname = "卷发";
				EName = "Curls";
				break;
			case 4:
				kindname = "短发";
				EName = "Short Hair";
				break;
			case 5:
				kindname = "染发";
				EName = "Dye Hair";
				break;
			case 6:
				kindname = "编发";
				EName = "Braided Hair";
				break;
			case 7:
				kindname = "长发";
				EName = "Long Hair";
				break; 
			default:
				break;
			} 
		}
		sgv = (StaggeredGridView) v.findViewById(R.id.staggeredGridView1);
		sgv.setPadding(15, 0, 15, 0);
		pbloading=(ProgressBar)v.findViewById(R.id.loadingprogress);
		pbloading.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(getActivity()).build());
		sgv.setSelector(R.drawable.griditemselector);
		sgv.setDrawSelectorOnTop(true);
		bar = new ProgressBar(getActivity()); 
		bar.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(getActivity()).build());
		ptrl = (PullToRefreshLayout) v.findViewById(R.id.pulltorefresh);
		sgv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) { 
				Intent intend = new Intent();
				intend.putExtra("url", images.get(position).getThumburl());
				intend.putExtra("msg",EName);
				intend.setClass(getActivity(), ImageDetailActivity.class);
				startActivity(intend);
			}
		});
		sgv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem + visibleItemCount == totalItemCount&totalItemCount!=0) {
					pageindex += 1;
					ContentTask2 task = new ContentTask2(getActivity());
					task.execute(String.valueOf(pageindex),kindname);
				}
			}
		});
		images=new ArrayList<DuitangImageBean>();
		options = new DisplayImageOptions.Builder() 
				.displayer(new RoundedBitmapDisplayer(10)) 
				.cacheInMemory(true)
				.resetViewBeforeLoading()
				.cacheOnDisk(true)
				.build();
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewGroup viewGroup = (ViewGroup) view;
		ptrl = new PullToRefreshLayout(viewGroup.getContext());
		ActionBarPullToRefresh.from(getActivity())
				.insertLayoutInto(viewGroup)
				.theseChildrenArePullable(R.id.staggeredGridView1)
				.listener(this).setup(ptrl);
		ContentTask2 task=new ContentTask2(getActivity());
		task.execute(String.valueOf(pageindex),kindname);
	} 
	@Override
	public void onRefreshStarted(View view) {
		pageindex += 1;
		ContentTask2 task = new ContentTask2(getActivity());
		task.execute(String.valueOf(pageindex),kindname);
		ptrl.setRefreshComplete();
	}
	private class ContentTask2 extends AsyncTask<String, Integer, List<DuitangImageBean>> {
		public ContentTask2(Context context) {
			super();
		}
		@Override
		protected List<DuitangImageBean> doInBackground(String... params) {
			if(PictureDao.checkConnection(getActivity())){
				return PictureDao.getDuitangsbyPage(params[0],params[1]);
			}else{
				return new ArrayList<DuitangImageBean>();
			} 
		}

		@Override
		protected void onPostExecute(List<DuitangImageBean> result) {
			// 这个备注的方法可以5条5条的更新
			// adapter=new Madapter((ArrayList<DuitangInfo>)result);
			adapter2 = new Madapter2(images);
			adapter2.resh(result);

			sgv.setAdapter(adapter2);
			adapter2.notifyDataSetChanged();
			pbloading.setVisibility(View.GONE);
		}
		@Override
		protected void onPreExecute() {
		} 
	}
	private class Madapter2 extends BaseAdapter {
		private ArrayList<DuitangImageBean> duitangs;

		private class Viewholder {
//			public TextView text; 
			public DynamicHeightImageView imageView;
		}

		public Madapter2(List<DuitangImageBean> result) {
			super();
			this.duitangs = (ArrayList<DuitangImageBean>) result;
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			Viewholder holder;
			if (convertView == null) {
				view = getActivity().getLayoutInflater().inflate(R.layout.fuckgrid, null);
				holder = new Viewholder();
				holder.imageView =  (DynamicHeightImageView) view.findViewById(R.id.fuckimage);
//				holder.text = (TextView) view.findViewById(R.id.fucktext);
				view.setTag(holder);
			} else{
				holder = (Viewholder) view.getTag();
			}
//			holder.text.setText(duitangs.get(position).getDesc()); 
			loader.displayImage(duitangs.get(position).getThumburl(),holder.imageView, options,new ImageLoadingListener() {
							@Override
							public void onLoadingCancelled(String arg0,	View arg1) {
							}
							@Override
							public void onLoadingComplete(String arg0,View arg1, Bitmap arg2) {
								// TODO 获取图片的位置，并设置宽高比
								DynamicHeightImageView myview = (DynamicHeightImageView) arg1;
								double newratio = common_sPositionHeightRatios.get(position, 0.0);
								if (newratio == 0) {
									newratio = (double) arg2.getHeight() / (double) arg2.getWidth();
									if (newratio > 3.0) {
										newratio = 3.0;
									}
									myview.setHeightRatio(newratio);
									common_sPositionHeightRatios.append(position, newratio);
								} 
							} 
							@Override
							public void onLoadingFailed(String arg0, View arg1,FailReason arg2) {
							} 
							@Override
							public void onLoadingStarted(String arg0, View arg1) {
								// TODO Auto-generated method stub
							}
						});
//			Glide.with(getActivity()).load(duitangs.get(position).getIsrc()).into(holder.imageView);
				double ratio = common_sPositionHeightRatios.get(position, 0.0);
				if (ratio != 0) {
					holder.imageView.setHeightRatio(ratio);
				}
			return view;
		}

		public void resh(List<DuitangImageBean> beans) {
			this.duitangs.addAll(beans);
			adapter2.notifyDataSetChanged();
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		loader.stop();
	}
}

