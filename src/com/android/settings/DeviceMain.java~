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
import android.content.ComponentName;
import android.view.WindowManager;

public class DeviceMain extends TabActivity {
    SlidingMenu menu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        /*referenced Vomenki's animation code and
        http://danielkvist.net/code/animated-tabhost-with-slide-gesture-in-android */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//         Display display = getWindowManager().getDefaultDisplay();
//         DisplayMetrics outMetrics = new DisplayMetrics ();
//         display.getMetrics(outMetrics);

//         float density  = getResources().getDisplayMetrics().density;
//         float dpWidth  = outMetrics.widthPixels / density;
//         float aimedWidth = (float) (dpWidth*0.8);
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindWidth((int)(display.getWidth()*0.66));
        menu.setFadeEnabled(true);
        menu.setFadeDegree(0.35f);
        menu.setSelectorEnabled(true);
        menu.setMenu(R.layout.sliding_menu);
        TextView deviceText = (TextView) findViewById(R.id.tab_device);
        deviceText.setTextColor(Color.parseColor("#FFFFBB33"));
        deviceText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        deviceText.setTextSize(22);
        defaultText((TextView) findViewById(R.id.tab_system));
        defaultText((TextView) findViewById(R.id.tab_wireless));
	final Context thisContext = this;
        defaultText((TextView) findViewById(R.id.tab_personal));
        RelativeLayout device_settings = (RelativeLayout) findViewById(R.id.layout_device_settings);
        device_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this activity
                if (menu.isMenuShowing())
                    menu.showContent();
            }
        });
        RelativeLayout wifi_settings = (RelativeLayout) findViewById(R.id.layout_wireless_settings);
        wifi_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //other activity
                if(menu.isMenuShowing()) {
                    switchActivity(NetworksMain.class);
                }
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
		RelativeLayout system_settings = (RelativeLayout) findViewById(R.id.layout_system_settings);
		system_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// other activity
				switchActivity(SystemMain.class);
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
        final TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        TabSpec soundTabSpec = tabHost.newTabSpec("sound");
        TabSpec displayTabSpec = tabHost.newTabSpec("display");
        TabSpec storageTabSpec = tabHost.newTabSpec("storage");
        TabSpec batteryTabSpec = tabHost.newTabSpec("battery");
        TabSpec applicationTabSpec = tabHost.newTabSpec("application");
        soundTabSpec.setIndicator("소리").setContent(new Intent(this, Settings.SoundSettingsActivity.class));
        displayTabSpec.setIndicator("화면").setContent(new Intent(this, Settings.DisplaySettingsActivity.class));
        storageTabSpec.setIndicator("저장소").setContent(new Intent(this, Settings.StorageSettingsActivity.class));
        batteryTabSpec.setIndicator("배터리").setContent(new Intent(this, Settings.PowerUsageSummaryActivity.class));
        applicationTabSpec.setIndicator("애플리케이션").setContent(new Intent(this, Settings.ManageApplicationsActivity.class));

        tabHost.addTab(soundTabSpec);
        tabHost.addTab(displayTabSpec);
        tabHost.addTab(storageTabSpec);
        tabHost.addTab(batteryTabSpec);
        tabHost.addTab(applicationTabSpec);
	tabHost.setAnimationCacheEnabled(true);

        tabHost.setOnTabChangedListener(new AnimatedTabHostListener(tabHost,this,"device"));
    }
    public void switchActivity(final Class mClass) {
        final Context mThis = DeviceMain.this;
        if (menu.isMenuShowing()) {
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