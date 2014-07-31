package com.bestfunforever.game.bubblemath;

import java.io.IOException;
import java.util.Locale;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import com.bestfunforever.andengine.uikit.activity.PortraitAdmobGameActivity;
import com.bestfunforever.andengine.uikit.activity.facebook.ILoginFacebook;
import com.bestfunforever.andengine.uikit.activity.facebook.IShareFacebook;
import com.bestfunforever.andengine.uikit.entity.ICheckedChange;
import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.andengine.uikit.entity.ISelector;
import com.bestfunforever.andengine.uikit.entity.Sprite.BubbleSprite;
import com.bestfunforever.game.bubblemath.Entity.MainMenu.IMenuRectangle;
import com.bestfunforever.game.bubblemath.Entity.MainMenu.MenuModeGameRectang;
import com.bestfunforever.game.bubblemath.Entity.MainMenu.MenuRectang;
import com.bestfunforever.game.bubblemath.Entity.MainMenu.MenuSettingGameRectangle;
import com.facebook.Session;
import com.facebook.SessionState;

public class MainActivity extends PortraitAdmobGameActivity implements IClick {

	protected static final String tag = "MainActivity";
	private BitmapTextureAtlas customFontTexture;
	private Font customFont;
	private TextureRegion mBgTextureRegion;
	private MenuRectang menu;
	private Scene mScene;
	private MenuModeGameRectang modeGame;
	private IMenuRectangle mCurrentMenuRectangle;
	private BitmapTextureAtlas mSoundBitmapTextureAtlas;
	private TiledTextureRegion mSoundTextureRegion;
	private BitmapTextureAtlas mMusicBitmapTextureAtlas;
	private TiledTextureRegion mMusicTextureRegion;
	private MenuSettingGameRectangle settingGame;
	private TextureRegion mBackRegion;
	private StringManger stringManger;
	private SharedPreferences pref;
	private TiledTextureRegion fbRegion;

	public void lockUserAction(boolean enable) {
		if (menu != null) {
			menu.lockUserAction(enable);
		}
		if (modeGame != null) {
			modeGame.lockUserAction(enable);
		}
		if (settingGame != null) {
			settingGame.lockUserAction(enable);
		}
	}

	@Override
	protected String getAdmobKey() {
		// TODO Auto-generated method stub
		return getString(R.string.admobkey);
	}

