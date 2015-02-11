package com.iiijiaban.hairstyle.ui;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iiijiaban.com.db.DbHelper;
import com.iiijiaban.hairstyle.R;
import com.iiijiaban.menu.RayMenu;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
public class CollectionDetailActivity extends Activity {

	public ArrayList<String> list;
	ImageView image;
	String url;
	String msg;
	DbHelper dh;
	private ProgressBar pbloading;
	private String fileName;//图片下载后的文件名称
	final DateFormat time=DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL); //时间格式
	private final static String DOWNLOAD_PATH  = Environment.getExternalStorageDirectory() + "/Hairstyle/download/";//下载文件夹
	private static final int[] ITEM_DRAWABLES = {R.drawable.ic_share,R.drawable.ic_download,R.drawable.ic_delete};
	private DisplayImageOptions options;
	public ImageLoader loader;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagedetail);
		loader = ImageLoader.getInstance();
		RayMenu ray = (RayMenu) findViewById(R.id.arcmenu);
		initmenu(ray, ITEM_DRAWABLES);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		image = (ImageView) findViewById(R.id.imagedetail);
		
		pbloading=(ProgressBar)findViewById(R.id.loadingprogress);
		pbloading.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(getApplicationContext()).build());
		
		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		msg = intent.getStringExtra("msg");
		getActionBar().setTitle(msg);

//		new Thread(new Runnable() {
//			@Override
//			public void run() {
		loader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
		loader.displayImage(url, image,options,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				 pbloading.setVisibility(View.GONE);
				
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub
				
			}
		}); 
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
	/**
	 * 初始化底部菜单
	 * 
	 * @param ray
	 * @param itemDrawables
	 */
	private void initmenu(RayMenu ray, int[] itemDrawables) {
		final int itemCount = itemDrawables.length;
		for (int i = 0; i < itemCount; i++) {
			ImageView item = new ImageView(getApplicationContext());
			item.setImageResource(itemDrawables[i]);

			final int position = i;
			ray.addItem(item, new OnClickListener() { 
				@Override
				public void onClick(View v) {
					switch (position) {
					case 0:
						Intent sendIntent = new Intent();
						sendIntent.setAction(Intent.ACTION_SEND);
						sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_pic) + url);
						sendIntent.setType("text/plain");
						startActivity(sendIntent);  
						break;
					case 1:
						Toast.makeText(getApplication(), getResources().getString(R.string.load_pic), Toast.LENGTH_SHORT).show();
						 new Thread(new Runnable() {
								@Override
								public void run() {
									Looper.prepare();
				                	try { 
				        				URL	myurl = new URL(url);
				        					HttpURLConnection conn = (HttpURLConnection)myurl.openConnection();
				        	                conn.connect();
				                            conn.setConnectTimeout(5000);
				        	                InputStream in = conn.getInputStream();
				        	                Bitmap map = BitmapFactory.decodeStream(in);
				        	                File dirFile = new File(DOWNLOAD_PATH);  
				        	                if(!dirFile.exists()){  
				        	                    dirFile.mkdir();  
				        	                }
				        	                Date now =new Date(); 
				        	        		fileName=time.format(now)+".jpg";
				        	                File myCaptureFile = new File(DOWNLOAD_PATH + fileName);  
				        	                Toast.makeText(getApplication(), getResources().getString(R.string.load_success), Toast.LENGTH_SHORT).show();
				        	                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
				        	                map.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
				        	                bos.flush();  
				        	                bos.close();  
				        	                }
				                	catch (MalformedURLException e) {
				    					e.printStackTrace();
				    				} catch (IOException e) {
				    					e.printStackTrace();
				    				}
				                	Looper.loop();
				                	} 
							}).start();
					  break;
					case 2:
						dh = new DbHelper(getApplicationContext());
						dh.del(url); 
						finish(); 
						break; 
					} 
				}
			});
		}
	}

}
