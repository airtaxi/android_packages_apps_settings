package com.android.settings;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import com.android.settings.AnimatedTabHostListener;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import android.content.SharedPreferences;
import com.android.settings.DevelopmentSettings;
import android.content.ComponentName;
import android.view.WindowManager;

public class SystemMain extends TabActivity {
    SlidingMenu menu;
    TabHost tabHost;
    @Override
    public void onCreate(Bundle savedInstanceState) {
		/*referenced Vomenki's anima	tion code and
		http://danielkvist.net/code/animated-tabhost-with-slide-gesture-in-android */
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		
		Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
// 		Display display = getWindowManager().getDefaultDisplay();
// 		DisplayMetrics outMetrics = new DisplayMetrics ();
// 		display.getMetrics(outMetrics);
		
// 		float density  = getResources().getDisplayMetrics().density;
// 		float dpWidth  = outMetrics.widthPixels / density;
// 		float aimedWidth = (float) ((int)(display.getWidth()*0.66));
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindWidth((int)(display.getWidth()*0.66));
		menu.setFadeEnabled(true);
		menu.setMenu(R.layout.sliding_menu);
		TextView systemText = (TextView) findViewById(R.id.tab_system);
		systemText.setTextColor(Color.parseColor("#FFFFBB33"));
		systemText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		systemText.setTextSize(22);
		defaultText((TextView) findViewById(R.id.tab_personal));
		defaultText((TextView) findViewById(R.id.tab_wireless));
		defaultText((TextView) findViewById(R.id.tab_device));
		final Context thisContext = this;
		RelativeLayout system_settings = (RelativeLayout) findViewById(R.id.layout_system_settings);
		system_settings.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		        //this activity
		        if (menu.isMenuShowing())
		            menu.showContent(true);
		    }
		});
		RelativeLayout wifi_settings = (RelativeLayout) findViewById(R.id.layout_wireless_settings);
		wifi_settings.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		        //other activity
		        switchActivity(NetworksMain.class);
		    }
		});
		RelativeLayout device_settings = (RelativeLayout) findViewById(R.id.layout_device_settings);
		device_settings.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		        //other activity
		        switchActivity(DeviceMain.class);
		    }
		});
		RelativeLayout personal_settings = (RelativeLayout) findViewById(R.id.layout_personal_settings);
		personal_settings.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		        //other activity
		        switchActivity(PersonalMain.class);
		    }
		});
		RelativeLayout halo_settings = (RelativeLayout) findViewById(R.id.layout_halo_settings);
		halo_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// internal activity
				Intent intent = new Intent(thisContext,FragmentLoader.class);
				intent.putExtra("identity","halo");
				startActivity(intent);
			}
		});
		RelativeLayout hybrid_settings = (RelativeLayout) findViewById(R.id.layout_hybrid_settings);
		hybrid_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// external activity
				Intent intent = new Intent();
				intent.setComponent(new ComponentName("com.paranoid.preferences", "com.paranoid.preferences.MainActivity"));
				startActivity(intent);
			}
		});
		RelativeLayout lock_settings = (RelativeLayout) findViewById(R.id.layout_lock_screen_settings);
		lock_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// internal activity
				Intent intent = new Intent(thisContext,FragmentLoader.class);
				intent.putExtra("identity","lock");
				startActivity(intent);
			}
		});
		RelativeLayout pie_settings = (RelativeLayout) findViewById(R.id.layout_pie_control_settings);
		pie_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// internal activity
				Intent intent = new Intent(thisContext,FragmentLoader.class);
				intent.putExtra("identity","pie");
				startActivity(intent);
			}
		});
		RelativeLayout toolbar_settings = (RelativeLayout) findViewById(R.id.layout_tool_bar_settings);
		toolbar_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// internal activity
				Intent intent = new Intent(thisContext,FragmentLoader.class);
				intent.putExtra("identity","tool");
				startActivity(intent);
			}
		});
		RelativeLayout themes_settings = (RelativeLayout) findViewById(R.id.layout_themes_settings);
		themes_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// external activity
				Intent intent = new Intent();
				intent.setComponent(new ComponentName("com.tmobile.themechooser", "com.tmobile.themechooser.ThemeChooser"));
				startActivity(intent);
			}
		});
		SharedPreferences mDevelopmentPreferences = getSharedPreferences(DevelopmentSettings.PREF_FILE,
		    Context.MODE_PRIVATE);
		boolean showDev = mDevelopmentPreferences.getBoolean(
                DevelopmentSettings.PREF_SHOW,
                android.os.Build.TYPE.equals("eng"));
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		TabSpec devTabSpec = tabHost.newTabSpec("dev");
		TabSpec appopsTabSpec = tabHost.newTabSpec("appops");
		TabSpec dateTabSpec = tabHost.newTabSpec("date");
		TabSpec accessTabSpec = tabHost.newTabSpec("access");
		TabSpec aboutTabSpec = tabHost.newTabSpec("about");
		appopsTabSpec.setIndicator("권한 설정").setContent(
				new Intent(this, Settings.AppOpsSummaryActivity.class));
		dateTabSpec.setIndicator("날짜 및 시간").setContent(
				new Intent(this, Settings.DateTimeSettingsActivity.class));
		accessTabSpec.setIndicator("접근성").setContent(
				new Intent(this, Settings.AccessibilitySettingsActivity.class));
		aboutTabSpec.setIndicator("기기 정보").setContent(
				new Intent(this, Settings.DeviceInfoSettingsActivity.class));
                if(showDev) {
		  devTabSpec.setIndicator("개발자 옵션").setContent(
		    new Intent(this, Settings.DevelopmentSettingsActivity.class));
		}
		tabHost.addTab(accessTabSpec);
		tabHost.addTab(dateTabSpec);
		tabHost.addTab(appopsTabSpec);
		if(showDev)
		  tabHost.addTab(devTabSpec);
		tabHost.addTab(aboutTabSpec);
		tabHost.setAnimationCacheEnabled(true);

		tabHost.setOnTabChangedListener(new AnimatedTabHostListener(tabHost,this,"system"));
	}
	public void switchActivity(final Class mClass) {
		final Context mThis = SystemMain.this;
		if(menu.isMenuShowing()) {
			menu.showContent(true);
			menu.setOnClosedListener(new SlidingMenu.OnClosedListener() {
				@Override
				public void onClosed() {
					startActivity(new Intent(mThis, mClass));
					overridePendingTransition(0, 0);
					finish();
				}
			});
		}
	}
	public void defaultText(TextView tv) {
	    tv.setTextColor(Color.parseColor("#FFFFFFFF"));
	    tv.setTypeface(null);
	    tv.setTextSize(20);
	}
}