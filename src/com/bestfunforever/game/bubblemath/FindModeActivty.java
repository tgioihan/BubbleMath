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
import org.andengine.util.adt.pool.MultiPool;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackIn;
import org.andengine.util.modifier.ease.EaseBackOut;
import org.andengine.util.modifier.ease.EaseBounceIn;
import org.andengine.util.modifier.ease.EaseBounceOut;

import android.util.Log;

import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.game.bubblemath.Entity.BubbleObjectGennerator;
import com.bestfunforever.game.bubblemath.Entity.GameObjectGenerate;
import com.bestfunforever.game.bubblemath.Entity.Item;
import com.bestfunforever.game.bubblemath.Entity.SpriteWithValue;
import com.bestfunforever.game.bubblemath.Util.CountGame;
import com.bestfunforever.game.bubblemath.Util.FindModeGame;
import com.bestfunforever.game.bubblemath.Util.GameUtil;

public class FindModeActivty extends BubbleGameActivity {

	protected static final String tag = "FindModeActivty";
	private TiledTextureRegion greenBubbleRegion;
	private FindModeGame mGame;
	private ArrayList<Item> mAnswerSpriteList;
	private Random random = new Random(System.currentTimeMillis());
	private float centerY;
	private BubbleObjectGennerator mPool;

