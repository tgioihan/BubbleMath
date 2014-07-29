package com.bestfunforever.game.bubblemath;

import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBounceIn;
import org.andengine.util.modifier.ease.EaseBounceOut;

import android.util.Log;
import android.widget.Toast;

import com.bestfunforever.andengine.uikit.entity.Sprite.BaseSprite.State;
import com.bestfunforever.andengine.uikit.listview.OnItemClickListenner;
import com.bestfunforever.andengine.uikit.menu.BaseMenu.IOnMenuItemClickListener;
import com.bestfunforever.andengine.uikit.menu.IMenuItem;
import com.bestfunforever.game.bubblemath.Entity.Item;
import com.bestfunforever.game.bubblemath.Entity.SpriteWithValue;
import com.bestfunforever.game.bubblemath.Util.RunModeGame;

public class RunModeActivity extends BubbleGameActivity implements IOnMenuItemClickListener {

	private TiledTextureRegion greenBubbleRegion;
	private AutoScrollHorizontalList mListView;
	private Text operator1;
	private Text operator2;
	private Text operator3;
	private Text operand1;
	private Text operand2;
	private MathAdapter adapter;
	private RunModeGame mGame;
	private Random random = new Random(System.currentTimeMillis());

	private Color[] colors = new Color[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN };

