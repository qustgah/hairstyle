package com.iiijiaban.hairstyle.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.iiijiaban.hairstyle.utils.TextDrawable;
import com.iiijiaban.hairstyle.R;
import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HomeActivity extends Activity {

    private static final int ITEM_COUNT = 20;
    static Resources res;
    List<Integer> list = new ArrayList<Integer>();
    private static List<String> txtlist = new ArrayList<String>();
    private ImageView imageView0; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main); 
        res = getResources();
        
        final WheelView wheelView = (WheelView) findViewById(R.id.wheelview);
        imageView0 = (ImageView) findViewById(R.id.img0);
        
        InitList(); 
        wheelView.setAdapter(new MaterialColorAdapter(list));
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectListener() {
            @Override
            public void onWheelItemSelected(WheelView parent, int position) {
            	
            }
        });
        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onWheelItemClick(WheelView parent, int position, boolean isSelected) {
            	switch (position) {
						case 0:
							Intent intent = new Intent(HomeActivity.this, CollectionActivity.class);
							intent.putExtra("kind",getResources().getString(R.string.kind_collection));
							intent.putExtra("kindid",0);
							startActivity(intent);
							break;
						case 1: 
							Intent intent1 = new Intent(HomeActivity.this, LocalimgActivity.class);
							intent1.putExtra("kind",getResources().getString(R.string.kind_localload));
							intent1.putExtra("kindid", 1);
							startActivity(intent1);
							break;
						case 2:
							startnewActivity(getResources().getString(R.string.kind_mawei),2,CHorseActivity.class);
							break;
						case 3:
							startnewActivity(getResources().getString(R.string.kind_juanfa),3,CCurlsActivity.class);
							break;
						case 4:
							startnewActivity(getResources().getString(R.string.kind_duanfa),4,CShortActivity.class);
							break;
						case 5:
							startnewActivity(getResources().getString(R.string.kind_ranfa),5,CDyeActivity.class);
							break;
						case 6:
							startnewActivity(getResources().getString(R.string.kind_bianfa),6,CBraidActivity.class);
							break;
						case 7:
							startnewActivity(getResources().getString(R.string.kind_changfa),7,CLongActivity.class);
							break;  
				default:
					break;
				} 
            }
        });
//        initialise the selection drawable with the first contrast color
//        wheelView.setSelectionColor(getContrastColor(entries.get(0)));
    }
    public void startnewActivity(String kindname,int id,Class myclass){
    	Intent intent = new Intent(HomeActivity.this, myclass);
		intent.putExtra("kind", kindname);
		intent.putExtra("kindid", id);
		startActivity(intent); 
    }
    
    public void InitList(){ 
    	
    	int id = R.drawable.shoucangjia;
    	int id2 = R.drawable.bendi;
    	int id3 = R.drawable.mawei;
    	int id4 = R.drawable.juanfa;
    	int id5 = R.drawable.duanfa;
    	int id6 = R.drawable.ranfa;
    	int id7 = R.drawable.bianfa;
    	int id8 = R.drawable.changfa;
    	
    	
    	list.add(id);
    	list.add(id2);
    	list.add(id3);
    	list.add(id4);
    	list.add(id5);
    	list.add(id6);
    	list.add(id7);
    	list.add(id8);
    	
    	txtlist.add(getResources().getString(R.string.kind_collection));
    	txtlist.add(getResources().getString(R.string.kind_localload));
    	txtlist.add(getResources().getString(R.string.kind_mawei));
    	txtlist.add(getResources().getString(R.string.kind_juanfa));
    	txtlist.add(getResources().getString(R.string.kind_duanfa));
    	txtlist.add(getResources().getString(R.string.kind_ranfa));
    	txtlist.add(getResources().getString(R.string.kind_bianfa));
    	txtlist.add(getResources().getString(R.string.kind_changfa));
    	
    } 

    static class MaterialColorAdapter extends WheelArrayAdapter<Integer> {
        MaterialColorAdapter(List<Integer> entries) {
            super(entries);
        } 
        @Override
        public Drawable getDrawable(int position) {
            Drawable[] drawable = new Drawable[] {
            		res.getDrawable(getItem(position))
                    ,new TextDrawable(txtlist.get(position))
            };
            return new LayerDrawable(drawable);
        } 
        @Override
        public int getCount() {
        	// TODO Auto-generated method stub
        	return super.getCount();
        }
        private Drawable createOvalDrawable(int color) {
            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.getPaint().setColor(color);
            return shapeDrawable;
        }
    }
}