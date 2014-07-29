package com.bestfunforever.game.bubblemath;

import java.util.Locale;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.bestfunforever.andengine.uikit.activity.PortraitAdmobGameActivity;
import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.andengine.uikit.entity.ISelector;
import com.bestfunforever.game.bubblemath.Entity.MainMenu.IMenuRectangle;
import com.bestfunforever.game.bubblemath.Entity.MainMenu.MenuModeGameRectang;
import com.bestfunforever.game.bubblemath.Entity.MainMenu.MenuRectang;
import com.bestfunforever.game.bubblemath.Entity.MainMenu.MenuSettingGameRectangle;

public class MainActivity extends PortraitAdmobGameActivity implements IClick {

	private BitmapTextureAtlas customFontTexture;
	private StrokeFont customFont;
	private TextureRegion mBgTextureRegion;
	private MenuRectang menu;
	private Scene mScene;
	private MenuModeGameRectang modeGame;
	private IMenuRectangle mCurrentMenuRectangle;
	private Font mFont;
	private BitmapTextureAtlas mSoundBitmapTextureAtlas;
	private TiledTextureRegion mSoundTextureRegion;
	private BitmapTextureAtlas mMusicBitmapTextureAtlas;
	private TiledTextureRegion mMusicTextureRegion;
	private MenuSettingGameRectangle settingGame;
	private TextureRegion mBackRegion;
	private StringManger stringManger;

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
	protected void onCreateResources() {
		FontFactory.setAssetBasePath("font/");
		customFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		// customFont = FontFactory.createStrokeFromAsset(getFontManager(),
		// customFontTexture, getAssets(), "MTCORSVA.ttf", (float) 100
		// * ratio, true, new org.andengine.util.color.Color(
		// 7f / 255, 132f / 255, 91f / 255).hashCode(), 2 * ratio,
		// Color.WHITE);
		customFont = new StrokeFont(getFontManager(), customFontTexture, Typeface.create(
				Typeface.DEFAULT_BOLD, Typeface.BOLD_ITALIC), (float) 60 * ratio, true,
				Color.RED, 1 * ratio, Color.WHITE, false);
		customFont.load();

		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), (int) (256 * ratio),
				(int) (256 * ratio), Typeface.create(Typeface.DEFAULT, Typeface.BOLD), (int) (32 * ratio));
		this.mFont.load();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas mBgBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 640, 960,
				TextureOptions.BILINEAR);
		this.mBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBgBitmapTextureAtlas, this,
				"main_bg.png", 0, 0); // 64x32
		mBgBitmapTextureAtlas.load();

		BitmapTextureAtlas backAtlas = new BitmapTextureAtlas(this.getTextureManager(), 81, 74, TextureOptions.BILINEAR);
		this.mBackRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backAtlas, this, "back.png", 0, 0); // 64x32
		backAtlas.load();

		this.mSoundBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 527, 388, TextureOptions.BILINEAR);
		this.mSoundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				this.mSoundBitmapTextureAtlas, this, "sound.png", 0, 0, 2, 1); // 64x32
		this.mSoundBitmapTextureAtlas.load();

		this.mMusicBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 527, 388, TextureOptions.BILINEAR);
		this.mMusicTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				this.mMusicBitmapTextureAtlas, this, "music.png", 0, 0, 2, 1); // 64x32
		this.mMusicBitmapTextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		mScene = new Scene();
		mScene.setBackground(new SpriteBackground(new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, mBgTextureRegion,
				getVertexBufferObjectManager())));
		this.mEngine.registerUpdateHandler(new FPSLogger());
		SharedPreferences pref = getSharedPreferences(Config.KEY_PREF, 0);
		Log.d("", "language "+Locale.getDefault().getDisplayLanguage() );
		if (Config.getLanguage(pref) == Config.KEY_LANGUAGE_NOTSET) {
			if (Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("VNI")) {
				Config.setLanguage(pref, Config.KEY_LANGUAGE_VNI);
			} else {
				Config.setLanguage(pref, Config.KEY_LANGUAGE_ENG);
			}
		}
		
		stringManger = new StringManger(this, Config.getLanguage(pref));

		return mScene;
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
		clearLastMenuAndCreateFirstMenu();
	}

	private void createAndShowMenuIfNeed() {
		if (menu == null) {
			menu = new MenuRectang(this, stringManger,218 * ratio, 300 * ratio, 424 * ratio, 450 * ratio, ratio, customFont,
					getVertexBufferObjectManager());
		}

		menu.attachToScene(mScene);
		menu.setClickListenner(this);
		menu.show(new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

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
			modeGame = new MenuModeGameRectang(stringManger,218 * ratio, 300 * ratio, 424 * ratio, 470 * ratio, ratio, customFont,
					mBackRegion, getVertexBufferObjectManager());
		}
		modeGame.attachToScene(mScene);
		modeGame.setClickListenner(this);
		modeGame.show(new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

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
			settingGame = new MenuSettingGameRectangle(stringManger,218 * ratio, 300 * ratio, 424 * ratio, 450 * ratio, ratio,
					customFont, mSoundTextureRegion, mMusicTextureRegion, mBackRegion, getVertexBufferObjectManager());
		}
		settingGame.attachToScene(mScene);
		settingGame.setClickListenner(this);
		settingGame.show(new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				lockUserAction(true);
			}
		});
		mCurrentMenuRectangle = settingGame;
	}

}
