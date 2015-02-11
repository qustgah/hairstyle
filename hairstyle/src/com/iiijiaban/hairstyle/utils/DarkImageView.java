package com.iiijiaban.hairstyle.utils;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.etsy.android.grid.util.DynamicHeightImageView;

public class DarkImageView extends DynamicHeightImageView {

	public DarkImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOnTouchListener(VIEW_TOUCH_DARK);
	}

	public OnTouchListener VIEW_TOUCH_DARK = new OnTouchListener() {
		// �䰵(����-50��ֵԽ����Ч��Խ��)
		public final float[] BT_SELECTED_DARK = new float[] { 1, 0, 0, 0, -50,
				0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				ImageView iv = (ImageView) v;
				iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED_DARK));
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				ImageView iv = (ImageView) v;
				iv.clearColorFilter();
				mPerformClick();
			} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
				ImageView iv = (ImageView) v;
				iv.clearColorFilter();
			}
			return true; // ��Ϊfalse��ִ��ACTION_DOWN��������ִ��
		}
	};

	private void mPerformClick() {
		this.performClick();
	}
}