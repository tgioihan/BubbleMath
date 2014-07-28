package com.bestfunforever.game.bubblemath.Entity.MainMenu;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseBackIn;
import org.andengine.util.modifier.ease.EaseBounceOut;

import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.andengine.uikit.entity.Sprite.ClipingRectangle;
import com.bestfunforever.andengine.uikit.entity.text.TextExtension;
import com.bestfunforever.game.bubblemath.Config;

public class MenuModeGameRectang extends ClipingRectangle implements IMenuRectangle {

	private TextExtension mFruitMode;
	private TextExtension mFindMode;
	private TextExtension mRunMode;
	private float margin = 10;

	public MenuModeGameRectang(float pX, float pY, float pWidth, float pHeight, float ratio, Font font,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		setColor(Color.TRANSPARENT);

		mFruitMode = new TextExtension(0, 0, font, "Fuit Mode", 10, new TextOptions(AutoWrap.LETTERS, pWidth,
				HorizontalAlign.CENTER), pVertexBufferObjectManager);
		mFindMode = new TextExtension(0, mFruitMode.getY()+mFruitMode.getHeight() + margin * ratio, font, "1..3", 10, new TextOptions(
				AutoWrap.LETTERS, pWidth, HorizontalAlign.CENTER), pVertexBufferObjectManager);
		mRunMode = new TextExtension(0,mFindMode.getY()+ mFindMode.getHeight() + margin * ratio, font, "Run Mode", 10, new TextOptions(
				AutoWrap.LETTERS, pWidth, HorizontalAlign.CENTER), pVertexBufferObjectManager);

//		float height = mRunMode.getY() + mRunMode.getHeight();
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
	}

	public void show() {
		mFruitMode.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				mFruitMode.getX(), 0, EaseBounceOut.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 0, 1)));
		mFindMode.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				mFindMode.getX(), 0, EaseBounceOut.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 0, 1)));
		mRunMode.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, mRunMode
				.getX(), 0, EaseBounceOut.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 0, 1)));
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
	}

	public void setClickListenner(IClick mClickListenner) {
		mFruitMode.setClickListenner(mClickListenner);
		mFindMode.setClickListenner(mClickListenner);
		mRunMode.setClickListenner(mClickListenner);
	}

	public void detachFromScene(Scene scene) {
		scene.unregisterTouchArea(mFruitMode);
		scene.unregisterTouchArea(mFindMode);
		scene.unregisterTouchArea(mRunMode);
		scene.detachChild(this);
	}

	@Override
	public void resetMenu() {
		mFruitMode.setX(-mFruitMode.getWidth());
		mFindMode.setX(getWidth());
		mRunMode.setX(-mRunMode.getWidth());
	}

}
