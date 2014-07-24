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
import android.widget.Toast;

import com.bestfunforever.andengine.uikit.activity.PortraitAdmobGameActivity;
import com.bestfunforever.andengine.uikit.entity.TickerTextManagable;
import com.bestfunforever.andengine.uikit.entity.TickerTextManagable.ITickerTextListenner;
import com.bestfunforever.andengine.uikit.menu.BaseMenu.IOnMenuItemClickListener;
import com.bestfunforever.andengine.uikit.menu.IMenuItem;

public class BubbleGameActivity extends PortraitAdmobGameActivity implements IOnMenuItemClickListener {

	private TiledTextureRegion iconHighScoreMenuRegion;
	private AutoScrollHorizontalList mListView;
	private TiledTextureRegion childFaceRegion;
	private TextureRegion mBgTextureRegion;
	private TiledTextureRegion messageRegion;
	private Font mFont;
	private BitmapTextureAtlas customFontTexture;
	private StrokeFont customFont;

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas highscoreMenuAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (140),
				(int) (140), TextureOptions.BILINEAR);
		iconHighScoreMenuRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(highscoreMenuAtlas, this,
				"high_score.png", 0, 0, 1, 1);
		highscoreMenuAtlas.load();

		BitmapTextureAtlas childAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (195), (int) (192),
				TextureOptions.BILINEAR);
		childFaceRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(childAtlas, this,
				"child_face.png", 0, 0, 1, 1);
		childAtlas.load();

		BitmapTextureAtlas messageAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (356), (int) (152),
				TextureOptions.BILINEAR);
		messageRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(messageAtlas, this,
				"message_frame.png", 0, 0, 1, 1);
		messageAtlas.load();

		BitmapTextureAtlas mBgBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 640, 960,
				TextureOptions.BILINEAR);
		this.mBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBgBitmapTextureAtlas, this,
				"bg5.jpg", 0, 0); // 64x32
		mBgBitmapTextureAtlas.load();
		
		customFontTexture = new BitmapTextureAtlas(this.getTextureManager(),512, 512, TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		customFont = FontFactory.createStrokeFromAsset(getFontManager(),customFontTexture , getAssets(), "UVNBanhMi.TTF", (float)50*ratio, true, Color.WHITE, 2, Color.RED);
		customFont.load();
		
		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), (int) (256 * ratio),
				(int) (256 * ratio), Typeface.create(Typeface.DEFAULT, Typeface.BOLD), (int) (32 * ratio));
		this.mFont.load();
	}

	@Override
	protected Scene onCreateScene() {
		Scene scene = new Scene();
		scene.setBackground(new SpriteBackground(new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, mBgTextureRegion,
				getVertexBufferObjectManager())));
		this.mEngine.registerUpdateHandler(new FPSLogger());

		mListView = new AutoScrollHorizontalList(this, 10, 50, CAMERA_WIDTH - 20, iconHighScoreMenuRegion.getHeight()
				* ratio, getVertexBufferObjectManager());
		MathAdapter adapter = new MathAdapter(iconHighScoreMenuRegion, ratio, getVertexBufferObjectManager());
		scene.attachChild(mListView);
		mListView.setAdapter(adapter);
		mListView.setSelection(Integer.MAX_VALUE / 2);
		mListView.setScrollVelocity(-3f);

		MathExpanableMenu mMenu = new MathExpanableMenu(10, CAMERA_HEIGHT - 110 * ratio, this, mCamera, ratio);
		mMenu.init();
		mMenu.setOnMenuItemClickListener(this);
		scene.setChildScene(mMenu);

		Sprite child = new Sprite(30, mMenu.getMenuPositionY() - childFaceRegion.getHeight() * ratio,
				childFaceRegion.getWidth() * ratio, childFaceRegion.getHeight() * ratio, childFaceRegion,
				getVertexBufferObjectManager());
		scene.attachChild(child);
		Sprite messageFrame = new Sprite(child.getX() + child.getWidth() + 30 * ratio, mMenu.getMenuPositionY()
				- messageRegion.getHeight() * ratio, messageRegion.getWidth() * ratio, messageRegion.getHeight()
				* ratio, messageRegion, getVertexBufferObjectManager());
		scene.attachChild(messageFrame);
		
		TickerTextManagable tickTextManagable = new TickerTextManagable(10*ratio, 10*ratio, mFont, "Giup minh giai bai toan nay voi , kho qua , hu hu", new TickerTextOptions(AutoWrap.WORDS, messageFrame.getWidth()-2*10*ratio, HorizontalAlign.CENTER, 15), getVertexBufferObjectManager());
		tickTextManagable.setTickerTextListenner(new ITickerTextListenner() {
			
			@Override
			public void onTickerTextComplete() {
				mListView.setRun(true);
			}
		});
		messageFrame.attachChild(tickTextManagable);
		return scene;
	}

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
