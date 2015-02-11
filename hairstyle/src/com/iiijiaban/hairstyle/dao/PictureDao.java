package com.iiijiaban.hairstyle.dao;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.iiijiaban.hairstyle.beans.DuitangImageBean;
import com.iiijiaban.hairstyle.utils.Helper;

public class PictureDao {

	/**
	 * 获取网页的内容
	 * @param pageno
	 * @param kindname
	 * @return
	 */
	public static ArrayList<DuitangImageBean> getDuitangsbyPage(String pageno,String kindname) {
		String str = "";
		try {
			str = java.net.URLEncoder.encode(kindname, "utf-8");
		} catch (Exception e) {
			Log.e("hairstyle-url", "url转换错误");
		}
		
		String url = "http://www.duitang.com/search/?kw=" + str
				+ "&type=feed&page=" + Integer.valueOf(pageno);
		ArrayList<DuitangImageBean> images = new ArrayList<DuitangImageBean>();
		try {
			Document doc = Jsoup.connect(url).timeout(10000).get();
			Element element0 = doc.select("div[class=pbr woo-mpbr]").last();
			Element element00 = element0.select("li[class=pbrw]").first();
			Element element000 = element00.select("span").first();
			String pages = element000.text();
			Elements htmlimages = doc.select("div[class=woo]");
			for (Element e : htmlimages) {
				DuitangImageBean imageBean = new DuitangImageBean();
				imageBean.setPages(pages);
				Element element1 = e.select("div[class=mbpho]").first();
				Element element2 = element1.getElementsByTag("a").first();
				Element element3 = element2.getElementsByTag("img").first();
				imageBean.setId(element3.attr("data-rootid"));
				imageBean.setDesc(element3.attr("alt"));
				imageBean.setThumburl(element3.attr("src"));
				images.add(imageBean);
			}
		} catch (Exception e) {
			if (e != null) {
				Log.e("hairstyle-url", e.getMessage().toString());
			}
		}
		return images;
	}

	/**
	 * 检查是否有网络连接
	 * 
	 * @param context
	 *            上下文
	 * @return
	 */
	public static boolean checkConnection(Context context) {
		try {
			@SuppressWarnings("static-access")
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			if (networkInfo != null) {
				return networkInfo.isAvailable();
			}
		} catch (Exception e) {

		}
		return false;
	}
	
	/**
	 * 根据专辑Id来获取堆糖专辑的数据
	 * @param id
	 * @param pageno
	 * @return
	 */
	public static ArrayList<DuitangImageBean> getDuitangsById(String id,String pageno){
		String myUrl = "" ;
		ArrayList<DuitangImageBean> images = new ArrayList<DuitangImageBean>();
		
		try {
			String result=Helper.getStringFromUrl(myUrl);
			if (null != result) {
				JSONObject newsObject = new JSONObject(result);
				JSONObject jsonObject = newsObject.getJSONObject("data");
				JSONArray blogsJson = jsonObject.getJSONArray("blogs");
				for (int i = 0; i < blogsJson.length(); i++) {
					JSONObject newsInfoLeftObject = blogsJson.getJSONObject(i);
					DuitangImageBean newsInfo = new DuitangImageBean();
					newsInfo.setId(newsInfoLeftObject.isNull("photo_id") ? "": newsInfoLeftObject.getString("photo_id"));
					newsInfo.setDesc(newsInfoLeftObject.isNull("msg") ? "": newsInfoLeftObject.getString("msg"));
					newsInfo.setThumburl(newsInfoLeftObject.isNull("isrc") ? "": newsInfoLeftObject.getString("isrc"));
					images.add(newsInfo);
				}
			}
		} catch (Exception e) { 
			if(e!=null){
				Log.e("http", e.getMessage().toString());
			} 
		} 
		return images;
	}
}