	@Override
	protected void onLoadResource() {
		BitmapTextureAtlas highscoreMenuAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (140),
				(int) (140), TextureOptions.BILINEAR);
		greenBubbleRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(highscoreMenuAtlas, this,
				"circle_green.png", 0, 0, 1, 1);
		highscoreMenuAtlas.load();
	}

	@Override
	public void onTickerTextComplete() {
		super.onTickerTextComplete();
		Log.d(tag,
				tag + " onTickerTextComplete " + tickTextManagable.getText()
						+ (!tickTextManagable.getText().equals("")));
		if (!endgame)
			if (!tickTextManagable.getText().equals(""))
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
			endgame = true;
			showEndGame();
		} else {
			animateGameObjectOut();
		}
	}

	private void animateGameObjectOut() {
		animateGraphicObjectOut();
	}

	@Override
	protected void initMathGraphicFrame() {
		mGame = new FindModeGame();
		Log.d(tag, tag + " initMathGraphicFrame ");
		initBubbleAnswerList();
		Log.d(tag, tag + " initMathGraphicFrame anser list");

		centerY = mAnswerSpriteList.get(0).getY() + (messageFrame.getY() - mAnswerSpriteList.get(0).getY()) / 2;
		createPool();
		Log.d(tag, tag + " initMathGraphicFrame pool then start");
		tickTextManagable.setText(stringManger.getStringFromKey(StringManger.RUN_MSG));
		tickTextManagable.setY(messageFrame.getHeight() / 2 - tickTextManagable.getHeight() / 2);

	}

	private void createPool() {
		mPool = new BubbleObjectGennerator(greenBubbleRegion, customFont, ratio, getVertexBufferObjectManager());
	}

	private float distanceGraphicObject = 0;
	private ArrayList<Integer> lefts = new ArrayList<Integer>();
	private ArrayList<Integer> rights = new ArrayList<Integer>();
	private ArrayList<Item> mGraphicObjects = new ArrayList<Item>();

	private void start() {
		lockUserAction(false);
		mGame.generate(Config.getMax(preferences));
		int[] listNumbers = mGame.getListNumbers();
		int resultPosition = mGame.getResultPosition();
		int totalItem = listNumbers.length;
		mGraphicObjects.clear();
		for (int i = 0; i < totalItem; i++) {
			Item value = mPool.obtainPoolItem();

			value.setScale(1);
			value.setX(0);
			value.setY(0);
			value.setId(listNumbers[i]);
			if (i == resultPosition) {
				value.setText("?");
			} else {
				value.setText(listNumbers[i]);
			}

			mGraphicObjects.add(value);
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
					mGraphicObjects.get(i).setX(-mGraphicObjects.get(i).getWidth());
					lefts.add(i);
				} else {
					mGraphicObjects.get(i).setX(CAMERA_WIDTH);
					rights.add(i);
				}
				Log.d(tag, tag + " row1 " + i + " " + mGraphicObjects.get(i).getWidth() + " width " + width1);
				width1 += mGraphicObjects.get(i).getWidth() + distanceGraphicObject * ratio;

			}

			float disX1 = CAMERA_WIDTH / 2 - width1 / 2;
			Log.d(tag, tag + " widht1 " + width1 + " disX " + disX1);
			for (int i = 0; i < row1; i++) {
				destinys[i] += disX1;
			}

			for (int i = 0; i < row2; i++) {
				destinys[i + row1] = width2;
				mGraphicObjects.get(i + row1).setY(
						mGraphicObjects.get(0).getY() + mGraphicObjects.get(0).getHeight() + distanceGraphicObject
								* ratio);
				if (i < row1 / 2) {
					mGraphicObjects.get(i + row1).setX(-mGraphicObjects.get(i).getWidth());
					lefts.add(i + row1);
				} else {
					mGraphicObjects.get(i + row1).setX(CAMERA_WIDTH);
					rights.add(i + row1);
				}
				Log.d(tag, tag + " row2 " + (i + row1) + " " + mGraphicObjects.get(i + row1).getWidth() + " width "
						+ width1);
				width2 += mGraphicObjects.get(i + row1).getWidth() + distanceGraphicObject * ratio;
			}

			float disX2 = CAMERA_WIDTH / 2 - width2 / 2;
			Log.d(tag, tag + " widht2 " + width2 + " disX " + disX2);
			for (int i = 0; i < row2; i++) {
				destinys[i + row1] += disX2;
			}

			float height = mGraphicObjects.get(totalItem - 1).getY() + mGraphicObjects.get(totalItem - 1).getHeight();

			float disY = centerY - height / 2;
			for (int i = 0; i < row1; i++) {
				mGraphicObjects.get(i).setY(mGraphicObjects.get(i).getY() + disY);
				mGraphicObjects.get(i).registerEntityModifier(
						new MoveXModifier(Config.ANIMATE_DURATION, mGraphicObjects.get(i).getX(), destinys[i],
								EaseBounceOut.getInstance()));
				Log.d(tag, tag + " start animation " + i + " destinys[i] " + destinys[i]);
				scene.attachChild(mGraphicObjects.get(i));
			}
			for (int i = 0; i < row2; i++) {
				mGraphicObjects.get(i + row1).setY(mGraphicObjects.get(i + row1).getY() + disY);
				if (i == row2 - 1) {
					mGraphicObjects.get(i + row1).registerEntityModifier(
							new MoveXModifier(Config.ANIMATE_DURATION, mGraphicObjects.get(i + row1).getX(), destinys[i
									+ row1], new IEntityModifierListener() {

								@Override
								public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

								}

								@Override
								public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
									animateAnswerObjectIn();
								}
							}, EaseBackOut.getInstance()));
				} else {
					mGraphicObjects.get(i + row1).registerEntityModifier(
							new MoveXModifier(Config.ANIMATE_DURATION, mGraphicObjects.get(i + row1).getX(), destinys[i
									+ row1], EaseBackOut.getInstance()));
				}
				Log.d(tag, tag + " start animation " + (i + row1) + " destinys[i] " + destinys[(i + row1)]);
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
					mGraphicObjects.get(i).setX(-mGraphicObjects.get(i).getWidth());
					lefts.add(i);
				} else {
					mGraphicObjects.get(i).setX(CAMERA_WIDTH);
					rights.add(i);
				}
				width1 += mGraphicObjects.get(i).getWidth() + distanceGraphicObject * ratio;
			}
			float disX1 = CAMERA_WIDTH / 2 - width1 / 2;
			for (int i = 0; i < totalItem; i++) {
				destinys[i] += disX1;
			}

			float height = mGraphicObjects.get(totalItem - 1).getY() + mGraphicObjects.get(totalItem - 1).getHeight();

			float disY = centerY - height / 2;

			for (int i = 0; i < totalItem; i++) {
				mGraphicObjects.get(i).setY(mGraphicObjects.get(i).getY() + disY);
				if (i == totalItem - 1) {
					mGraphicObjects.get(i).registerEntityModifier(
							new MoveXModifier(Config.ANIMATE_DURATION, mGraphicObjects.get(i).getX(), destinys[i],
									new IEntityModifierListener() {

										@Override
										public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

										}

										@Override
										public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
											animateAnswerObjectIn();

										}
									}, EaseBackOut.getInstance()));
				} else {
					mGraphicObjects.get(i).registerEntityModifier(
							new MoveXModifier(Config.ANIMATE_DURATION, mGraphicObjects.get(i).getX(), destinys[i],
									EaseBackOut.getInstance()));
				}
				Log.d(tag, tag + " start animation " + (i) + " destinys[i] " + destinys[(i)]);
				scene.attachChild(mGraphicObjects.get(i));
			}

		}

	}

	private void animateGraphicObjectOut() {
		int count = 0;
		boolean leftListenner = lefts.size() > 0;
		for (Integer i : lefts) {
			if (count == lefts.size() - 1) {
				mGraphicObjects.get(i).registerEntityModifier(
						new MoveXModifier(Config.ANIMATE_DURATION, mGraphicObjects.get(i).getX(), -mGraphicObjects.get(
								i).getWidth(), new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

							}

							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
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
						}, EaseBackIn.getInstance()));
			} else {
				mGraphicObjects.get(i).registerEntityModifier(
						new MoveXModifier(Config.ANIMATE_DURATION, mGraphicObjects.get(i).getX(), -mGraphicObjects.get(
								i).getWidth(), EaseBackIn.getInstance()));
			}
			count++;

		}
		count = 0;
		for (Integer i : rights) {
			if (count == rights.size() - 1 && !leftListenner) {
				mGraphicObjects.get(i).registerEntityModifier(
						new MoveXModifier(Config.ANIMATE_DURATION, mGraphicObjects.get(i).getX(), CAMERA_WIDTH,
								new IEntityModifierListener() {

									@Override
									public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

									}

									@Override
									public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
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
								}, EaseBackIn.getInstance()));
			} else {
				mGraphicObjects.get(i).registerEntityModifier(
						new MoveXModifier(Config.ANIMATE_DURATION, mGraphicObjects.get(i).getX(), CAMERA_WIDTH,
								EaseBackIn.getInstance()));
			}
			count++;
		}
	}

	private void removeGameGraphicObject() {
		for (Item item : mGraphicObjects) {
			scene.detachChild(item);
			mPool.recyclePoolItem(item);
			;
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
				Item item = new Item(ratio, customFont, greenBubbleRegion, getVertexBufferObjectManager());
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
						playCLick();
						clearStateAnswerList();
						lockUserAction(false);
						final Item item = (Item) view;
						item.onSelectedState();
						int value = (Integer) item.getValue();
						if (value == mGame.getResult()) {
							Log.d(tag, tag + " right value " + value);
							mGame.incressRightAnswer();
							animateRightAnswer();
						} else {
							lockUserAction(true);
							item.onNormalState();
						}

					}
				});
				scene.registerTouchArea(item);
				scene.attachChild(item);
			}
		}

	}

	protected void animateRightAnswer() {
		lockUserAction(false);
		int resultPosition = mGame.getResultPosition();
		Item item = mGraphicObjects.get(resultPosition);
		item.registerEntityModifier(new ScaleModifier(Config.ANIMATE_DURATION, 1, 0, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				((Item) pItem).setText(mGame.getResult());
				Log.d("", "animateRightAnswer " + mGame.getResult() + " pos " + mGame.getResultPosition());
				pItem.registerEntityModifier(new ScaleModifier(Config.ANIMATE_DURATION, 0, 1,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

							}

							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
								mSeekBar.setPercent(mGame.getProcessPercent(), true);
							}
						}, EaseBounceOut.getInstance()));
			}
		}, EaseBounceOut.getInstance()));
	}

	private void animateAnswerObjectOut() {
		for (int i = 0; i < 4; i++) {
			Item item = mAnswerSpriteList.get(i);
			item.registerEntityModifier(new ScaleModifier(Config.ANIMATE_DURATION, 1, 0, EaseBounceIn.getInstance()));
		}
	}

	private void animateAnswerObjectIn() {
		int rsPOs = random.nextInt(4);
		int[] rs = GameUtil.gennerateRandomArrayExcept(random, 4, mGame.getResult(), rsPOs, Config.getMax(preferences));
		for (int i = 0; i < 4; i++) {
			Item item = mAnswerSpriteList.get(i);
			item.setScale(0);
			item.setText(rs[i]);
			if (i < 3) {
				item.registerEntityModifier(new ScaleModifier(Config.ANIMATE_DURATION, 0, 1, EaseBounceOut
						.getInstance()));
			} else {
				item.registerEntityModifier(new ScaleModifier(Config.ANIMATE_DURATION, 0, 1,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

							}

							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
								lockUserAction(true);
							}
						}, EaseBounceOut.getInstance()));
			}
		}
	}

	public void clearStateAnswerList() {
		if (mAnswerSpriteList != null) {
			for (Item item : mAnswerSpriteList) {
				item.onNormalState();
			}
		}
	}

	@Override
	protected void lockUserAction(boolean enable) {
		if (mAnswerSpriteList != null) {
			for (Item item : mAnswerSpriteList) {
				item.setEnable(enable);
			}
		}
	}

	@Override
	protected void onCloseEndGame() {
		for (Item item : mAnswerSpriteList) {
			item.setEnable(true);
			item.setVisible(true);
		}
		for (Item item : mGraphicObjects) {
			item.setEnable(true);
			item.setVisible(true);
		}
		mGame.reset();
		mSeekBar.setPercent(mGame.getProcessPercent(), true);
		if (!endgame) {
			start();
		}
	}

	@Override
	protected void onEndGame() {
		for (Item item : mAnswerSpriteList) {
			item.setEnable(false);
			item.setVisible(false);
		}
		runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				for (Item item : mGraphicObjects) {
					scene.detachChild(item);
				}
				mGraphicObjects.clear();
			}
		});
	}

}
