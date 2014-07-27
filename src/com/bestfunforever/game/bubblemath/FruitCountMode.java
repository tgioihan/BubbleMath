package com.bestfunforever.game.bubblemath;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.adt.pool.MultiPool;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBounceIn;
import org.andengine.util.modifier.ease.EaseBounceOut;

import android.util.Log;

import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.andengine.uikit.entity.TickerTextExtension.TickerTextOptions;
import com.bestfunforever.andengine.uikit.entity.TickerTextManagable;
import com.bestfunforever.andengine.uikit.util.Util;
import com.bestfunforever.game.bubblemath.Entity.GameObjectGenerate;
import com.bestfunforever.game.bubblemath.Entity.GameUtil;
import com.bestfunforever.game.bubblemath.Entity.Item;
import com.bestfunforever.game.bubblemath.Entity.SpriteWithValue;

public class FruitCountMode extends BubbleGameActivity {
	private static final String tag = "FruitCountMode";
	private TiledTextureRegion greenBubbleRegion;
	private float animDuration = 2f;
	private CountGame mGame;
	private ArrayList<Item> mAnswerSpriteList;
	private MultiPool<SpriteWithValue> mPool;
	private ArrayList<SpriteWithValue> mGraphicObjects = new ArrayList<SpriteWithValue>();
	Random random = new Random(System.currentTimeMillis());
	private float centerY;
	private TickerTextManagable tickTextManagable;
	private Sprite messageFrame;

