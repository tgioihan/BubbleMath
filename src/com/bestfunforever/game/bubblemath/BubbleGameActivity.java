package com.bestfunforever.game.bubblemath;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.TickerText.TickerTextOptions;
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

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.bestfunforever.andengine.uikit.activity.PortraitAdmobGameActivity;
import com.bestfunforever.andengine.uikit.entity.SeekBar;
import com.bestfunforever.andengine.uikit.entity.SeekBar.ISeekBarListenner;
import com.bestfunforever.andengine.uikit.entity.TickerTextManagable;
import com.bestfunforever.andengine.uikit.entity.TickerTextManagable.ITickerTextListenner;
import com.bestfunforever.andengine.uikit.menu.BaseMenu.IOnMenuItemClickListener;
import com.bestfunforever.andengine.uikit.menu.IMenuItem;

public abstract class BubbleGameActivity extends PortraitAdmobGameActivity
		implements IOnMenuItemClickListener, ITickerTextListenner,
		ISeekBarListenner {

	protected TiledTextureRegion childFaceRegion;
	protected TextureRegion mBgTextureRegion;
	protected TiledTextureRegion messageRegion;
	protected Font mFont;
	protected BitmapTextureAtlas customFontTexture;
	protected StrokeFont customFont;
	protected Game mGame;
	protected Scene scene;
	protected BitmapTextureAtlas customFontBigTexture;
	protected StrokeFont customFontBig;
	protected Sprite child;
	protected Handler handler;
	protected TiledTextureRegion progressRegion;
	protected TiledTextureRegion starRegion;
	protected SeekBar mSeekBar;

	protected float marginTextMath = 30f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handler = new Handler();
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		BitmapTextureAtlas progressAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), (int) (461), (int) (45),
				TextureOptions.BILINEAR);
		progressRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(progressAtlas, this, "bg_progress.png",
						0, 0, 1, 1);
		progressAtlas.load();

		BitmapTextureAtlas starAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), (int) (74), (int) (93),
				TextureOptions.BILINEAR);
		starRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(starAtlas, this, "star.png", 0, 0, 1, 1);
		starAtlas.load();

		BitmapTextureAtlas childAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), (int) (195), (int) (192),
				TextureOptions.BILINEAR);
		childFaceRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(childAtlas, this, "child_face.png", 0, 0,
						1, 1);
		childAtlas.load();

		BitmapTextureAtlas messageAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), (int) (356), (int) (152),
				TextureOptions.BILINEAR);
		messageRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(messageAtlas, this, "message_frame.png",
						0, 0, 1, 1);
		messageAtlas.load();

		BitmapTextureAtlas mBgBitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 640, 960, TextureOptions.BILINEAR);
		this.mBgTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBgBitmapTextureAtlas, this, "bg5.jpg", 0, 0); // 64x32
		mBgBitmapTextureAtlas.load();

		customFontTexture = new BitmapTextureAtlas(this.getTextureManager(),
				512, 512, TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		customFont = FontFactory.createStrokeFromAsset(getFontManager(),
				customFontTexture, getAssets(), "UVNBanhMi.TTF", (float) 80
						* ratio, true, Color.WHITE, 2, Color.RED);
		customFont.load();

		customFontBigTexture = new BitmapTextureAtlas(this.getTextureManager(),
				1024, 1024, TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		customFontBig = FontFactory.createStrokeFromAsset(getFontManager(),
				customFontBigTexture, getAssets(), "UVNBanhMi.TTF", (float) 145
						* ratio, true, Color.WHITE, 2, Color.RED);
		customFontBig.load();

		this.mFont = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), (int) (256 * ratio),
				(int) (256 * ratio),
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD),
				(int) (32 * ratio));
		this.mFont.load();

		onLoadResource();

		marginTextMath = marginTextMath * ratio;
	}

	@Override
	protected Scene onCreateScene() {
		scene = new Scene();
		mGame = new Game();
		scene.setBackground(new SpriteBackground(
				new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, mBgTextureRegion,
						getVertexBufferObjectManager())));
		this.mEngine.registerUpdateHandler(new FPSLogger());

		mSeekBar = new SeekBar(this, 0, 50 * ratio, progressRegion.getWidth()
				* ratio, progressRegion.getHeight() * ratio, progressRegion,
				9f * ratio, org.andengine.util.color.Color.GREEN, starRegion,
				ratio, getVertexBufferObjectManager());
		mSeekBar.setX(CAMERA_WIDTH / 2 - mSeekBar.getWidth() / 2);
		// mSeekBar.setPercent(50);
		scene.attachChild(mSeekBar);
		mSeekBar.setSeekBarListenner(this);

		MathExpanableMenu mMenu = new MathExpanableMenu(10, CAMERA_HEIGHT - 110
				* ratio, this, mCamera, ratio);
		mMenu.init();
		mMenu.setOnMenuItemClickListener(this);
		scene.setChildScene(mMenu);

		child = new Sprite(30, mMenu.getMenuPositionY()
				- childFaceRegion.getHeight() * ratio,
				childFaceRegion.getWidth() * ratio, childFaceRegion.getHeight()
						* ratio, childFaceRegion,
				getVertexBufferObjectManager());
		scene.attachChild(child);
		Sprite messageFrame = new Sprite(child.getX() + child.getWidth() + 30
				* ratio, mMenu.getMenuPositionY() - messageRegion.getHeight()
				* ratio, messageRegion.getWidth() * ratio,
				messageRegion.getHeight() * ratio, messageRegion,
				getVertexBufferObjectManager());
		scene.attachChild(messageFrame);

		TickerTextManagable tickTextManagable = new TickerTextManagable(
				10 * ratio, 10 * ratio, mFont,
				"Giup minh giai bai toan nay voi , kho qua , hu hu",
				new TickerTextOptions(AutoWrap.WORDS, messageFrame.getWidth()
						- 2 * 10 * ratio, HorizontalAlign.CENTER, 15),
				getVertexBufferObjectManager());
		tickTextManagable.setTickerTextListenner(this);
		messageFrame.attachChild(tickTextManagable);

		initMathGraphicFrame();

		return scene;
	}

	protected abstract void initMathGraphicFrame();

	protected abstract void onLoadResource();

	@Override
	public boolean onMenuItemClicked(HUD pMenuScene, final IMenuItem pMenuItem) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(BubbleGameActivity.this,
						"onMenuItemClicked " + pMenuItem.getID(),
						Toast.LENGTH_SHORT).show();
			}
		});

		return false;
	}

}
