package com.bestfunforever.game.bubblemath;

import java.util.Locale;
import java.util.Random;

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
import android.widget.Toast;

import com.bestfunforever.andengine.uikit.activity.PortraitAdmobGameActivity;
import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.andengine.uikit.entity.Sprite.BubbleSprite;
import com.bestfunforever.andengine.uikit.entity.Sprite.SeekBar;
import com.bestfunforever.andengine.uikit.entity.Sprite.SeekBar.ISeekBarListenner;
import com.bestfunforever.andengine.uikit.entity.text.TickerTextExtension.TickerTextOptions;
import com.bestfunforever.andengine.uikit.entity.text.TickerTextManagable;
import com.bestfunforever.andengine.uikit.entity.text.TickerTextManagable.ITickerTextListenner;
import com.bestfunforever.andengine.uikit.menu.BaseMenu.IOnMenuItemClickListener;
import com.bestfunforever.andengine.uikit.menu.IMenuItem;
import com.bestfunforever.game.bubblemath.Entity.MainMenu.MathExpanableMenu;

public abstract class BubbleGameActivity extends PortraitAdmobGameActivity implements IOnMenuItemClickListener,
		ITickerTextListenner, ISeekBarListenner {

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

		onLoadResource();
	}

	@Override
	protected Scene onCreateScene() {
		scene = new Scene();
		scene.setBackground(new SpriteBackground(new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, mBgTextureRegion,
				getVertexBufferObjectManager())));
		this.mEngine.registerUpdateHandler(new FPSLogger());
		SharedPreferences pref = getSharedPreferences(Config.KEY_PREF, 0);
		if (Config.getLanguage(pref) == Config.KEY_LANGUAGE_NOTSET) {
			if (Locale.getDefault().getDisplayLanguage().equalsIgnoreCase("VNI")) {
				Config.setLanguage(pref, Config.KEY_LANGUAGE_VNI);
			} else {
				Config.setLanguage(pref, Config.KEY_LANGUAGE_ENG);
			}
		}

		stringManger = new StringManger(this, Config.getLanguage(pref));
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
				child.setY(mMenu.getMenuPositionY() - childFaceRegion.getHeight() * ratio);
				child.registerEntityModifier(new AlphaModifier(durationEndGame, 0, 1));
				messageFrame.registerEntityModifier(new AlphaModifier(durationEndGame, 0, 1,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

							}

							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
								tickTextManagable.setText("Chuc mung ban da vuot qua dc phan nay ");
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
			fbSprite = new BubbleSprite(paddingEndGame * ratio+messageFrame.getX(), pY, ratio, fbRegion, getVertexBufferObjectManager());
			replaySprite = new BubbleSprite(0, pY, ratio, replayRegion, getVertexBufferObjectManager());
			replaySprite.setX(messageFrame.getWidth() / 2 - replaySprite.getWidth() / 2+messageFrame.getX());
			backSprite = new BubbleSprite(messageFrame.getWidth() - paddingEndGame * ratio+messageFrame.getX(), pY, ratio, backRegion,
					getVertexBufferObjectManager());
			fbSprite.setClickListenner(new IClick() {

				@Override
				public void onCLick(IAreaShape view) {
					shareFb();
				}
			});
			replaySprite.setClickListenner(new IClick() {

				@Override
				public void onCLick(IAreaShape view) {
					replay();
				}
			});
			backSprite.setClickListenner(new IClick() {

				@Override
				public void onCLick(IAreaShape view) {
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
		fbSprite.registerEntityModifier(new ScaleModifier(durationEndGame, 0, 1));
		replaySprite.registerEntityModifier(new ScaleModifier(durationEndGame, 0, 1));
		backSprite.registerEntityModifier(new ScaleModifier(durationEndGame, 0, 1));
		
	}

	public void closeEndGame() {
		fbSprite.registerEntityModifier(new ScaleModifier(durationEndGame, 1, 0));
		replaySprite.registerEntityModifier(new ScaleModifier(durationEndGame, 1, 0));
		backSprite.registerEntityModifier(new ScaleModifier(durationEndGame, 1, 0));
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
				child.setY(messageFrame.getY() + messageFrame.getHeight() - child.getHeight());
				messageFrame.setY(mMenu.getMenuPositionY()
						- messageRegion.getHeight() * ratio);
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
		// TODO Auto-generated method stub

	}

	protected abstract void onCloseEndGame();

	protected abstract void onEndGame();

	protected abstract void initMathGraphicFrame();

	protected abstract void onLoadResource();

	protected abstract void lockUserAction(boolean enable);

	@Override
	public boolean onMenuItemClicked(HUD pMenuScene, final IMenuItem pMenuItem) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(BubbleGameActivity.this, "onMenuItemClicked " + pMenuItem.getID(), Toast.LENGTH_SHORT)
						.show();
			}
		});

		return false;
	}

}
