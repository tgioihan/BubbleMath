package com.bestfunforever.game.bubblemath.Entity.MainMenu;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
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
import com.bestfunforever.andengine.uikit.entity.Sprite.BubbleSprite;
import com.bestfunforever.andengine.uikit.entity.Sprite.ClipingRectangle;
import com.bestfunforever.andengine.uikit.entity.Sprite.SpriteImplement;
import com.bestfunforever.andengine.uikit.entity.text.TextExtension;
import com.bestfunforever.game.bubblemath.Config;
import com.bestfunforever.game.bubblemath.StringManger;

public class MenuModeGameRectang extends ClipingRectangle implements IMenuRectangle {

	private TextExtension mFruitMode;
	private TextExtension mFindMode;
	private TextExtension mRunMode;
	private float margin = 0;
	private BubbleSprite bubbleSprite;

	public MenuModeGameRectang(SimpleBaseGameActivity context, StringManger stringManger, float pX, float pY,
			float pWidth, float pHeight, float ratio, Font font, TextureRegion mBackRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		setColor(Color.TRANSPARENT);
		mFruitMode = new TextExtension(0, 0, font, stringManger.getStringFromKey(StringManger.FRUIT), 10,
				new TextOptions(AutoWrap.WORDS, pWidth, HorizontalAlign.CENTER), pVertexBufferObjectManager);
		mFindMode = new TextExtension(0, mFruitMode.getY() + mFruitMode.getHeight() + margin * ratio, font,
				stringManger.getStringFromKey(StringManger.FINDMODE), 10, new TextOptions(AutoWrap.WORDS, pWidth,
						HorizontalAlign.CENTER), pVertexBufferObjectManager);
		mRunMode = new TextExtension(0, mFindMode.getY() + mFindMode.getHeight() + margin * ratio, font,
				stringManger.getStringFromKey(StringManger.RUN), 10, new TextOptions(AutoWrap.WORDS, pWidth,
						HorizontalAlign.CENTER), pVertexBufferObjectManager);

		// float height = mRunMode.getY() + mRunMode.getHeight();
		float disY = 0;
		mFruitMode.setY(mFruitMode.getY() + disY);
		mFindMode.setY(mFindMode.getY() + disY);
		mRunMode.setY(mRunMode.getY() + disY);
		mFruitMode.setX(-mFruitMode.getWidth());
		mFindMode.setX(pWidth);
		mRunMode.setX(-mRunMode.getWidth());
		mFruitMode.setId(Config.MENU__FRUITMODE);
		mFindMode.setId(Config.MENU__FINDMODE);
		mRunMode.setId(Config.MENU__RUNMODE);
		bubbleSprite = new BubbleSprite(pWidth, mRunMode.getY() + mRunMode.getHeight(), ratio, null, null, mBackRegion,
				getVertexBufferObjectManager());
		bubbleSprite.setId(Config.MENU__BACK);
		attachChild(bubbleSprite);
		attachChild(mFruitMode);
		attachChild(mFindMode);
		attachChild(mRunMode);
	}

	public void attachToScene(Scene scene) {
		resetMenu();
		scene.attachChild(this);
		scene.registerTouchArea(mFruitMode);
		scene.registerTouchArea(mFindMode);
		scene.registerTouchArea(mRunMode);
		scene.registerTouchArea(bubbleSprite);
	}

	public void show(IEntityModifierListener listenner) {
		mFruitMode.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				mFruitMode.getX(), 0, EaseBounceOut.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 0, 1)));
		mFindMode.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				mFindMode.getX(), 0, EaseBounceOut.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 0, 1)));
		mRunMode.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, mRunMode
				.getX(), 0, listenner, EaseBounceOut.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 0, 1)));
		bubbleSprite.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				bubbleSprite.getX(), getWidth() / 2 - bubbleSprite.getWidth() / 2, EaseBounceOut.getInstance())));
	}

	public void hide(IEntityModifierListener listenner) {
		mFruitMode.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				-mFruitMode.getX(), -mFruitMode.getWidth(), EaseBackIn.getInstance()), new AlphaModifier(
				Config.ANIMATE_DURATION, 1, 0)));
		mFindMode.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				mFindMode.getX(), getWidth(), listenner, EaseBackIn.getInstance()), new AlphaModifier(
				Config.ANIMATE_DURATION, 1, 0)));
		mRunMode.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, -mRunMode
				.getX(), -mRunMode.getWidth(), EaseBackIn.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 1,
				0)));
		bubbleSprite.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				bubbleSprite.getX(), getWidth(), listenner, EaseBackIn.getInstance())));
	}

	public void setClickListenner(IClick mClickListenner) {
		mFruitMode.setClickListenner(mClickListenner);
		mFindMode.setClickListenner(mClickListenner);
		mRunMode.setClickListenner(mClickListenner);
		bubbleSprite.setClickListenner(mClickListenner);
	}

	public void detachFromScene(Scene scene) {
		scene.unregisterTouchArea(mFruitMode);
		scene.unregisterTouchArea(mFindMode);
		scene.unregisterTouchArea(mRunMode);
		scene.unregisterTouchArea(bubbleSprite);
		scene.detachChild(this);
	}

	@Override
	public void resetMenu() {
		mFruitMode.setX(-mFruitMode.getWidth());
		mFindMode.setX(getWidth());
		mRunMode.setX(-mRunMode.getWidth());
		bubbleSprite.setX(getWidth());
	}

	@Override
	public void lockUserAction(boolean enable) {
		mFruitMode.setEnabled(enable);
		mFindMode.setEnabled(enable);
		mRunMode.setEnabled(enable);
		bubbleSprite.setEnabled(enable);
	}

}
