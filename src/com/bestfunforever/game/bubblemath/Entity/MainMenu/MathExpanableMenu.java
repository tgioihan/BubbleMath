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

import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.andengine.uikit.entity.Sprite.BubbleSprite;
import com.bestfunforever.andengine.uikit.menu.ExpandableMenu;
import com.bestfunforever.andengine.uikit.menu.MenuItem;

public class MathExpanableMenu extends ExpandableMenu {
	private TiledTextureRegion iconHighScoreMenuRegion;
	private float pX;
	private float pY;

	public MathExpanableMenu(SimpleBaseGameActivity context, Camera mCamera, float ratio) {
		this(0, 0, context, mCamera, ratio);
	}

	public MathExpanableMenu(float pX, float pY, SimpleBaseGameActivity context, Camera mCamera, float ratio) {
		super(context, mCamera, ratio);
		this.pX = pX;
		this.pY = pY;
	}
	
	public float getMenuPositionX(){
		return pX;
	}
	
	public float getMenuPositionY(){
		return pY;
	}

	@Override
	public void onLoadResource() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas highscoreMenuAtlas = new BitmapTextureAtlas(context.getTextureManager(), (int) (140),
				(int) (140), TextureOptions.BILINEAR);
		iconHighScoreMenuRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(highscoreMenuAtlas,
				context, "high_score.png", 0, 0, 1, 1);
		highscoreMenuAtlas.load();
	}

	@Override
	public void onCreate() {
		mItemLayer = new Rectangle(0, 0, camera_width, camera_height, context.getVertexBufferObjectManager());
		mItemLayer.setColor(Color.TRANSPARENT);
		attachChild(mItemLayer);

		mControl = new BubbleSprite(pX, pY, iconHighScoreMenuRegion.getWidth() * ratio,
				iconHighScoreMenuRegion.getHeight() * ratio, iconHighScoreMenuRegion,
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
		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		for (int i = 0; i < 3; i++) {
			MenuItem menuItem1 = new MenuItem(i, iconHighScoreMenuRegion.getWidth() * ratio,
					iconHighScoreMenuRegion.getHeight() * ratio, null, null, iconHighScoreMenuRegion,
					context.getVertexBufferObjectManager());
			list.add(menuItem1);
		}
		addMenuItem(list);

	}

}
