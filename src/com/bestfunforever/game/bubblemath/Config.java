package com.bestfunforever.game.bubblemath;

import android.content.SharedPreferences;

public class Config {
	public static final float ANIMATE_DURATION = 1f;

	public static final int MENU__START = 0;
	public static final int MENU__SETTings = 1;

	public static final int MENU__FRUITMODE = 2;

	public static final int MENU__FINDMODE = 3;

	public static final int MENU__RUNMODE = 4;

	//
	public static final String KEY_PREF = "PREF";
	public static final String KEY_MAX = "MAX";
	public static final String KEY_SOUND = "SOUND";
	public static final String KEY_VOLUME = "VOLUME";

	public static int getMax(SharedPreferences preferences) {
		return preferences.getInt(KEY_MAX, 5);
	}

	public static void setMax(SharedPreferences preferences, int max) {
		preferences.edit().putInt(KEY_MAX, max).commit();
	}
}