	@Override
	protected void onCreateResources() {
		Log.d("", "key hash " + getKeyHash(getPackageName()));
		FontFactory.setAssetBasePath("font/");
		customFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		customFont = FontFactory.createFromAsset(getFontManager(), customFontTexture, getAssets(),
				"UVF_AguafinaScript.ttf", (float) 100 * ratio, true, Color.RED);
		customFont.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas mBgBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 640, 960,
				TextureOptions.BILINEAR);
		this.mBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBgBitmapTextureAtlas, this,
				"main_bg.png", 0, 0); // 64x32
		mBgBitmapTextureAtlas.load();

		BitmapTextureAtlas backAtlas = new BitmapTextureAtlas(this.getTextureManager(), 81, 74, TextureOptions.BILINEAR);
		this.mBackRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backAtlas, this, "back.png", 0, 0); // 64x32
		backAtlas.load();

		BitmapTextureAtlas fbAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (84), (int) (84),
				TextureOptions.BILINEAR);
		fbRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fbAtlas, this, "fb.png", 0, 0, 1, 1);
		fbAtlas.load();

		this.mSoundBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 527, 388, TextureOptions.BILINEAR);
		this.mSoundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				this.mSoundBitmapTextureAtlas, this, "sound.png", 0, 0, 2, 1); // 64x32
		this.mSoundBitmapTextureAtlas.load();

		this.mMusicBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 527, 388, TextureOptions.BILINEAR);
		this.mMusicTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				this.mMusicBitmapTextureAtlas, this, "music.png", 0, 0, 2, 1); // 64x32
		this.mMusicBitmapTextureAtlas.load();

		loadSound();
		loadMusic();
		loadSwitchSound();
	}

	@Override
	protected Scene onCreateScene() {
		mScene = new Scene();
		mScene.setBackground(new SpriteBackground(new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, mBgTextureRegion,
				getVertexBufferObjectManager())));
		this.mEngine.registerUpdateHandler(new FPSLogger());
		pref = getSharedPreferences(Config.KEY_PREF, 0);
		Log.d("", "language " + Locale.getDefault().getDisplayLanguage());
		if (Config.getLanguage(pref) == Config.KEY_LANGUAGE_NOTSET) {
			if (Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("VNI")) {
				Config.setLanguage(pref, Config.KEY_LANGUAGE_VNI);
			} else {
				Config.setLanguage(pref, Config.KEY_LANGUAGE_ENG);
			}
		}

		stringManger = new StringManger(this);

		BubbleSprite fbSprite = new BubbleSprite(30 * ratio, 0, ratio, fbRegion, getVertexBufferObjectManager());
		fbSprite.setY(CAMERA_HEIGHT - fbSprite.getHeight() - 20 * ratio);
		fbSprite.setClickListenner(new IClick() {

			@Override
			public void onCLick(IAreaShape view) {
				playCLick();
				shareFb();

			}
		});
		mScene.attachChild(fbSprite);
		mScene.registerTouchArea(fbSprite);

		return mScene;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (music != null && music.isPlaying()) {
			music.pause();
		}
	}

	@Override
	public void onBackPressed() {
		if (mCurrentMenuRectangle != null && mCurrentMenuRectangle != menu) {
			clearLastMenuAndCreateFirstMenu();
		} else
			super.onBackPressed();
	}

	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		super.onResumeGame();
		playMusic();
		clearLastMenuAndCreateFirstMenu();
	}

	private void createAndShowMenuIfNeed() {
		if (menu == null) {
			menu = new MenuRectang(this, stringManger, 218 * ratio, 300 * ratio, 424 * ratio, 450 * ratio, ratio,
					customFont, getVertexBufferObjectManager());
		}

		menu.attachToScene(mScene);
		menu.setClickListenner(this);
		menu.show(new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				playSwitchSound();
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				lockUserAction(true);
			}
		});
		mCurrentMenuRectangle = menu;
	}

	@Override
	public void onCLick(IAreaShape view) {
		int id = ((ISelector) view).getId();
		playCLick();
		switch (id) {
		case Config.MENU__START:
			lockUserAction(false);
			mCurrentMenuRectangle.hide(new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

				}

				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					clearLastMenuAndCreateChooseMode();
				}
			});
			break;

		case Config.MENU__SETTings:
			lockUserAction(false);
			mCurrentMenuRectangle.hide(new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

				}

				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					clearLastMenuAndCreateSettingsMenu();
				}
			});
			break;

		case Config.MENU__BACK:
			lockUserAction(false);
			clearLastMenuAndCreateFirstMenu();
			break;

		case Config.MENU__Music:
			lockUserAction(false);
			break;

		case Config.MENU__Sound:
			lockUserAction(false);
			break;

		case Config.MENU__Language:
			lockUserAction(false);
			break;

		case Config.MENU__FRUITMODE:
			lockUserAction(false);
			startActivity(new Intent(getApplicationContext(), FruitCountModeActivity.class));
			break;
		case Config.MENU__FINDMODE:
			lockUserAction(false);
			startActivity(new Intent(getApplicationContext(), FindModeActivty.class));
			break;
		case Config.MENU__RUNMODE:
			lockUserAction(true);
			startActivity(new Intent(getApplicationContext(), RunModeActivity.class));
			break;
		default:
			break;
		}
	}

	private void clearLastMenuAndCreateChooseMode() {
		lockUserAction(false);
		if (mCurrentMenuRectangle != null) {
			mCurrentMenuRectangle.hide(new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

				}

				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (mCurrentMenuRectangle != null) {
								mCurrentMenuRectangle.detachFromScene(mScene);
							}
							createAndShowChooseModeIfNeed();
						}
					});
				}
			});
		} else {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (mCurrentMenuRectangle != null) {
						mCurrentMenuRectangle.detachFromScene(mScene);
					}
					createAndShowChooseModeIfNeed();
				}
			});
		}

	}

	private void clearLastMenuAndCreateFirstMenu() {
		lockUserAction(false);
		if (mCurrentMenuRectangle != null) {
			mCurrentMenuRectangle.hide(new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

				}

				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (mCurrentMenuRectangle != null) {
								mCurrentMenuRectangle.detachFromScene(mScene);
							}
							createAndShowMenuIfNeed();
						}
					});
				}
			});
		} else {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (mCurrentMenuRectangle != null) {
						mCurrentMenuRectangle.detachFromScene(mScene);
					}
					createAndShowMenuIfNeed();
				}
			});
		}

	}

	private void createAndShowChooseModeIfNeed() {
		if (modeGame == null) {
			modeGame = new MenuModeGameRectang(this, stringManger, 218 * ratio, 300 * ratio, 424 * ratio, 600 * ratio,
					ratio, customFont, mBackRegion, getVertexBufferObjectManager());
		}
		modeGame.attachToScene(mScene);
		modeGame.setClickListenner(this);
		modeGame.show(new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				playSwitchSound();
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				lockUserAction(true);
			}
		});
		mCurrentMenuRectangle = modeGame;
	}

	private void clearLastMenuAndCreateSettingsMenu() {
		lockUserAction(false);
		if (mCurrentMenuRectangle != null) {
			mCurrentMenuRectangle.hide(new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

				}

				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (mCurrentMenuRectangle != null) {
								mCurrentMenuRectangle.detachFromScene(mScene);
							}
							createAndShowSettingsMenuIfNeed();
						}
					});
				}
			});
		} else {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (mCurrentMenuRectangle != null) {
						mCurrentMenuRectangle.detachFromScene(mScene);
					}
					createAndShowSettingsMenuIfNeed();
				}
			});
		}

	}

	private void createAndShowSettingsMenuIfNeed() {
		if (settingGame == null) {
			settingGame = new MenuSettingGameRectangle(this, pref, stringManger, 218 * ratio, 300 * ratio, 424 * ratio,
					600 * ratio, ratio, customFont, mSoundTextureRegion, mMusicTextureRegion, mBackRegion,
					getVertexBufferObjectManager());
			settingGame.setSoundCheckedChange(soundCheckedChange);
			settingGame.setMusicCheckedChange(musicCheckedChange);
			settingGame.setLanguageClick(languageCheckedChange);
		}
		settingGame.attachToScene(mScene);
		settingGame.setClickListenner(this);
		settingGame.show(new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				playSwitchSound();
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				lockUserAction(true);
			}
		});
		mCurrentMenuRectangle = settingGame;
	}

	private IClick languageCheckedChange = new IClick() {

		@Override
		public void onCLick(IAreaShape view) {
			playCLick();
			changeLanguage();
		}
	};

	private ICheckedChange soundCheckedChange = new ICheckedChange() {

		@Override
		public void onCheckedChange(boolean checked) {
			Log.d(tag, tag + " sound change " + checked);
			playCLick();
			Config.setSoundState(pref, checked ? Config.KEY_ON : Config.KEY_OFF);
		}
	};

	private ICheckedChange musicCheckedChange = new ICheckedChange() {

		@Override
		public void onCheckedChange(boolean checked) {
			Log.d(tag, tag + " music change " + checked);
			playCLick();
			Config.setMusicState(pref, checked ? Config.KEY_ON : Config.KEY_OFF);
			if (checked) {
				if (music != null && !music.isPlaying()) {
					playMusic();
				}
			} else {
				if (music != null && music.isPlaying()) {
					music.pause();
				}
			}
		}
	};

	protected void changeLanguage() {
		if (settingGame != null) {
			lockUserAction(true);
			settingGame.hide(new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

				}

				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					settingGame.show(new IEntityModifierListener() {

						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

						}

						@Override
						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
							lockUserAction(true);
						}
					});
				}
			});
		}
	}

	private Sound clickSound;
	private Music music;
	private Sound switchSound;

	private void loadSound() {
		SoundFactory.setAssetBasePath("sound/");
		try {
			clickSound = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
					"button_click.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void playSwitchSound() {
		if (switchSound != null && Config.getSoundState(pref) == Config.KEY_ON) {
			switchSound.play();
		} else if (clickSound == null && Config.getSoundState(pref) == Config.KEY_ON) {
			loadSwitchSound();
			switchSound.play();
		}
	}

	private void loadSwitchSound() {
		SoundFactory.setAssetBasePath("sound/");
		try {
			switchSound = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
					"switchsound.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void playCLick() {
		if (clickSound != null && Config.getSoundState(pref) == Config.KEY_ON) {
			clickSound.play();
		} else if (clickSound == null && Config.getSoundState(pref) == Config.KEY_ON) {
			loadSound();
			clickSound.play();
		}
	}

	private void loadMusic() {
		MusicFactory.setAssetBasePath("sound/");
		try {
			music = MusicFactory.createMusicFromAsset(getMusicManager(), getApplicationContext(), "bgsound.mp3");
			music.setLooping(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void playMusic() {
		// TODO Auto-generated method stub
		if (Config.getMusicState(pref) == Config.KEY_ON) {
			if (music == null) {
				loadMusic();
				music.play();
			} else {
				music.play();
			}
		}
	}

	protected void shareFb() {
		shareFacebookWithLogin(getString(R.string.app_name), getString(R.string.share_fb_msg),
				getString(R.string.share_fb_msg), "https://play.google.com/store/apps/details?id=" + getPackageName(),
				"https://raw.githubusercontent.com/tgioihan/BubbleMath/master/ic_launcher-web.png", new IShareFacebook() {

					@Override
					public void onShareSuccess() {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								toastOnUIThread(getString(R.string.share_fb_msg_success));
							}
						});
					}

					@Override
					public void onShareFail(int errorCode) {
						Log.d("", "share error " + errorCode);
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								toastOnUIThread(getString(R.string.share_fb_msg_fail));
							}
						});
					}
				});
	}
}
