package com.bestfunforever.game.bubblemath;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBounceOut;

public class FruitCountMode extends BubbleGameActivity {

	private TiledTextureRegion greenBubbleRegion;
	private TiledTextureRegion appleRegion;
	private TiledTextureRegion bananaRegion;
	private TiledTextureRegion cherryRegion;
	private TiledTextureRegion pinearappleRegion;
	private TiledTextureRegion mangoRegion;
	private TiledTextureRegion strawberryRegion;
	private float animDuration = 0.5f;

	@Override
	public void onTickerTextComplete() {

	}

	@Override
	public void onStartSeeking(float percent, boolean userTouch) {

	}

	@Override
	public void onSeeking(float percent, boolean userTouch) {

	}

	@Override
	public void onFinishSeek(float percent, boolean userTouch) {

	}

	@Override
	protected void initMathGraphicFrame() {
		initBubbleAnswerList();

	}

	private void initBubbleAnswerList() {
		ArrayList<Item> mList = new ArrayList<Item>(4);

		int width = 0;
		for (int i = 0; i < 4; i++) {
			Item item = new Item(ratio, customFont, greenBubbleRegion,
					getVertexBufferObjectManager());
			item.setX(width);
			if (i < 3)
				width += item.getWidth() + 20 * ratio;
			else
				width += item.getWidth();
			mList.add(item);
		}
		float tmp = CAMERA_WIDTH / 2 - width / 2;
		for (int i = 0; i < 4; i++) {
			Item item = mList.get(i);
			item.setX(item.getX() + tmp);
			item.setScale(0);
			item.setText(i);
			scene.attachChild(item);
			if (i < 3) {
				item.registerEntityModifier(new ScaleModifier(animDuration, 1,
						0, EaseBounceOut.getInstance()));
			} else {
				item.registerEntityModifier(new ScaleModifier(animDuration, 1,
						0, new IEntityModifierListener() {

							@Override
							public void onModifierStarted(
									IModifier<IEntity> pModifier, IEntity pItem) {

							}

							@Override
							public void onModifierFinished(
									IModifier<IEntity> pModifier, IEntity pItem) {

							}
						}, EaseBounceOut.getInstance()));
			}
		}

	}

	@Override
	protected void onLoadResource() {
		BitmapTextureAtlas highscoreMenuAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), (int) (140), (int) (140),
				TextureOptions.BILINEAR);
		greenBubbleRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(highscoreMenuAtlas, this,
						"circle_green.png", 0, 0, 1, 1);
		highscoreMenuAtlas.load();

		BitmapTextureAtlas fruitAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), (int) (118), (int) (708),
				TextureOptions.BILINEAR);
		appleRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(fruitAtlas, this, "fruit_art.png", 0, 0,
						1, 1);
		bananaRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(fruitAtlas, this, "fruit_art.png", 0,
						118, 1, 1);
		cherryRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(fruitAtlas, this, "fruit_art.png", 0,
						236, 1, 1);
		pinearappleRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(fruitAtlas, this, "fruit_art.png", 0,
						354, 1, 1);
		mangoRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(fruitAtlas, this, "fruit_art.png", 0,
						472, 1, 1);
		strawberryRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(fruitAtlas, this, "fruit_art.png", 0,
						590, 1, 1);
		fruitAtlas.load();

	}

}
