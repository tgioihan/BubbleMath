package com.bestfunforever.game.bubblemath;

import android.content.SharedPreferences;

public class Config {
	public static final float ANIMATE_DURATION = 1f;

	public static final int MENU__START = 1;
	public static final int MENU__SETTings = 2;

	public static final int MENU__FRUITMODE = 3;

	public static final int MENU__FINDMODE = 4;

	public static final int MENU__RUNMODE = 5;

	public static final int MENU__MORE = 6;

	public static final int MENU__Sound = 7;

	public static final int MENU__Music = 8;

	public static final int MENU__Language = 9;

	public static final int MENU__BACK = 10;

	//
	public static final String KEY_PREF = "PREF";
	public static final String KEY_MAX = "MAX";
	public static final String KEY_SOUND = "SOUND";
	public static final int KEY_ON = 0;
	public static final int KEY_OFF = 1;
	public static final String KEY_MUSIC = "MUSIC";
	public static final String KEY_LANGUAGE = "LANGUAGE";
	public static final int KEY_LANGUAGE_NOTSET = 0;
	public static final int KEY_LANGUAGE_ENG = 1;
	public static final int KEY_LANGUAGE_VNI = 0;

	public static int getMax(SharedPreferences preferences) {
		return preferences.getInt(KEY_MAX, 10);
	}

	public static void setMax(SharedPreferences preferences, int max) {
		preferences.edit().putInt(KEY_MAX, max).commit();
	}

	public static int getSoundState(SharedPreferences preferences) {
		return preferences.getInt(KEY_SOUND, KEY_ON);
	}

	public static void setSoundState(SharedPreferences preferences, int soundState) {
		preferences.edit().putInt(KEY_SOUND, soundState).commit();
	}

	public static int getMusicState(SharedPreferences preferences) {
		return preferences.getInt(KEY_MUSIC, KEY_ON);
	}

	public static void setMusicState(SharedPreferences preferences, int musicState) {
		preferences.edit().putInt(KEY_MUSIC, musicState).commit();
	}

	public static int getLanguage(SharedPreferences preferences) {
		return preferences.getInt(KEY_LANGUAGE, KEY_LANGUAGE_NOTSET);
	}

	public static void setLanguage(SharedPreferences preferences, int languageId) {
		preferences.edit().putInt(KEY_LANGUAGE, languageId).commit();
	}
}
