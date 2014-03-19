package com.android.settings;

import com.android.settings.bluetooth.BluetoothEnabler;
import com.android.settings.wifi.WifiEnabler;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Switch;
import android.widget.TabHost;

/**
 * A custom OnTabChangeListener that uses the TabHost its related to to fetch
 * information about the current and previous tabs. It uses this information to
 * perform some custom animations that slide the tabs in and out from left and
 * right.
 * 
 * @author Daniel Kvist
 * 
 */
public class AnimatedTabHostListener implements TabHost.OnTabChangeListener {

	private static final int ANIMATION_TIME = 540;
	private TabHost tabHost;
	private String option = null;
	private View previousView;
	private View currentView;
	private Context context;
	private GestureDetector gestureDetector;
	private int currentTab;

	/**
	 * Constructor that takes the TabHost as a parameter and sets previousView
	 * to the currentView at instantiation
	 * 
	 * @param tabHost
	 */
	public AnimatedTabHostListener(final TabHost tabHost, Context context,
			String... option) {
	    this.tabHost = tabHost;
	    this.previousView = tabHost.getCurrentView();
	    this.context = context;
	    // if this cause error, check if String array has another method that check if null - hoyo
	    if (!option.equals(null))
		    this.option = option[0];
	}

	/**
	 * When tabs change we fetch the current view that we are animating to and
	 * animate it and the previous view in the appropriate directions.
	 */
	@Override
	public void onTabChanged(String tabId) {
		if (option != null) {
			if (option.equals("networks")) {
				int thisTabIndex = tabHost.getCurrentTab();
				if (thisTabIndex == 0) {
					WifiEnabler mWifiEnabler;
					Activity activity = (Activity) context;
					Switch actionBarSwitch = new Switch(activity);
					final int padding = activity.getResources().getDimensionPixelSize(
							R.dimen.action_bar_switch_padding);
					actionBarSwitch.setPaddingRelative(0, 0, padding, 0);
					activity.getActionBar().setDisplayOptions(
							ActionBar.DISPLAY_SHOW_CUSTOM,
							ActionBar.DISPLAY_SHOW_CUSTOM);
					activity.getActionBar().setCustomView(
							actionBarSwitch,
							new ActionBar.LayoutParams(
									ActionBar.LayoutParams.WRAP_CONTENT,
									ActionBar.LayoutParams.WRAP_CONTENT,
									Gravity.CENTER_VERTICAL | Gravity.END));
					mWifiEnabler = new WifiEnabler(activity, actionBarSwitch);
					mWifiEnabler.resume();
				}
				else if (thisTabIndex == 1) {
					BluetoothEnabler mBluetoothEnabler;
					Activity activity = (Activity) context;
					Switch actionBarSwitch = new Switch(activity);
					final int padding = activity.getResources().getDimensionPixelSize(
							R.dimen.action_bar_switch_padding);
					actionBarSwitch.setPaddingRelative(0, 0, padding, 0);
					activity.getActionBar().setDisplayOptions(
							ActionBar.DISPLAY_SHOW_CUSTOM,
							ActionBar.DISPLAY_SHOW_CUSTOM);
					activity.getActionBar().setCustomView(
							actionBarSwitch,
							new ActionBar.LayoutParams(
									ActionBar.LayoutParams.WRAP_CONTENT,
									ActionBar.LayoutParams.WRAP_CONTENT,
									Gravity.CENTER_VERTICAL | Gravity.END));
					mBluetoothEnabler = new BluetoothEnabler(activity, actionBarSwitch);
					mBluetoothEnabler.resume();
					
				}
				else {
					Activity activity = (Activity) context;
					activity.getActionBar().setCustomView(null);
				}
			}
		}
		currentView = tabHost.getCurrentView();
		if (tabHost.getCurrentTab() > currentTab) {
			previousView.setAnimation(outToLeftAnimation());
			currentView.setAnimation(inFromRightAnimation());
		} else {
			previousView.setAnimation(outToRightAnimation());
			currentView.setAnimation(inFromLeftAnimation());
		}
		previousView = currentView;
		currentTab = tabHost.getCurrentTab();

	}
	/**
	 * Custom animation that animates in from right
	 * 
	 * @return Animation the Animation object
	 */
	private Animation inFromRightAnimation() {
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		return setProperties(inFromRight);
	}

	/**
	 * Custom animation that animates out to the right
	 * 
	 * @return Animation the Animation object
	 */
	private Animation outToRightAnimation() {
		Animation outToRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		return setProperties(outToRight);
	}

	/**
	 * Custom animation that animates in from left
	 * 
	 * @return Animation the Animation object
	 */
	private Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		return setProperties(inFromLeft);
	}

	/**
	 * Custom animation that animates out to the left
	 * 
	 * @return Animation the Animation object
	 */
	private Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		return setProperties(outtoLeft);
	}

	/**
	 * Helper method that sets some common properties
	 * 
	 * @param animation
	 *            the animation to give common properties
	 * @return the animation with common properties
	 */
	private Animation setProperties(Animation animation) {
		animation.setDuration(ANIMATION_TIME);
		animation.setInterpolator(new AnticipateOvershootInterpolator());
		//animation.setFillAfter(true);
		return animation;
	}
}