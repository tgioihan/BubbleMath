package com.bestfunforever.game.bubblemath;

import android.content.Context;

public class StringManger {

	private Context context;
	private int languageId;

	public static final int START = 0;
	public static final int SETTING = 1;
	public static final int MORE = 2;
	public static final int FRUIT = 3;
	public static final int FINDMODE = 4;
	public static final int RUN = 5;
	public static final int SOUND = 6;
	public static final int MUSIC = 7;
	public static final int LANGUAGE = 8;

	public static final int APPLE = 9;
	public static final int BANANA = 10;
	public static final int CHERRY = 11;
	public static final int PINEAAPPLE = 12;
	public static final int MANGO = 13;
	public static final int strawberry = 14;

	public static final int FRUIT_MSG = 15;
	public static final int FINDMODE_MSG = 16;
	public static final int RUN_MSG = 17;

	public StringManger(Context context, int languageId) {
		super();
		this.context = context;
		this.languageId = languageId;
	}

	public String getStringFromKey(int key) {
		if (languageId == Config.KEY_LANGUAGE_ENG) {
			return getStringENGFromKey(key);
		} else {
			return getStringVNIFromKey(key);
		}
	}

	private String getStringVNIFromKey(int key) {
		String result = "";
		switch (key) {
		case START:
			result = context.getString(R.string.vni_start);
			break;
		case SETTING:
			result = context.getString(R.string.vni_settings);
			break;
		case MORE:
			result = context.getString(R.string.vni_more);
			break;
		case FRUIT:
			result = context.getString(R.string.vni_fruitmode);
			break;
		case FINDMODE:
			result = context.getString(R.string.vni_findmode);
			break;
		case RUN:
			result = context.getString(R.string.vni_runmode);
			break;
		case SOUND:
			result = context.getString(R.string.vni_sound);
			break;
		case MUSIC:
			result = context.getString(R.string.vni_music);
			break;
		case LANGUAGE:
			result = context.getString(R.string.vni_language);
			break;
		case APPLE:
			result = context.getString(R.string.vni_apple);
			break;
		case BANANA:
			result = context.getString(R.string.vni_banana);
			break;
		case CHERRY:
			result = context.getString(R.string.vni_cherry);
			break;
		case PINEAAPPLE:
			result = context.getString(R.string.vni_pineapple);
			break;
		case MANGO:
			result = context.getString(R.string.vni_manggo);
			break;
		case strawberry:
			result = context.getString(R.string.vni_strawberry);
			break;

		case FRUIT_MSG:
			result = context.getString(R.string.vni_fruitmode_msg);
			break;
		case FINDMODE_MSG:
			result = context.getString(R.string.vni_findmode_msg);
			break;
		case RUN_MSG:
			result = context.getString(R.string.vni_runmode_msg);
			break;
		default:
			break;
		}
		return result;
	}

	public String getStringENGFromKey(int key) {
		String result = "";
		switch (key) {
		case START:
			result = context.getString(R.string.start);
			break;
		case SETTING:
			result = context.getString(R.string.settings);
			break;
		case MORE:
			result = context.getString(R.string.more);
			break;
		case FRUIT:
			result = context.getString(R.string.fruitmode);
			break;
		case FINDMODE:
			result = context.getString(R.string.findmode);
			break;
		case RUN:
			result = context.getString(R.string.runmode);
			break;
		case SOUND:
			result = context.getString(R.string.sound);
			break;
		case MUSIC:
			result = context.getString(R.string.music);
			break;
		case LANGUAGE:
			result = context.getString(R.string.language);
			break;
		case APPLE:
			result = context.getString(R.string.apple);
			break;
		case BANANA:
			result = context.getString(R.string.banana);
			break;
		case CHERRY:
			result = context.getString(R.string.cherry);
			break;
		case PINEAAPPLE:
			result = context.getString(R.string.pineapple);
			break;
		case MANGO:
			result = context.getString(R.string.manggo);
			break;
		case strawberry:
			result = context.getString(R.string.strawberry);
			break;

		case FRUIT_MSG:
			result = context.getString(R.string.fruitmode_msg);
			break;
		case FINDMODE_MSG:
			result = context.getString(R.string.findmode_msg);
			break;
		case RUN_MSG:
			result = context.getString(R.string.runmode_msg);
			break;
		default:
			break;
		}
		return result;
	}

}
