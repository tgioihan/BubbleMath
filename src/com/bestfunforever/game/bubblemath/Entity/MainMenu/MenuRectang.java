package com.bestfunforever.game.bubblemath.Entity.MainMenu;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
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

public class MenuRectang extends ClipingRectangle implements IMenuRectangle {
	private TextExtension mStart;
	private TextExtension mSetting;

	public MenuRectang(float pX, float pY, float pWidth, float pHeight, float ratio, Font font,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		setColor(Color.TRANSPARENT);

		mStart = new TextExtension(0, 0, font, "Start", 10, new TextOptions(AutoWrap.WORDS, pWidth,
				HorizontalAlign.CENTER), pVertexBufferObjectManager);
		mSetting = new TextExtension(0, mStart.getHeight() + 40 * ratio, font, "Setting", 10, new TextOptions(
				AutoWrap.WORDS, pWidth, HorizontalAlign.CENTER), pVertexBufferObjectManager);
		float height = mSetting.getY() + mSetting.getHeight();
		float disY = pHeight / 2 - height / 2;
		mStart.setY(mStart.getY() + disY);
		mSetting.setY(mSetting.getY() + disY);
		mStart.setX(-mStart.getWidth());
		mSetting.setX(pWidth);
		mStart.setId(Config.MENU__START);
		mSetting.setId(Config.MENU__SETTings);
		attachChild(mStart);
		attachChild(mSetting);
	}

	public void attachToScene(Scene scene) {
		reset();
		scene.attachChild(this);
		scene.registerTouchArea(mSetting);
		scene.registerTouchArea(mStart);
	}

	public void show() {
		mStart.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, mStart
				.getX(), 0, EaseBounceOut.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 0, 1)));
		mSetting.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, mSetting
				.getX(), 0, EaseBounceOut.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 0, 1)));
	}

	public void hide(IEntityModifierListener listenner) {
		mStart.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, mStart
				.getX(), -mStart.getWidth(), EaseBackIn.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 1, 0)

		));
		mSetting.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,mSetting.getX(),
				getWidth(), listenner, EaseBackIn.getInstance()), new AlphaModifier(Config.ANIMATE_DURATION, 1, 0)));
	}

	public void setClickListenner(IClick mClickListenner) {
		mStart.setClickListenner(mClickListenner);
		mSetting.setClickListenner(mClickListenner);
	}

	public void detachFromScene(Scene scene) {
		scene.unregisterTouchArea(mSetting);
		scene.unregisterTouchArea(mStart);
		scene.detachChild(this);
	}

	@Override
	public void resetMenu() {
		mStart.setX(-mStart.getWidth());
		mSetting.setX(getWidth());
	}

}
