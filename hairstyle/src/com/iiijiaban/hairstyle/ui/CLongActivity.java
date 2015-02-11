package com.iiijiaban.hairstyle.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.iiijiaban.hairstyle.R;
import com.iiijiaban.hairstyle.fragment.MainFragment;

public class CLongActivity extends FragmentActivity{
	private MenuItem collection;
	private String kindname;
	private int kindid;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.main);
		if (arg0 == null) {
			setInitialFragment();
		}
		kindname = getIntent().getStringExtra("kind");
		if(kindname.equals("")){
			switch (getIntent().getIntExtra("kindid", 2)) {
			case 2:
				kindname = "Horsetail";
				break;
			case 3:
				kindname = "Curls";
				break;
			case 4:
				kindname = "Short Hair";
				break;
			case 5:
				kindname = "Dye Hair";
				break;
			case 6:
				kindname = "Braided Hair";
				break;
			case 7:
				kindname = "Long Hair";
				break; 
			default:
				break;
			} 
		}
		getActionBar().setTitle(kindname);
		getActionBar().setHomeButtonEnabled(true); 
		getActionBar().setDisplayHomeAsUpEnabled(true); 
		getActionBar().setDisplayShowHomeEnabled(true);
	}
	/**
	 * ÔOÖÃÖ÷fragment
	 */
	private void setInitialFragment() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.main_content, MainFragment.newInstance()).commit();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflat = new MenuInflater(this);
		inflat.inflate(R.menu.actionbarmenu, menu); 
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

}
