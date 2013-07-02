package com.ingker.tongkuancollection;

import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.ingker.activity.CategoryActivity;
import com.ingker.activity.CollectActivity;
import com.ingker.activity.SettingActivity;
import com.ingker.activity.TimelineActivity;

/**
 * 
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @since Mar 12, 2013
 * @version 1.0.0
 */
public class MainActivity extends TabActivity {

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		loadTabHost();
	}
	
	public void loadTabHost() {
		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab
		TabWidget tabWidget = tabHost.getTabWidget();
		tabHost.setPadding(tabHost.getPaddingLeft(), tabHost.getPaddingTop(), tabHost.getPaddingRight(), tabHost.getPaddingBottom() - 5);

		intent = new Intent().setClass(this, TimelineActivity.class);
		spec = tabHost.newTabSpec("同款精选").setIndicator(null, res.getDrawable(R.drawable.menu_index)).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, CategoryActivity.class);
		spec = tabHost.newTabSpec("分类").setIndicator(null, res.getDrawable(R.drawable.menu_category)).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, CollectActivity.class);
		spec = tabHost.newTabSpec("收藏").setIndicator(null, res.getDrawable(R.drawable.menu_share)).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, SettingActivity.class);
		spec = tabHost.newTabSpec("设置").setIndicator(null, res.getDrawable(R.drawable.menu_settings)).setContent(intent);
		tabHost.addTab(spec);
		
		//修改文字大小
		tabHost.setCurrentTab(0);
		int count = tabWidget.getChildCount();
		for (int i = 0; i < count; i++) {
			View view = tabWidget.getChildTabViewAt(i);
			view.getLayoutParams().height = 80; // tabWidget.getChildAt(i)
			final TextView tv = (TextView) view.findViewById(android.R.id.title);
			tv.setTextSize(11);
			tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
		}
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
	}
}


