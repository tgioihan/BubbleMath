package com.bestfunforever.game.bubblemath.Entity.MainMenu;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseBackIn;
import org.andengine.util.modifier.ease.EaseBounceOut;

import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.andengine.uikit.entity.Sprite.BaseSprite;
import com.bestfunforever.andengine.uikit.entity.Sprite.ClipingRectangle;
import com.bestfunforever.andengine.uikit.entity.Sprite.SpriteImplement;
import com.bestfunforever.andengine.uikit.entity.text.TextExtension;
import com.bestfunforever.game.bubblemath.Config;
import com.bestfunforever.game.bubblemath.StringManger;

public class MenuRectang extends ClipingRectangle implements IMenuRectangle {
	private BaseSprite mStart;
	private BaseSprite mSetting;
	private BaseSprite mMore;
	private float margin = 10;
	private TextureRegion mStartRegion;
	private TextureRegion mMoreRegion;
	private TextureRegion mSettingRegion;

	public MenuRectang(SimpleBaseGameActivity context,StringManger stringManger, float pX, float pY, float pWidth, float pHeight, float ratio,
			Font font, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		setColor(Color.TRANSPARENT);
		
		onLoadResource(context);

		mStart = new SpriteImplement(0, 0, mStartRegion.getWidth()*ratio,mStartRegion.getHeight()*ratio,mStartRegion, pVertexBufferObjectManager);
		mSetting = new SpriteImplement(0, mStart.getY() + mStart.getHeight() + margin * ratio, mSettingRegion.getWidth()*ratio,mSettingRegion.getHeight()*ratio,mSettingRegion, pVertexBufferObjectManager);
		mMore = new SpriteImplement(0, mSetting.getY() + mSetting.getHeight() + margin * ratio, mMoreRegion.getWidth()*ratio,mMoreRegion.getHeight()*ratio,mMoreRegion, pVertexBufferObjectManager);

		float height = mSetting.getY() + mSetting.getHeight() + mMore.getHeight();
		float disY = pHeight / 2 - height / 2;
		mStart.setY(mStart.getY() + disY);
		mSetting.setY(mSetting.getY() + disY);
		mMore.setY(mMore.getY() + disY);
		mMore.setX(-mMore.getWidth());
		mStart.setX(-mStart.getWidth());
		mSetting.setX(pWidth);
		mStart.setId(Config.MENU__START);
		mSetting.setId(Config.MENU__SETTings);
		mMore.setId(Config.MENU__MORE);
		attachChild(mStart);
		attachChild(mSetting);
		attachChild(mMore);
	}

	private void onLoadResource(SimpleBaseGameActivity context) {
		BitmapTextureAtlas startAtlas = new BitmapTextureAtlas(context.getTextureManager(), 165, 61, TextureOptions.BILINEAR);
		this.mStartRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(startAtlas, context, "start.png", 0, 0); // 64x32
		startAtlas.load();	
		
		BitmapTextureAtlas settingAtlas = new BitmapTextureAtlas(context.getTextureManager(), 252, 79, TextureOptions.BILINEAR);
		this.mSettingRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingAtlas, context, "settings.png", 0, 0); // 64x32
		settingAtlas.load();
		
		BitmapTextureAtlas moreAtlas = new BitmapTextureAtlas(context.getTextureManager(), 164, 61, TextureOptions.BILINEAR);
		this.mMoreRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(moreAtlas, context, "more.png", 0, 0); // 64x32
		moreAtlas.load();
	}

	public void attachToScene(Scene scene) {
		reset();
		scene.attachChild(this);
		scene.registerTouchArea(mSetting);
		scene.registerTouchArea(mStart);
		scene.registerTouchArea(mMore);
	}

	public void show(IEntityModifierListener listenner) {
		mStart.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, mStart
				.getX(), 0, EaseBounceOut.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 0, 1)));
		mSetting.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, mSetting
				.getX(), 0, EaseBounceOut.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 0, 1)));
		mMore.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				mMore.getX(), 0, listenner, EaseBounceOut.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 0,
				1)));
	}

	public void hide(IEntityModifierListener listenner) {
		mStart.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, mStart
				.getX(), -mStart.getWidth(), EaseBackIn.getInstance()),
				new AlphaModifier(Config.ANIMATE_DURATION, 1, 0)

		));
		mSetting.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, mSetting
				.getX(), getWidth(), listenner, EaseBackIn.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION,
				1, 0)));
		mMore.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				mMore.getX(), -mMore.getWidth(), EaseBackIn.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION,
				1, 0)

		));
	}

	public void setClickListenner(IClick mClickListenner) {
		mStart.setClickListenner(mClickListenner);
		mSetting.setClickListenner(mClickListenner);
	}

	public void detachFromScene(Scene scene) {
		scene.unregisterTouchArea(mSetting);
		scene.unregisterTouchArea(mStart);
		scene.unregisterTouchArea(mMore);
		scene.detachChild(this);
	}

	@Override
	public void resetMenu() {
		mStart.setX(-mStart.getWidth());
		mSetting.setX(getWidth());
		mMore.setX(-mMore.getWidth());
	}

	@Override
	public void lockUserAction(boolean enable) {
		mStart.setEnabled(enable);
		mSetting.setEnabled(enable);
		mMore.setEnabled(enable);
	}

}
