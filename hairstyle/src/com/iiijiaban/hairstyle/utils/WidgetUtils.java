package com.iiijiaban.hairstyle.utils;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * ͼ�δ���Ĺ�����
 * @author Administrator
 *
 */
public class WidgetUtils {

	/**
	 * ����ͼƬʹͼƬ�䰵������
	 * @param imageview  ͼƬview 
	 */
	public static void setImageColor(ImageView imageview){
		Drawable drawable = imageview.getDrawable();
		drawable.setColorFilter(Color.GRAY,PorterDuff.Mode.MULTIPLY);
		imageview.setImageDrawable(drawable); 
	}
}
