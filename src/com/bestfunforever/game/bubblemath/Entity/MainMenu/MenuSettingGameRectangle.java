package com.bestfunforever.game.bubblemath.Entity.MainMenu;

import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseBackIn;
import org.andengine.util.modifier.ease.EaseBounceOut;

import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.andengine.uikit.entity.Sprite.BubbleSprite;
import com.bestfunforever.andengine.uikit.entity.Sprite.CheckBox;
import com.bestfunforever.andengine.uikit.entity.Sprite.ClipingRectangle;
import com.bestfunforever.game.bubblemath.Config;
import com.bestfunforever.game.bubblemath.StringManger;

public class MenuSettingGameRectangle extends ClipingRectangle implements IMenuRectangle {

	private float margin = 10;
	private CheckBox soundCheckBox;
	private Rectangle soundRectangle;
	private CheckBox musicCheckBox;
	private Rectangle musicRectangle;
	private Text maxNum;
	private Rectangle numRectangle;
	private BubbleSprite bubbleSprite;

	public MenuSettingGameRectangle(StringManger stringManger, float pX, float pY, float pWidth, float pHeight,
			float ratio, Font font, TiledTextureRegion soundRegion, TiledTextureRegion musicRegion,
			TextureRegion mBackRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		setColor(Color.TRANSPARENT);

		Text soundLabel = new Text(0, 0, font, stringManger.getStringFromKey(StringManger.SOUND),
				pVertexBufferObjectManager);
		soundCheckBox = new CheckBox(pWidth - soundRegion.getWidth() * ratio, 0, ratio, false, soundRegion,
				pVertexBufferObjectManager);
		float height1 = Math.max(soundLabel.getHeight(), soundCheckBox.getHeight());
		soundRectangle = new Rectangle(0, 0, pWidth, height1, pVertexBufferObjectManager);
		soundCheckBox.setY(height1 / 2 - soundCheckBox.getHeight() / 2);
		soundLabel.setY(height1 / 2 - soundLabel.getHeight() / 2);
		soundRectangle.setX(-soundRectangle.getWidth());
		soundRectangle.setColor(Color.TRANSPARENT);
		soundRectangle.attachChild(soundLabel);
		soundRectangle.attachChild(soundCheckBox);

		Text musicLabel = new Text(0, 0, font, stringManger.getStringFromKey(StringManger.MUSIC),
				pVertexBufferObjectManager);
		musicCheckBox = new CheckBox(pWidth - musicRegion.getWidth() * ratio, 0, ratio, false, musicRegion,
				pVertexBufferObjectManager);
		float height2 = Math.max(musicLabel.getHeight(), musicCheckBox.getHeight());
		musicRectangle = new Rectangle(0, soundRectangle.getY() + soundRectangle.getHeight(), pWidth, height2,
				pVertexBufferObjectManager);
		musicCheckBox.setY(height2 / 2 - musicCheckBox.getHeight() / 2);
		musicLabel.setY(height1 / 2 - musicLabel.getHeight() / 2);
		musicRectangle.setX(pWidth);
		musicRectangle.setColor(Color.TRANSPARENT);
		musicRectangle.attachChild(musicLabel);
		musicRectangle.attachChild(musicCheckBox);

		Text languageLabel = new Text(0, 0, font, stringManger.getStringFromKey(StringManger.LANGUAGE),
				pVertexBufferObjectManager);
		maxNum = new Text(0, 0, font, "6", 2, pVertexBufferObjectManager);
		maxNum.setX(pWidth - maxNum.getWidth());
		numRectangle = new Rectangle(0, musicRectangle.getY() + musicRectangle.getHeight(), pWidth, height2,
				pVertexBufferObjectManager);
		numRectangle.setX(-numRectangle.getWidth());
		numRectangle.setColor(Color.TRANSPARENT);
		numRectangle.attachChild(languageLabel);
		numRectangle.attachChild(maxNum);

		bubbleSprite = new BubbleSprite(pWidth, numRectangle.getY() + numRectangle.getHeight(), ratio, null, null,
				mBackRegion, getVertexBufferObjectManager());
		bubbleSprite.setId(Config.MENU__BACK);
		attachChild(bubbleSprite);
		attachChild(soundRectangle);
		attachChild(musicRectangle);
		attachChild(numRectangle);
	}

	public void attachToScene(Scene scene) {
		resetMenu();
		scene.attachChild(this);
		scene.registerTouchArea(soundCheckBox);
		scene.registerTouchArea(musicCheckBox);
		scene.registerTouchArea(bubbleSprite);
	}

	public void show(IEntityModifierListener listenner) {
		soundRectangle.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				soundRectangle.getX(), 0, EaseBounceOut.getInstance())));
		musicRectangle.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				musicRectangle.getX(), 0, EaseBounceOut.getInstance())));
		numRectangle.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				numRectangle.getX(), 0, listenner, EaseBounceOut.getInstance())));
		bubbleSprite.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				bubbleSprite.getX(), getWidth() / 2 - bubbleSprite.getWidth() / 2, EaseBounceOut.getInstance())));
	}

	public void hide(IEntityModifierListener listenner) {
		soundRectangle.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				-soundRectangle.getX(), -soundRectangle.getWidth(), EaseBackIn.getInstance())));
		musicRectangle.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				musicRectangle.getX(), getWidth(), listenner, EaseBackIn.getInstance())));
		numRectangle.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				numRectangle.getX(), -numRectangle.getWidth(), EaseBackIn.getInstance())));
		bubbleSprite.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				bubbleSprite.getX(), getWidth(), listenner, EaseBackIn.getInstance())));
	}

	public void setClickListenner(IClick mClickListenner) {
		bubbleSprite.setClickListenner(mClickListenner);
		soundCheckBox.setClickListenner(mClickListenner);
		musicCheckBox.setClickListenner(mClickListenner);
	}

	public void detachFromScene(Scene scene) {
		scene.unregisterTouchArea(soundCheckBox);
		scene.unregisterTouchArea(musicCheckBox);
		scene.unregisterTouchArea(bubbleSprite);
		scene.detachChild(this);
	}

	@Override
	public void resetMenu() {
		soundRectangle.setX(-soundRectangle.getWidth());
		musicRectangle.setX(getWidth());
		numRectangle.setX(-numRectangle.getWidth());
		bubbleSprite.setX(getWidth());
	}

	@Override
	public void lockUserAction(boolean enable) {
		soundCheckBox.setEnabled(enable);
		musicCheckBox.setEnabled(enable);
		bubbleSprite.setEnabled(enable);
	}

}
