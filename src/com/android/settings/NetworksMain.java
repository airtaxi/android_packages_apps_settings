package com.android.settings;

import static android.os.UserManager.DISALLOW_CONFIG_WIFI;
import static android.os.UserManager.DISALLOW_CONFIG_BLUETOOTH;
import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceActivity;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.android.settings.wifi.WifiEnabler;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.view.GestureDetector;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import android.content.ComponentName;

import com.android.settings.FragmentLoader;

import com.android.settings.cyanogenmod.QuickSettings;
import com.android.settings.cyanogenmod.NavBar;

import android.content.pm.PackageManager;
import android.view.MenuItem;
import android.os.UserManager;

import com.android.settings.bluetooth.LocalBluetoothAdapter;
import android.bluetooth.BluetoothAdapter;
public class NetworksMain extends TabActivity {
	TabHost tabHost;
	SlidingMenu menu;
	private WifiManager mWifiManager;
	private LocalBluetoothAdapter mLocalAdapter;
	private static final int MENU_ID_WPS_PBC = Menu.FIRST;
	private static final int MENU_ID_WPS_PIN = Menu.FIRST + 1;
	private static final int MENU_ID_P2P = Menu.FIRST + 2;
	private static final int MENU_ID_ADD_NETWORK = Menu.FIRST + 3;
	private static final int MENU_ID_ADVANCED = Menu.FIRST + 4;
	private static final int MENU_ID_SCAN = Menu.FIRST + 5;
	
	private static final int MENU_ID_SCAN_BT = Menu.FIRST;
	private static final int MENU_ID_RENAME_DEVICE = Menu.FIRST + 1;
	private static final int MENU_ID_VISIBILITY_TIMEOUT = Menu.FIRST + 2;
	private static final int MENU_ID_SHOW_RECEIVED = Menu.FIRST + 3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*
		 * referenced Vomenki's animation code and
		 * http://danielkvist.net/code/animated
		 * -tabhost-with-slide-gesture-in-android
		 */
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
// 		Display display = getWindowManager().getDefaultDisplay();
// 		DisplayMetrics outMetrics = new DisplayMetrics();
// 		display.getMetrics(outMetrics);
// 
// 		float density = getResources().getDisplayMetrics().density;
// 		float dpWidth = outMetrics.widthPixels / density;
// 		float aimedWidth = (float) (dpWidth * 0.8);
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindWidth((int)(display.getWidth()*0.66));
		menu.setMenu(R.layout.sliding_menu);
		TextView wifiText = (TextView) findViewById(R.id.tab_wireless);
		wifiText.setTextColor(Color.parseColor("#FFFFBB33"));
		wifiText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		wifiText.setTextSize(22);
		defaultText((TextView)findViewById(R.id.tab_system));
		defaultText((TextView)findViewById(R.id.tab_device));
		defaultText((TextView)findViewById(R.id.tab_personal));
		final Context thisContext = this;
		RelativeLayout wifi_settings = (RelativeLayout) findViewById(R.id.layout_wireless_settings);
		wifi_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// this activity
				if (menu.isMenuShowing())
					menu.showContent(true);
			}
		});
		RelativeLayout device_settings = (RelativeLayout) findViewById(R.id.layout_device_settings);
		device_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// other activity
				switchActivity(DeviceMain.class);
			}
		});
		RelativeLayout personal_settings = (RelativeLayout) findViewById(R.id.layout_personal_settings);
		personal_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// other activity
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
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		TabSpec wifiTabSpec = tabHost.newTabSpec("wifi");
		TabSpec bluetoothTabSpec = tabHost.newTabSpec("bluetooth");
		TabSpec dataUsageTabSpec = tabHost.newTabSpec("data");
		TabSpec wirelessTabSpec = tabHost.newTabSpec("wireless");
		wifiTabSpec.setIndicator("와이파이").setContent(
				new Intent(this, Settings.WifiSettingsActivity.class));
		bluetoothTabSpec.setIndicator("블루투스").setContent(
				new Intent(this, Settings.BluetoothSettingsActivity.class));
		dataUsageTabSpec.setIndicator("데이터").setContent(
				new Intent(this, Settings.DataUsageSummaryActivity.class));
		wirelessTabSpec.setIndicator("무선 네트워크").setContent(
				new Intent(this, Settings.WirelessSettingsActivity.class));

		tabHost.addTab(wifiTabSpec);
		tabHost.addTab(bluetoothTabSpec);
		tabHost.addTab(dataUsageTabSpec);
		tabHost.addTab(wirelessTabSpec);
		//FIXME: NOT WORKING - Hoyo
		tabHost.setAnimationCacheEnabled(true);
		//FIXME: THIS TOO!  - Hoyo
