package com.bestfunforever.game.bubblemath.Entity.MainMenu;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.content.SharedPreferences;

import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.andengine.uikit.entity.Sprite.BubbleSprite;
import com.bestfunforever.andengine.uikit.menu.CheckboxMenuItem;
import com.bestfunforever.andengine.uikit.menu.ExpandableMenu;
import com.bestfunforever.andengine.uikit.menu.IMenuItem;
import com.bestfunforever.andengine.uikit.menu.MenuItem;
import com.bestfunforever.game.bubblemath.Config;

public class MathExpanableMenu extends ExpandableMenu {
	private TiledTextureRegion menuRegion;
	private float pX;
	private float pY;
	private TiledTextureRegion backRegion;
	private TiledTextureRegion mSoundTextureRegion;
	private TiledTextureRegion mMusicTextureRegion;
	private SharedPreferences pref;

	public static final int MENU_SOUND = 1;
	public static final int MENU_MUSIC = 2;
	public static final int MENU_BACK = 3;

	public MathExpanableMenu(SimpleBaseGameActivity context, Camera mCamera, float ratio) {
		this(0, 0, context, mCamera, ratio);
	}

	public MathExpanableMenu(float pX, float pY, SimpleBaseGameActivity context, Camera mCamera, float ratio) {
		super(context, mCamera, ratio);
		this.pX = pX;
		this.pY = pY;
		pref = context.getSharedPreferences(Config.KEY_PREF, 0);
	}

	public float getMenuPositionX() {
		return pX;
	}

	public float getMenuPositionY() {
		return pY;
	}

	@Override
	public void onLoadResource() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas menuIconAtlas = new BitmapTextureAtlas(context.getTextureManager(), (int) (84), (int) (84),
				TextureOptions.BILINEAR);
		menuRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menuIconAtlas, context, "menu.png", 0,
				0, 1, 1);
		menuIconAtlas.load();

		BitmapTextureAtlas backAtlas = new BitmapTextureAtlas(context.getTextureManager(), (int) (84), (int) (84),
				TextureOptions.BILINEAR);
		backRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(backAtlas, context, "back_menu.png",
				0, 0, 1, 1);
		backAtlas.load();

		BitmapTextureAtlas mSoundBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 527, 388,
				TextureOptions.BILINEAR);
		this.mSoundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mSoundBitmapTextureAtlas, context, "sound.png", 0, 0, 2, 1); // 64x32
		mSoundBitmapTextureAtlas.load();

		BitmapTextureAtlas mMusicBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 527, 388,
				TextureOptions.BILINEAR);
		this.mMusicTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mMusicBitmapTextureAtlas, context, "music.png", 0, 0, 2, 1); // 64x32
		mMusicBitmapTextureAtlas.load();

	}

	@Override
	public void onCreate() {
		mItemLayer = new Rectangle(0, 0, camera_width, camera_height, context.getVertexBufferObjectManager());
		mItemLayer.setColor(Color.TRANSPARENT);
		attachChild(mItemLayer);

		mControl = new BubbleSprite(pX, pY, menuRegion.getWidth() * ratio, menuRegion.getHeight() * ratio, menuRegion,
				context.getVertexBufferObjectManager());
		mControl.setClickListenner(new IClick() {

			@Override
			public void onCLick(IAreaShape view) {
				// TODO Auto-generated method stub
				if (stage == STAGE.HIDE) {
					show();
				} else if (stage == STAGE.SHOW) {
					hide();
				}
			}
		});
		attachChild(mControl);
		registerTouchArea(mControl);
		ArrayList<IMenuItem> list = new ArrayList<IMenuItem>();
		CheckboxMenuItem soundMenu = new CheckboxMenuItem(MENU_SOUND, 0, 0, ratio, Config.getSoundState(pref) == Config.KEY_ON, mSoundTextureRegion,
				context.getVertexBufferObjectManager());
		CheckboxMenuItem musicMenu = new CheckboxMenuItem(MENU_MUSIC, 0, 0,soundMenu.getWidth(),soundMenu.getHeight(), Config.getMusicState(pref) == Config.KEY_ON, mMusicTextureRegion,
				context.getVertexBufferObjectManager());
		MenuItem backItem = new MenuItem(MENU_BACK, menuRegion.getWidth() * ratio, menuRegion.getHeight() * ratio,
				null, null, backRegion, context.getVertexBufferObjectManager());
		list.add(soundMenu);
		list.add(musicMenu);
		list.add(backItem);
		addMenuItem(list);

	}

}
