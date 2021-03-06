package com.bestfunforever.game.bubblemath;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.modifier.IModifier;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bestfunforever.andengine.uikit.activity.PortraitAdmobGameActivity;
import com.bestfunforever.andengine.uikit.activity.facebook.ILoginFacebook;
import com.bestfunforever.andengine.uikit.activity.facebook.IShareFacebook;
import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.andengine.uikit.entity.Sprite.BubbleSprite;
import com.bestfunforever.andengine.uikit.entity.Sprite.SeekBar;
import com.bestfunforever.andengine.uikit.entity.Sprite.SeekBar.ISeekBarListenner;
import com.bestfunforever.andengine.uikit.entity.text.TickerTextExtension.TickerTextOptions;
import com.bestfunforever.andengine.uikit.entity.text.TickerTextManagable;
import com.bestfunforever.andengine.uikit.entity.text.TickerTextManagable.ITickerTextListenner;
import com.bestfunforever.andengine.uikit.menu.BaseMenu.IMenuListenner;
import com.bestfunforever.andengine.uikit.menu.BaseMenu.IOnMenuItemClickListener;
import com.bestfunforever.andengine.uikit.menu.CheckboxMenuItem;
import com.bestfunforever.andengine.uikit.menu.IMenuItem;
import com.bestfunforever.game.bubblemath.Entity.MainMenu.MathExpanableMenu;
import com.facebook.Session;
import com.facebook.SessionState;

