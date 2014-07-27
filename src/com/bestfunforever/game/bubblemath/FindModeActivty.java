package com.bestfunforever.game.bubblemath;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackIn;
import org.andengine.util.modifier.ease.EaseBackOut;
import org.andengine.util.modifier.ease.EaseBounceIn;
import org.andengine.util.modifier.ease.EaseBounceOut;

import android.util.Log;

import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.andengine.uikit.util.Util;
import com.bestfunforever.game.bubblemath.Entity.Item;

public class FindModeActivty extends BubbleGameActivity {

	protected static final String tag = "FindModeActivty";
	private TiledTextureRegion greenBubbleRegion;
	private FindModeGame mGame;
	private ArrayList<Item> mAnswerSpriteList;
	private Item num1;
	private Item num2;
	private Item num3;
	private float num1Destiny;
	private float num2Destiny;
	private float num3Destiny;
	private float animDuration = 1;
	private Random random = new Random(System.currentTimeMillis());

	@Override
	protected void onLoadResource() {
		BitmapTextureAtlas highscoreMenuAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), (int) (140), (int) (140),
				TextureOptions.BILINEAR);
		greenBubbleRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(highscoreMenuAtlas, this,
						"circle_green.png", 0, 0, 1, 1);
		highscoreMenuAtlas.load();
	}

	@Override
	public void onTickerTextComplete() {
		start();
	}

	@Override
	public void onStartSeeking(float percent, boolean userTouch) {

	}

	@Override
	public void onSeeking(float percent, boolean userTouch) {

	}

	@Override
	public void onFinishSeek(float percent, boolean userTouch) {
		if (mGame.isComplete()) {
			Log.d("", "game finish ");
		} else {
			moveOutMathFuntion();
		}
	}

	@Override
	protected void initMathGraphicFrame() {
		mGame = new FindModeGame();
		initBubbleAnswerList();
		initMathObjects();
		tickTextManagable.setText("Tim so con thieu trong day so tren");
	}

	private void initMathObjects() {
		final float centerY = mAnswerSpriteList.get(0).getY()
				+ (messageFrame.getY() - mAnswerSpriteList.get(0).getY()) / 2;
		num1 = new Item(ratio, customFont, greenBubbleRegion,
				getVertexBufferObjectManager());
		num2 = new Item(ratio, customFont, greenBubbleRegion,
				getVertexBufferObjectManager());
		num3 = new Item(ratio, customFont, greenBubbleRegion,
				getVertexBufferObjectManager());
		num1.setX(-num1.getWidth());
		num2.setX(-num2.getWidth());
		num3.setX(CAMERA_WIDTH);

		num1.setY(centerY - num1.getHeight() / 2);
		num2.setY(centerY - num2.getHeight() / 2);
		num3.setY(centerY - num3.getHeight() / 2);

		scene.attachChild(num1);
		scene.attachChild(num2);
		scene.attachChild(num3);

		// calculate animation destiny
		float totalWidth = 0;
		totalWidth += num1.getWidth() + marginTextMath;
		totalWidth += num2.getWidth() + marginTextMath;
		totalWidth += num3.getWidth() + marginTextMath;
		float distance = CAMERA_WIDTH / 2 - totalWidth / 2;
		num1Destiny = distance;
		num2Destiny = num1Destiny + num1.getWidth() + marginTextMath;
		num3Destiny = num2Destiny + num2.getWidth() + marginTextMath;
	}

	private void moveInMathFuntion() {
		num1.registerEntityModifier(new MoveXModifier(animDuration,
				num1.getX(), num1Destiny, EaseBounceOut.getInstance()));
		num2.registerEntityModifier(new MoveXModifier(animDuration,
				num2.getX(), num2Destiny, EaseBounceOut.getInstance()));
		num3.registerEntityModifier(new MoveXModifier(animDuration,
				num3.getX(), num3Destiny, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						for (IAreaShape item : mAnswerSpriteList) {
							((Item) item).onNormalState();
						}
						showAnswerList();
					}
				}, EaseBounceOut.getInstance()));
	}

	private void moveOutMathFuntion() {
		num1.registerEntityModifier(new MoveXModifier(animDuration,
				num1.getX(), -num1.getWidth(), EaseBounceIn.getInstance()));
		num2.registerEntityModifier(new MoveXModifier(animDuration,
				num2.getX(), CAMERA_WIDTH, EaseBounceIn.getInstance()));
		num3.registerEntityModifier(new MoveXModifier(animDuration,
				num3.getX(), CAMERA_WIDTH, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						for (IAreaShape item : mAnswerSpriteList) {
							((Item) item).onNormalState();
						}
						start();
					}
				}, EaseBounceIn.getInstance()));
	}

	private void start() {
		mGame.generate(10);
		num1.setText(mGame.getOperator1());
		num2.setText("?");
		num3.setText(mGame.getOperator3());
		moveInMathFuntion();
	}

	private void showAnswerList() {
		int[] rs = new int[4];
		int rsPOs = 2;

		for (int i = 0; i < 4; i++) {
			if (i == rsPOs) {
				rs[rsPOs] = mGame.getOperator2();
			} else {
				rs[i] = Util.randInt(1, 10);
			}
		}
		for (int i = 0; i < 4; i++) {
			Item item = mAnswerSpriteList.get(i);
			item.setScale(0);
			item.setText(rs[i]);
			if (i < 3) {
				item.registerEntityModifier(new ScaleModifier(animDuration, 0,
						1, EaseBounceOut.getInstance()));
			} else {
				item.registerEntityModifier(new ScaleModifier(animDuration, 0,
						1, new IEntityModifierListener() {

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

	private void initBubbleAnswerList() {
		if (mAnswerSpriteList == null) {
			mAnswerSpriteList = new ArrayList<Item>(4);

			float y = mSeekBar.getY() + mSeekBar.getHeight() + 30 * ratio;

			int width = 0;
			for (int i = 0; i < 4; i++) {
				Item item = new Item(ratio, customFont, greenBubbleRegion,
						getVertexBufferObjectManager());
				item.setX(width);
				item.setY(y);
				if (i < 3)
					width += item.getWidth() + 20 * ratio;
				else
					width += item.getWidth();
				mAnswerSpriteList.add(item);
			}
			float tmp = CAMERA_WIDTH / 2 - width / 2;
			for (int i = 0; i < 4; i++) {
				Item item = mAnswerSpriteList.get(i);
				item.setX(item.getX() + tmp);
				item.setScale(0);
				item.setClickListenner(new IClick() {

					@Override
					public void onCLick(IAreaShape view) {
						final Item item = (Item) view;
						int value = (Integer) item.getValue();
						if (value == mGame.getOperator2()) {
							Log.d(tag, tag + " right value " + value);
							mGame.incressRightAnswer();
							mSeekBar.setPercent(mGame.getProcessPercent(), true);
						}
					}
				});
				scene.registerTouchArea(item);
				scene.attachChild(item);
			}
		}

	}

}
