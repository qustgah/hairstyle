package com.iiijiaban.hairstyle.fragment;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.iiijiaban.hairstyle.R;
import com.ijiaban.actionbar.pulltorefreshlib.OnRefreshListener;

/**
 * 主fragment 包含N個子fragment
 * 
 * @author adamin
 * 
 */
public class MainFragment extends Fragment implements OnRefreshListener {
//	private MyPagerAdapter adapter;
	/**
	 * viewpager相关
	 */
//	private PagerSlidingTabStrip tabs;
//	private ViewPager pager;
    private AdView mAdView;
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	} 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	} 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mainfragment_layout, container,false);
		setRetainInstance(true);
		setHasOptionsMenu(true); 
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.adlayoutmainfragment);
		mAdView = new AdView(getActivity());
		mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
		mAdView.setAdSize(AdSize.BANNER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layout.addView(mAdView, params);
		mAdView.loadAd(new AdRequest.Builder().build());
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		TestFragment2 fragment = new TestFragment2();
		fragmentTransaction.replace(R.id.mainfragment,fragment).commit();
		return view;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	} 
	/**
	 * 实例化
	 * 
	 * @return
	 */
	public static Fragment newInstance() {
		Fragment fr = new MainFragment();
		return fr;
	}

	/**
	 * OnRefreshListene 接口的方法
	 */
	@Override
	public void onRefreshStarted(View view) {

	} 
}