	@Override
	public void onTickerTextComplete() {
		Log.d(tag, tag + " onTickerTextComplete " + tickTextManagable.getText()
				+ (!tickTextManagable.getText().equals("")));
		if (!tickTextManagable.getText().equals(""))
			animateAnswerObjectIn();
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
			animateGameObjectOut();
		}
	}

	private void animateGameObjectOut() {
		animateAnswerObjectOut();
		animateGraphicObjectOut();
	}

	@Override
	protected void initMathGraphicFrame() {
		mGame = new CountGame();
		Log.d(tag, tag + " initMathGraphicFrame ");
		initBubbleAnswerList();
		Log.d(tag, tag + " initMathGraphicFrame anser list");
		messageFrame = new Sprite(child.getX() + child.getWidth() + 30 * ratio,
				mMenu.getMenuPositionY() - messageRegion.getHeight() * ratio,
				messageRegion.getWidth() * ratio, messageRegion.getHeight()
						* ratio, messageRegion, getVertexBufferObjectManager());
		scene.attachChild(messageFrame);
		tickTextManagable = new TickerTextManagable(10 * ratio, 10 * ratio,
				mFont, "", 100, new TickerTextOptions(AutoWrap.WORDS,
						messageFrame.getWidth() - 2 * 10 * ratio,
						HorizontalAlign.CENTER, 15),
				getVertexBufferObjectManager());
		tickTextManagable.setTickerTextListenner(this);
		messageFrame.attachChild(tickTextManagable);

		Log.d(tag, tag + " initMathGraphicFrame ticker");

		centerY = mAnswerSpriteList.get(0).getY()
				+ (child.getY() - mAnswerSpriteList.get(0).getY()) / 2;
		createPool();
		Log.d(tag, tag + " initMathGraphicFrame pool then start");
		start();

	}

	private void createPool() {
		// TODO Auto-generated method stub
		mPool = new MultiPool<SpriteWithValue>();
		for (int i = 0; i < CountGame.fruitPng.length; i++) {
			mPool.registerPool(i,
					new GameObjectGenerate(fruitRegions.getTextureRegion(i),
							ratio, getVertexBufferObjectManager()));
		}
	}

	private float distanceGraphicObject = 0;
	private TiledTextureRegion fruitRegions;
	private ArrayList<Integer> lefts = new ArrayList<Integer>();
	private ArrayList<Integer> rights = new ArrayList<Integer>();

	private void start() {
		mGame.generate(10);
		int totalItem = mGame.getTotalItem();
		int itemType = mGame.getItemRsPos();
		int result = mGame.getResult();

		mGraphicObjects.clear();
		Log.d(tag, tag + " start result " + result + " totalItem " + totalItem);
		int[] rsPositions = GameUtil.gennerateRandomArray(random, result,
				totalItem);
		Log.d(tag, tag + " rsPositions " + rsPositions);
		for (int i = 0; i < totalItem; i++) {
			if (GameUtil.matchItemInArray(i, rsPositions)) {
				Log.d(tag, tag + " initial fruit rs type " + i);
				SpriteWithValue value = mPool.obtainPoolItem(itemType);
				value.setX(0);
				value.setY(0);
				mGraphicObjects.add(value);
			} else {
				Log.d(tag, tag + " initial fruit normal type " + i);
				SpriteWithValue value = mPool.obtainPoolItem(GameUtil
						.genneratePosionNotMatch(random, itemType,
								CountGame.fruitPng.length));
				value.setX(0);
				value.setY(0);
				mGraphicObjects.add(value);
			}
		}

		// layout item
		lefts.clear();
		rights.clear();
		int row = 0;
		if (totalItem / 2 > 1) {
			row = 2;
			int row1 = totalItem / 2;
			int row2 = totalItem - row1;
			if (row2 > row2) {
				int tmp = row1;
				row1 = row2;
				row2 = tmp;
			}

			float[] destinys = new float[totalItem];
			float width1 = 0;
			float width2 = 0;

			for (int i = 0; i < row1; i++) {
				destinys[i] = width1;
				mGraphicObjects.get(i).setY(0);
				if (i < row1 / 2) {
					mGraphicObjects.get(i).setX(
							-mGraphicObjects.get(i).getWidth());
					lefts.add(i);
				} else {
					mGraphicObjects.get(i).setX(CAMERA_WIDTH);
					rights.add(i);
				}
				Log.d(tag, tag + " row1 " + i + " "
						+ mGraphicObjects.get(i).getWidth() + " width "
						+ width1);
				width1 += mGraphicObjects.get(i).getWidth()
						+ distanceGraphicObject * ratio;

			}

			float disX1 = CAMERA_WIDTH / 2 - width1 / 2;
			Log.d(tag, tag + " widht1 " + width1 + " disX " + disX1);
			for (int i = 0; i < row1; i++) {
				destinys[i] += disX1;
			}

			for (int i = 0; i < row2; i++) {
				destinys[i + row1] = width2;
				mGraphicObjects.get(i + row1).setY(
						mGraphicObjects.get(0).getY()
								+ mGraphicObjects.get(0).getHeight()
								+ distanceGraphicObject * ratio);
				if (i < row1 / 2) {
					mGraphicObjects.get(i + row1).setX(
							-mGraphicObjects.get(i).getWidth());
					lefts.add(i + row1);
				} else {
					mGraphicObjects.get(i + row1).setX(CAMERA_WIDTH);
					rights.add(i + row1);
				}
				Log.d(tag, tag + " row2 " + (i + row1) + " "
						+ mGraphicObjects.get(i + row1).getWidth() + " width "
						+ width1);
				width2 += mGraphicObjects.get(i + row1).getWidth()
						+ distanceGraphicObject * ratio;
			}

			float disX2 = CAMERA_WIDTH / 2 - width2 / 2;
			Log.d(tag, tag + " widht2 " + width2 + " disX " + disX2);
			for (int i = 0; i < row2; i++) {
				destinys[i + row1] += disX2;
			}

			float height = mGraphicObjects.get(totalItem - 1).getY()
					+ mGraphicObjects.get(totalItem - 1).getHeight();

			float disY = centerY - height / 2;
			for (int i = 0; i < row1; i++) {
				mGraphicObjects.get(i).setY(
						mGraphicObjects.get(i).getY() + disY);
				mGraphicObjects.get(i).registerEntityModifier(
						new MoveXModifier(animDuration, mGraphicObjects.get(i)
								.getX(), destinys[i], EaseBounceOut
								.getInstance()));
				Log.d(tag, tag + " start animation " + i + " destinys[i] "
						+ destinys[i]);
				scene.attachChild(mGraphicObjects.get(i));
			}
			for (int i = 0; i < row2; i++) {
				mGraphicObjects.get(i + row1).setY(
						mGraphicObjects.get(i + row1).getY() + disY);
				if (i == row2 - 1) {
					mGraphicObjects.get(i + row1).registerEntityModifier(
							new MoveXModifier(animDuration, mGraphicObjects
									.get(i + row1).getX(), destinys[i + row1],
									new IEntityModifierListener() {

										@Override
										public void onModifierStarted(
												IModifier<IEntity> pModifier,
												IEntity pItem) {

										}

										@Override
										public void onModifierFinished(
												IModifier<IEntity> pModifier,
												IEntity pItem) {
											String mesg = "Co bao nhieu qua "
													+ CountGame.fruitName[mGame
															.getItemRsPos()]
													+ " trong hinh tren";
											tickTextManagable.setText(mesg);

										}
									}, EaseBounceOut.getInstance()));
				} else {
					mGraphicObjects.get(i + row1).registerEntityModifier(
							new MoveXModifier(animDuration, mGraphicObjects
									.get(i + row1).getX(), destinys[i + row1],
									EaseBounceOut.getInstance()));
				}
				Log.d(tag, tag + " start animation " + (i + row1)
						+ " destinys[i] " + destinys[(i + row1)]);
				scene.attachChild(mGraphicObjects.get(i + row1));
			}

		} else {
			row = 1;
			float[] destinys = new float[totalItem];
			float width1 = 0;
			for (int i = 0; i < totalItem; i++) {
				destinys[i] = width1;
				mGraphicObjects.get(i).setY(0);
				if (i < totalItem / 2) {
					mGraphicObjects.get(i).setX(
							-mGraphicObjects.get(i).getWidth());
					lefts.add(i);
				} else {
					mGraphicObjects.get(i).setX(CAMERA_WIDTH);
					rights.add(i);
				}
				width1 += mGraphicObjects.get(i).getWidth()
						+ distanceGraphicObject * ratio;
			}
			float disX1 = CAMERA_WIDTH / 2 - width1 / 2;
			for (int i = 0; i < totalItem; i++) {
				destinys[i] += disX1;
			}

			float height = mGraphicObjects.get(totalItem - 1).getY()
					+ mGraphicObjects.get(totalItem - 1).getHeight();

			float disY = centerY - height / 2;

			for (int i = 0; i < totalItem; i++) {
				mGraphicObjects.get(i).setY(
						mGraphicObjects.get(i).getY() + disY);
				if (i == totalItem - 1) {
					mGraphicObjects.get(i).registerEntityModifier(
							new MoveXModifier(animDuration, mGraphicObjects
									.get(i).getX(), destinys[i],
									new IEntityModifierListener() {

										@Override
										public void onModifierStarted(
												IModifier<IEntity> pModifier,
												IEntity pItem) {

										}

										@Override
										public void onModifierFinished(
												IModifier<IEntity> pModifier,
												IEntity pItem) {
											String mesg = "Co bao nhieu qua "
													+ CountGame.fruitName[mGame
															.getItemRsPos()]
													+ " trong hinh tren";
											tickTextManagable.setText(mesg);

										}
									}, EaseBounceOut.getInstance()));
				} else {
					mGraphicObjects.get(i).registerEntityModifier(
							new MoveXModifier(animDuration, mGraphicObjects
									.get(i).getX(), destinys[i], EaseBounceOut
									.getInstance()));
				}
				Log.d(tag, tag + " start animation " + (i) + " destinys[i] "
						+ destinys[(i)]);
				scene.attachChild(mGraphicObjects.get(i));
			}

		}

	}
	

	private void animateGraphicObjectOut() {
		for (Integer i : lefts) {
			mGraphicObjects.get(i).registerEntityModifier(
					new MoveXModifier(animDuration, mGraphicObjects
							.get(i).getX(), -mGraphicObjects.get(i).getWidth(),
							new IEntityModifierListener() {

								@Override
								public void onModifierStarted(
										IModifier<IEntity> pModifier,
										IEntity pItem) {

								}

								@Override
								public void onModifierFinished(
										IModifier<IEntity> pModifier,
										IEntity pItem) {
									runOnUpdateThread(new Runnable() {
										
										@Override
										public void run() {
											for (Item item : mAnswerSpriteList) {
												item.onNormalState();
											}
											removeGameGraphicObject();
										}
										
									});
								}
							}, EaseBounceIn.getInstance()));
		}
		
		for (Integer i : rights) {
			mGraphicObjects.get(i).registerEntityModifier(
					new MoveXModifier(animDuration, mGraphicObjects
							.get(i).getX(), CAMERA_WIDTH, EaseBounceIn
							.getInstance()));
		}
	}
	
	private void removeGameGraphicObject() {
		for (SpriteWithValue spriteWithValue : mGraphicObjects) {
			scene.detachChild(spriteWithValue);
		}
		mGraphicObjects.clear();
		start();
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
						if (value == mGame.getResult()) {
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

	private void animateAnswerObjectOut() {
		for (int i = 0; i < 4; i++) {
			Item item = mAnswerSpriteList.get(i);
			item.registerEntityModifier(new ScaleModifier(animDuration, 1, 0,
					EaseBounceIn.getInstance()));
		}
	}

	private void animateAnswerObjectIn() {
		int[] rs = new int[4];
		int rsPOs = random.nextInt(4);

		for (int i = 0; i < 4; i++) {
			if (i == rsPOs) {
				rs[rsPOs] = mGame.getResult();
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
				this.getTextureManager(), 118, (int) (118)
						* CountGame.fruitPng.length, TextureOptions.BILINEAR);
		fruitRegions = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(fruitAtlas, this, "fruit_art.png", 0, 0,
						1, CountGame.fruitPng.length);
		fruitAtlas.load();

	}

}