// 		tabHost.setOnTouchListener(new OnSwipeTouchListener() {
// 		    public void onSwipeRight() {
// 			System.out.println("HOYO: RIGHT!");
// 			validate(-1);
// 		    }
// 		    public void onSwipeLeft() {
// 			System.out.println("HOYO: LEFT!");
// 			validate(1);
// 		    }
// 		    public void validate(int dir) {
// 			int changeto = tabHost.getCurrentTab()+dir;
// 			if (changeto >= 0 && changeto <= (tabHost.getTabWidget().getChildCount() - 1))
// 			{
// 			    System.out.println("HOYO: CHANGED: DBG:"+changeto);
// 			    tabHost.setCurrentTab(changeto);
// 			} else {
// 			    System.out.println("HOYO: NOT CHANGED: DBG(n/m):"+changeto+"/"+tabHost.getTabWidget().getChildCount());
// 			}
// 		    }
// 		});

		WifiEnabler mWifiEnabler;
		Activity activity = this;
		Switch actionBarSwitch = new Switch(activity);
		final int padding = activity.getResources().getDimensionPixelSize(
				R.dimen.action_bar_switch_padding);
		actionBarSwitch.setPaddingRelative(0, 0, padding, 0);
		activity.getActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM);
		activity.getActionBar().setCustomView(
				actionBarSwitch,
				new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
						ActionBar.LayoutParams.WRAP_CONTENT,
						Gravity.CENTER_VERTICAL | Gravity.END));
		mWifiEnabler = new WifiEnabler(activity, actionBarSwitch);
		mWifiEnabler.resume();
		tabHost.setOnTabChangedListener(new AnimatedTabHostListener(tabHost,
				this, "networks"));
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	  menu.clear();
	  if(tabHost.getCurrentTab() == 0) {
//		TODO : FIXTHIS
	    UserManager mUserManager = (UserManager) getSystemService(Context.USER_SERVICE);
	    if (mUserManager.hasUserRestriction(DISALLOW_CONFIG_WIFI)) return false;
	    WifiManager mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
	    menu.add(Menu.NONE, MENU_ID_WPS_PBC, 0, R.string.wifi_menu_wps_pbc)
		    .setIcon(R.drawable.ic_wps)
		    .setEnabled(mWifiManager.isWifiEnabled())
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	    menu.add(Menu.NONE, MENU_ID_ADD_NETWORK, 0, R.string.wifi_add_network)
		    .setIcon(R.drawable.ic_menu_add)
		    .setEnabled(mWifiManager.isWifiEnabled())
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	    menu.add(Menu.NONE, MENU_ID_SCAN, 0, R.string.wifi_menu_scan)
		    .setIcon(R.drawable.ic_menu_scan_network)
		    .setEnabled(mWifiManager.isWifiEnabled())
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	    menu.add(Menu.NONE, MENU_ID_WPS_PIN, 0, R.string.wifi_menu_wps_pin)
		    .setEnabled(mWifiManager.isWifiEnabled())
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	    if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)) {
		menu.add(Menu.NONE, MENU_ID_P2P, 0, R.string.wifi_menu_p2p)
			.setEnabled(mWifiManager.isWifiEnabled())
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	    }
	    menu.add(Menu.NONE, MENU_ID_ADVANCED, 0, R.string.wifi_menu_advanced)
		    .setIcon(android.R.drawable.ic_menu_manage)
		    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	  }
	  else if(tabHost.getCurrentTab() == 2) {
// 	      // If the user is not allowed to configure bluetooth, do not show the menu.
// 	      UserManager mUserManager = (UserManager) getSystemService(Context.USER_SERVICE);
// 	      if (mUserManager.hasUserRestriction(DISALLOW_CONFIG_BLUETOOTH)) return false;
// 
// 	      boolean bluetoothIsEnabled = mLocalAdapter.getBluetoothState() == BluetoothAdapter.STATE_ON;
// 	      boolean isDiscovering = mLocalAdapter.isDiscovering();
// 	      int textId = isDiscovering ? R.string.bluetooth_searching_for_devices :
// 		  R.string.bluetooth_search_for_devices;
// 	      menu.add(Menu.NONE, MENU_ID_SCAN_BT, 0, textId)
// 		      .setEnabled(bluetoothIsEnabled && !isDiscovering)
// 		      .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
// 	      menu.add(Menu.NONE, MENU_ID_RENAME_DEVICE, 0, R.string.bluetooth_rename_device)
// 		      .setEnabled(bluetoothIsEnabled)
// 		      .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
// 	      menu.add(Menu.NONE, MENU_ID_VISIBILITY_TIMEOUT, 0, R.string.bluetooth_visibility_timeout)
// 		      .setEnabled(bluetoothIsEnabled)
// 		      .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
// 	      menu.add(Menu.NONE, MENU_ID_SHOW_RECEIVED, 0, R.string.bluetooth_show_received_files)
// 		      .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
 	    }
 	  return super.onPrepareOptionsMenu(menu);
	}
	public Activity getActivity() {
	  return this;
	}
// 	@Override
// 	public boolean onOptionsItemSelected(MenuItem item) {
// 	    if(tabHost.getCurrentTab() == 1) {
// 		switch (item.getItemId()) {
// 		    case MENU_ID_SCAN:
// 			if (mLocalAdapter.getBluetoothState() == BluetoothAdapter.STATE_ON) {
// 			    startScanning();
// 			}
// 			return true;
// 
// 		    case MENU_ID_RENAME_DEVICE:
// 			new BluetoothNameDialogFragment().show(
// 				getFragmentManager(), "rename device");
// 			return true;
// 
// 		    case MENU_ID_VISIBILITY_TIMEOUT:
// 			new BluetoothVisibilityTimeoutFragment().show(
// 				getFragmentManager(), "visibility timeout");
// 			return true;
// 
// 		    case MENU_ID_SHOW_RECEIVED:
// 			Intent intent = new Intent(BTOPP_ACTION_OPEN_RECEIVED_FILES);
// 			getActivity().sendBroadcast(intent);
// 			return true;
// 		}
// 	    }
// 	    return super.onOptionsItemSelected(item);
// 	}

	public void switchActivity(final Class mClass) {
		final Context mThis = NetworksMain.this;
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