public abstract class BubbleGameActivity extends PortraitAdmobGameActivity implements IOnMenuItemClickListener,
		ITickerTextListenner, ISeekBarListenner, IMenuListenner {

	protected TiledTextureRegion childFaceRegion;
	protected TextureRegion mBgTextureRegion;
	protected TiledTextureRegion messageRegion;
	protected Font mFont;
	protected BitmapTextureAtlas customFontTexture;
	protected StrokeFont customFont;
	protected Scene scene;
	protected BitmapTextureAtlas customFontBigTexture;
	protected StrokeFont customFontBig;
	protected Sprite child;
	protected Handler handler;
	protected TiledTextureRegion progressRegion;
	protected TiledTextureRegion starRegion;
	protected SeekBar mSeekBar;

	protected float marginTextMath = 30f;
	protected MathExpanableMenu mMenu;
	protected SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = getSharedPreferences(Config.KEY_PREF, 0);
		handler = new Handler();
	}

	private String[] childFaces = new String[] { "boy.png", "girl.png" };
	protected TickerTextManagable tickTextManagable;
	protected Sprite messageFrame;
	protected StringManger stringManger;
	private BitmapTextureAtlas smallFontTexture;
	protected TiledTextureRegion fbRegion;
	protected TiledTextureRegion replayRegion;
	protected TiledTextureRegion backRegion;

	protected float durationEndGame = 0.5f;
	private String tag;
	private SharedPreferences pref;

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		BitmapTextureAtlas mBgBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 640, 960,
				TextureOptions.BILINEAR);
		this.mBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBgBitmapTextureAtlas, this,
				"bg5.jpg", 0, 0); // 64x32
		mBgBitmapTextureAtlas.load();

		Random random = new Random(System.currentTimeMillis());

		BitmapTextureAtlas childAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (187), (int) (279),
				TextureOptions.BILINEAR);
		childFaceRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(childAtlas, this,
				childFaces[random.nextInt(2)], 0, 0, 1, 1);
		childAtlas.load();

		marginTextMath = marginTextMath * ratio;
	}

	private void loadResource() {
		BitmapTextureAtlas progressAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (461), (int) (45),
				TextureOptions.BILINEAR);
		progressRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(progressAtlas, this,
				"bg_progress.png", 0, 0, 1, 1);
		progressAtlas.load();

		BitmapTextureAtlas starAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (74), (int) (93),
				TextureOptions.BILINEAR);
		starRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(starAtlas, this, "star.png", 0, 0, 1,
				1);
		starAtlas.load();

		BitmapTextureAtlas messageAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (431), (int) (167),
				TextureOptions.BILINEAR);
		messageRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(messageAtlas, this,
				"message_frame.png", 0, 0, 1, 1);
		messageAtlas.load();

		BitmapTextureAtlas fbAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (84), (int) (84),
				TextureOptions.BILINEAR);
		fbRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fbAtlas, this, "fb.png", 0, 0, 1, 1);
		fbAtlas.load();

		BitmapTextureAtlas replayAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (84), (int) (84),
				TextureOptions.BILINEAR);
		replayRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(replayAtlas, this, "replay.png", 0,
				0, 1, 1);
		replayAtlas.load();

		BitmapTextureAtlas backAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (84), (int) (84),
				TextureOptions.BILINEAR);
		backRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(backAtlas, this, "back_menu.png", 0,
				0, 1, 1);
		backAtlas.load();

		customFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		customFont = FontFactory.createStrokeFromAsset(getFontManager(), customFontTexture, getAssets(),
				"MTCORSVA.ttf", (float) 80 * ratio, true, Color.WHITE, 3, Color.GREEN);
		customFont.load();

		customFontBigTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		customFontBig = FontFactory.createStrokeFromAsset(getFontManager(), customFontBigTexture, getAssets(),
				"MTCORSVA.ttf", (float) 200 * ratio, true, Color.WHITE, 3, Color.RED);
		customFontBig.load();

		smallFontTexture = new BitmapTextureAtlas(this.getTextureManager(), (int) (256 * ratio), (int) (256 * ratio),
				TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		mFont = FontFactory.createFromAsset(getFontManager(), smallFontTexture, getAssets(), "UVF_AguafinaScript.ttf",
				(int) (40 * ratio), true, Color.BLACK);
		mFont.load();

		loadSound();
		loadMusic();

		onLoadResource();
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
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		super.onResumeGame();
		playMusic();
	}

	@Override
	protected Scene onCreateScene() {
		scene = new Scene();
		scene.setBackground(new SpriteBackground(new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, mBgTextureRegion,
				getVertexBufferObjectManager())));
		this.mEngine.registerUpdateHandler(new FPSLogger());
		pref = getSharedPreferences(Config.KEY_PREF, 0);
		if (Config.getLanguage(pref) == Config.KEY_LANGUAGE_NOTSET) {
			if (Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("VNI")) {
				Config.setLanguage(pref, Config.KEY_LANGUAGE_VNI);
			} else {
				Config.setLanguage(pref, Config.KEY_LANGUAGE_ENG);
			}
		}

		stringManger = new StringManger(this);
		mEngine.registerUpdateHandler(new TimerHandler(1, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				loadResource();
				createScene();
			}
		}));

		return scene;
	}

	private void createScene() {
		mMenu = new MathExpanableMenu(10, CAMERA_HEIGHT - 110 * ratio, this, mCamera, ratio);
		mMenu.init();
		mMenu.setOnMenuItemClickListener(this);
		mMenu.setMenuListenner(this);
		scene.setChildScene(mMenu);

		child = new Sprite(0, mMenu.getMenuPositionY() - childFaceRegion.getHeight() * ratio,
				childFaceRegion.getWidth() * ratio, childFaceRegion.getHeight() * ratio, childFaceRegion,
				getVertexBufferObjectManager());

		scene.attachChild(child);

		mSeekBar = new SeekBar(this, 0, 50 * ratio, progressRegion.getWidth() * ratio, progressRegion.getHeight()
				* ratio, progressRegion, 9f * ratio, org.andengine.util.color.Color.GREEN, starRegion, ratio,
				getVertexBufferObjectManager());
		mSeekBar.setX(CAMERA_WIDTH / 2 - mSeekBar.getWidth() / 2);
		// mSeekBar.setPercent(50);
		scene.attachChild(mSeekBar);
		mSeekBar.setSeekBarListenner(this);

		messageFrame = new Sprite(child.getX() + child.getWidth() + 12 * ratio, mMenu.getMenuPositionY()
				- messageRegion.getHeight() * ratio, messageRegion.getWidth() * ratio, messageRegion.getHeight()
				* ratio, messageRegion, getVertexBufferObjectManager());
		scene.attachChild(messageFrame);

		tickTextManagable = new TickerTextManagable(10 * ratio, 10 * ratio, mFont, "", 100, new TickerTextOptions(
				AutoWrap.WORDS, messageFrame.getWidth() - 2 * 10 * ratio, HorizontalAlign.CENTER, 15),
				getVertexBufferObjectManager());
		tickTextManagable.setTickerTextListenner(this);
		messageFrame.attachChild(tickTextManagable);
		initMathGraphicFrame();
	}

	public void showEndGame() {
		onEndGame();
		child.registerEntityModifier(new AlphaModifier(durationEndGame, 1, 0));
		messageFrame.registerEntityModifier(new AlphaModifier(durationEndGame, 1, 0, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				float center = CAMERA_HEIGHT / 2;
				messageFrame.setY(center - messageFrame.getHeight() / 2);
				child.setY(messageFrame.getY() + messageFrame.getHeight() - child.getHeight());
				child.registerEntityModifier(new AlphaModifier(durationEndGame, 0, 1));
				messageFrame.registerEntityModifier(new AlphaModifier(durationEndGame, 0, 1,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

							}

							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
								tickTextManagable.setText(stringManger.getStringFromKey(StringManger.CONGRATULATION));
								tickTextManagable.setY(messageFrame.getHeight() / 2 - tickTextManagable.getHeight() / 2);
							}
						}));
			}
		}));

	}

	protected boolean endgame = false;

	@Override
	public void onTickerTextComplete() {
		Log.d(tag,
				tag + " onTickerTextComplete " + tickTextManagable.getText()
						+ (!tickTextManagable.getText().equals("")));
		if (endgame) {
			createObjectEndGameIfNeed();
		}
	}

	private float paddingEndGame = 20f;
	private BubbleSprite fbSprite;
	private BubbleSprite replaySprite;
	private BubbleSprite backSprite;

	private void createObjectEndGameIfNeed() {
		if (fbSprite == null) {
			float pY = messageFrame.getY() + messageFrame.getHeight();
			fbSprite = new BubbleSprite(paddingEndGame * ratio + messageFrame.getX(), pY, ratio, fbRegion,
					getVertexBufferObjectManager());
			replaySprite = new BubbleSprite(0, pY, ratio, replayRegion, getVertexBufferObjectManager());
			replaySprite.setX(messageFrame.getWidth() / 2 - replaySprite.getWidth() / 2 + messageFrame.getX());
			backSprite = new BubbleSprite(0, pY, ratio, backRegion, getVertexBufferObjectManager());
			backSprite.setX(messageFrame.getWidth() - paddingEndGame * ratio + messageFrame.getX()
					- backSprite.getWidth());
			fbSprite.setClickListenner(new IClick() {

				@Override
				public void onCLick(IAreaShape view) {
					playCLick();
					shareFb();
				}
			});
			replaySprite.setClickListenner(new IClick() {

				@Override
				public void onCLick(IAreaShape view) {
					playCLick();
					replay();
				}
			});
			backSprite.setClickListenner(new IClick() {

				@Override
				public void onCLick(IAreaShape view) {
					playCLick();
					finish();
				}
			});
			scene.attachChild(fbSprite);
			scene.attachChild(replaySprite);
			scene.attachChild(backSprite);
		}
		scene.registerTouchArea(fbSprite);
		scene.registerTouchArea(replaySprite);
		scene.registerTouchArea(backSprite);
		fbSprite.setAlpha(1);
		replaySprite.setAlpha(1);
		backSprite.setAlpha(1);
		fbSprite.registerEntityModifier(new ScaleModifier(durationEndGame, 0, 1));
		replaySprite.registerEntityModifier(new ScaleModifier(durationEndGame, 0, 1));
		backSprite.registerEntityModifier(new ScaleModifier(durationEndGame, 0, 1));

	}

	public void closeEndGame() {
		fbSprite.registerEntityModifier(new AlphaModifier(durationEndGame, 1, 0));
		replaySprite.registerEntityModifier(new AlphaModifier(durationEndGame, 1, 0));
		backSprite.registerEntityModifier(new AlphaModifier(durationEndGame, 1, 0));
		child.registerEntityModifier(new AlphaModifier(durationEndGame, 1, 0));
		scene.unregisterTouchArea(fbSprite);
		scene.unregisterTouchArea(replaySprite);
		scene.unregisterTouchArea(backSprite);
		messageFrame.registerEntityModifier(new AlphaModifier(durationEndGame, 1, 0, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				messageFrame.setY(mMenu.getMenuPositionY() - messageRegion.getHeight() * ratio);
				child.setY(messageFrame.getY() + messageFrame.getHeight() - child.getHeight());
				child.registerEntityModifier(new AlphaModifier(durationEndGame, 0, 1));
				messageFrame.registerEntityModifier(new AlphaModifier(durationEndGame, 0, 1,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

							}

							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
								onCloseEndGame();
							}
						}));
			}
		}));
	}

	protected void replay() {
		endgame = false;
		closeEndGame();
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
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								toastOnUIThread(getString(R.string.share_fb_msg_fail));
							}
						});
					}
				});
	}

	protected abstract void onCloseEndGame();

	protected abstract void onEndGame();

	protected abstract void initMathGraphicFrame();

	protected abstract void onLoadResource();

	protected abstract void lockUserAction(boolean enable);

	@Override
	public boolean onMenuItemClicked(HUD pMenuScene, final IMenuItem pMenuItem) {
		int id = pMenuItem.getId();
		playCLick();
		switch (id) {
		case MathExpanableMenu.MENU_SOUND:
			playCLick();
			((CheckboxMenuItem) pMenuItem).setChecked(!((CheckboxMenuItem) pMenuItem).isChecked());
			boolean checkedSound = ((CheckboxMenuItem) pMenuItem).isChecked();
			Config.setSoundState(pref, checkedSound ? Config.KEY_ON : Config.KEY_OFF);
			break;
		case MathExpanableMenu.MENU_MUSIC:
			playCLick();
			((CheckboxMenuItem) pMenuItem).setChecked(!((CheckboxMenuItem) pMenuItem).isChecked());
			boolean checkedMusic = ((CheckboxMenuItem) pMenuItem).isChecked();
			Config.setMusicState(pref, checkedMusic ? Config.KEY_ON : Config.KEY_OFF);
			if (checkedMusic) {
				if (music != null && !music.isPlaying()) {
					playMusic();
				}
			} else {
				if (music != null && music.isPlaying()) {
					music.pause();
				}
			}
			break;

		case MathExpanableMenu.MENU_BACK:
			finish();
			break;
		default:
			break;
		}

		return false;
	}

	private Sound clickSound;
	private Music music;
	private Sound switchSound;
	private Sound wrongSound;
	private Sound correctsound;

	protected void loadSound() {
		SoundFactory.setAssetBasePath("sound/");
		try {
			clickSound = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
					"button_click.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void playCLick() {
		if (clickSound != null && Config.getSoundState(pref) == Config.KEY_ON) {
			clickSound.play();
		} else if (clickSound == null && Config.getSoundState(pref) == Config.KEY_ON) {
			loadSound();
			clickSound.play();
		}
	}

	protected void loadMusic() {
		MusicFactory.setAssetBasePath("sound/");
		try {
			music = MusicFactory.createMusicFromAsset(getMusicManager(), getApplicationContext(), "bgsound.mp3");
			music.setLooping(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void playMusic() {
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

	protected void playSwitchSound() {
		if (switchSound != null && Config.getSoundState(pref) == Config.KEY_ON) {
			switchSound.play();
		} else if (clickSound == null && Config.getSoundState(pref) == Config.KEY_ON) {
			loadSwitchSound();
			switchSound.play();
		}
	}

	protected void loadSwitchSound() {
		SoundFactory.setAssetBasePath("sound/");
		try {
			switchSound = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
					"switchsound.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void playWrongSound() {
		if (wrongSound != null && Config.getSoundState(pref) == Config.KEY_ON) {
			wrongSound.play();
		} else if (wrongSound == null && Config.getSoundState(pref) == Config.KEY_ON) {
			loadWrongSound();
			wrongSound.play();
		}
	}

	protected void loadWrongSound() {
		SoundFactory.setAssetBasePath("sound/");
		try {
			wrongSound = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
					"49948__simon-rue__misslyckad-bana-v5.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void playCorrectSound() {
		if (correctsound != null && Config.getSoundState(pref) == Config.KEY_ON) {
			correctsound.play();
		} else if (correctsound == null && Config.getSoundState(pref) == Config.KEY_ON) {
			loadCorrectSound();
			correctsound.play();
		}
	}

	protected void loadCorrectSound() {
		SoundFactory.setAssetBasePath("sound/");
		try {
			correctsound = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
					"correctsound.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected String getAdmobKey() {
		// TODO Auto-generated method stub
		return getString(R.string.admobkey);
	}

	@Override
	public void onShow() {
		playCLick();
	}

	@Override
	public void onHide() {
		playCLick();
	}

}