	@Override
	public void onTickerTextComplete() {
		super.onTickerTextComplete();
		if(!endgame)
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
			moveOutMathFuntion();
		}
	}

	@Override
	protected void initMathGraphicFrame() {
		mGame = new RunModeGame();
		mListView = new AutoScrollHorizontalList(this, 10 * ratio, mSeekBar.getY() + mSeekBar.getHeight() + 30 * ratio,
				CAMERA_WIDTH - 20 * ratio, greenBubbleRegion.getHeight() * ratio, getVertexBufferObjectManager());
		adapter = new MathAdapter(greenBubbleRegion, Config.getMax(preferences), ratio, customFont,
				getVertexBufferObjectManager());
		scene.attachChild(mListView);
		mListView.setAdapter(adapter);
		mListView.setSelection(Integer.MAX_VALUE / 2);
		mListView.setScrollVelocity(-3f * ratio);
		scene.registerTouchArea(mListView);
		mListView.setOnItemClickListenner(new OnItemClickListenner() {

			@Override
			public void onClick(IAreaShape view, int position) {
				lockUserAction(false);
				((Item) view).setState(State.SELECTED);
				checkAnswer(((Item) view).getValue());
			}
		});

		final float centerY = mListView.getY() + (messageFrame.getY() - mListView.getY()) / 2;
		operator1 = new Text(0, 0, customFontBig, "", 3, getVertexBufferObjectManager());
		operator2 = new Text(0, 0, customFontBig, "", 3, getVertexBufferObjectManager());
		operator3 = new Text(0, 0, customFontBig, "", 3, getVertexBufferObjectManager());
		operand1 = new Text(0, 0, customFontBig, "", 3, getVertexBufferObjectManager());
		operand2 = new Text(0, 0, customFontBig, "", 3, getVertexBufferObjectManager());
		operator1.setX(-operator1.getWidth());
		operator2.setX(-operator2.getWidth());
		operator3.setX(CAMERA_WIDTH);

		operator1.setY(centerY - operand1.getHeight() / 2);
		operator2.setY(centerY - operator2.getHeight() / 2);
		operator3.setY(centerY - operator3.getHeight() / 2);
		operand1.setY(centerY - operand1.getHeight() / 2);
		operand2.setY(centerY - operand2.getHeight() / 2);

		operand1.setScale(0);
		operand2.setScale(0);
		scene.attachChild(operator1);
		scene.attachChild(operator2);
		scene.attachChild(operator3);
		scene.attachChild(operand1);
		scene.attachChild(operand2);
		tickTextManagable.setText(stringManger.getStringFromKey(StringManger.RUN_MSG));
		tickTextManagable.setY(messageFrame.getHeight() / 2 - tickTextManagable.getHeight() / 2);

	}

	@Override
	protected void onLoadResource() {
		BitmapTextureAtlas highscoreMenuAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (140),
				(int) (140), TextureOptions.BILINEAR);
		greenBubbleRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(highscoreMenuAtlas, this,
				"circle_green.png", 0, 0, 1, 1);
		highscoreMenuAtlas.load();
	}

	protected void checkAnswer(Object value) {
		Text mQuestionText = getQuesttionText();
		mListView.setRun(false);
		if (mGame.checkValue(value)) {
			mGame.incressRightAnswer();
			if (mQuestionText != null) {
				mQuestionText.registerEntityModifier(new ScaleModifier(Config.ANIMATE_DURATION, 1, 0,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

							}

							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
								((Text) pItem).setText(String.valueOf(mGame.getRightValue()));
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
		} else {
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					for (IAreaShape item : mListView.getChildrents()) {
						((Item) item).onNormalState();
					}
					lockUserAction(true);
					mListView.setRun(true);
				}
			}, 500);
		}
	}

	private Text getQuesttionText() {
		int posRs = mGame.getPosRs();
		int typeRs = mGame.getRsType();
		if (typeRs == 0) {
			switch (posRs) {
			case 0:
				return operator1;

			case 1:
				return operator2;

			case 2:
				return operator3;

			default:
				break;
			}
		} else {
			switch (posRs) {
			case 0:
				return operand1;

			case 1:
				return operand2;

			default:
				break;
			}
		}
		return null;
	}

	protected void start() {
		lockUserAction(false);
		mGame.generate(Config.getMax(preferences));

		operator1.setText(String.valueOf(mGame.getOperatorsVls()[0]));
		operator2.setText(String.valueOf(mGame.getOperatorsVls()[1]));
		operator3.setText(String.valueOf(mGame.getOperatorsVls()[2]));

		operand1.setText(String.valueOf(mGame.getOperand1()));
		operand2.setText(String.valueOf(mGame.getOperand2()));

		// set random COlor
		operator1.setColor(colors[random.nextInt(colors.length)]);
		operator2.setColor(colors[random.nextInt(colors.length)]);
		operator3.setColor(colors[random.nextInt(colors.length)]);
		operand1.setColor(colors[random.nextInt(colors.length)]);
		operand2.setColor(colors[random.nextInt(colors.length)]);

		adapter.setType(mGame.getRsType());

		int posRs = mGame.getPosRs();
		int typeRs = mGame.getRsType();
		if (typeRs == 0) {
			switch (posRs) {
			case 0:
				operator1.setText("?");
				break;

			case 1:
				operator2.setText("?");
				break;

			case 2:
				operator3.setText("?");
				break;

			default:
				break;
			}
		} else {
			switch (posRs) {
			case 0:
				operand1.setText("?");
				break;

			case 1:
				operand2.setText("?");
				break;

			default:
				break;
			}
		}

		// start track position for each text
		float totalWidth = 0;
		totalWidth += operator1.getWidth() + marginTextMath;
		totalWidth += operand1.getWidth() + marginTextMath;
		totalWidth += operator2.getWidth() + marginTextMath;
		totalWidth += operand2.getWidth() + marginTextMath;
		totalWidth += operator3.getWidth();
		float distance = CAMERA_WIDTH / 2 - totalWidth / 2;
		float operator11Destiny = distance;
		float operand1Destiny = operator11Destiny + operator1.getWidth() + marginTextMath;
		float operator2Destiny = operand1Destiny + operand1.getWidth() + marginTextMath;
		float operand2Destiny = operator2Destiny + operator2.getWidth() + marginTextMath;
		float operator3Destiny = operand2Destiny + operand2.getWidth() + marginTextMath;
		operator1.registerEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, operator1.getX(),
				operator11Destiny, EaseBounceOut.getInstance()));
		operator2.registerEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, operator2.getX(), operator2Destiny,
				EaseBounceOut.getInstance()));
		operator3.registerEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, operator3.getX(), operator3Destiny,
				EaseBounceOut.getInstance()));

		operand1.setScale(0);
		operand2.setScale(0);
		operand1.setX(operand1Destiny);
		operand2.setX(operand2Destiny);
		operand1.registerEntityModifier(new ScaleModifier(Config.ANIMATE_DURATION, 0, 1, EaseBounceOut.getInstance()));
		operand2.registerEntityModifier(new ScaleModifier(Config.ANIMATE_DURATION, 0, 1, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				lockUserAction(true);
				mListView.setRun(true);
			}
		}, EaseBounceOut.getInstance()));

	}

	private void moveOutMathFuntion() {
		operator1.registerEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, operator1.getX(), -operator1
				.getWidth(), EaseBounceIn.getInstance()));
		operator2.registerEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, operator2.getX(), CAMERA_WIDTH,
				EaseBounceIn.getInstance()));
		operator3.registerEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, operator3.getX(), CAMERA_WIDTH,
				EaseBounceIn.getInstance()));

		operand1.registerEntityModifier(new ScaleModifier(Config.ANIMATE_DURATION, 1, 0, EaseBounceIn.getInstance()));
		operand2.registerEntityModifier(new ScaleModifier(Config.ANIMATE_DURATION, 1, 0, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				for (IAreaShape item : mListView.getChildrents()) {
					((Item) item).onNormalState();
				}
				lockUserAction(true);
				mListView.setRun(true);
				start();
			}
		}, EaseBounceIn.getInstance()));
	}

	@Override
	public boolean onMenuItemClicked(HUD pMenuScene, final IMenuItem pMenuItem) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(RunModeActivity.this, "onMenuItemClicked " + pMenuItem.getID(), Toast.LENGTH_SHORT)
						.show();
			}
		});

		return false;
	}

	@Override
	protected void lockUserAction(boolean enable) {
		mListView.setEnable(enable);
	}

	@Override
	protected void onCloseEndGame() {
		operator1.setVisible(true);
		operator2.setVisible(true);
		operator3.setVisible(true);
		operand1.setVisible(true);
		operand2.setVisible(true);
		mListView.setVisible(true);
		mListView.setEnable(true);
		mListView.setRun(true);
		if(!endgame){
			start();
		}
	}

	@Override
	protected void onEndGame() {
		operator1.setVisible(false);
		operator2.setVisible(false);
		operator3.setVisible(false);
		operand1.setVisible(false);
		operand2.setVisible(false);
		mListView.setVisible(false);
		mListView.setEnable(false);
		mListView.setRun(false);
	}

}
