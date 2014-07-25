package com.bestfunforever.game.bubblemath;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TickerText.TickerTextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBounceIn;
import org.andengine.util.modifier.ease.EaseBounceOut;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.bestfunforever.andengine.uikit.activity.PortraitAdmobGameActivity;
import com.bestfunforever.andengine.uikit.entity.BaseSprite.State;
import com.bestfunforever.andengine.uikit.entity.TickerTextManagable;
import com.bestfunforever.andengine.uikit.entity.TickerTextManagable.ITickerTextListenner;
import com.bestfunforever.andengine.uikit.listview.OnItemClickListenner;
import com.bestfunforever.andengine.uikit.menu.BaseMenu.IOnMenuItemClickListener;
import com.bestfunforever.andengine.uikit.menu.IMenuItem;

public class BubbleGameActivity extends PortraitAdmobGameActivity implements IOnMenuItemClickListener {

	private TiledTextureRegion greenBubbleRegion;
	private AutoScrollHorizontalList mListView;
	private TiledTextureRegion childFaceRegion;
	private TextureRegion mBgTextureRegion;
	private TiledTextureRegion messageRegion;
	private Font mFont;
	private BitmapTextureAtlas customFontTexture;
	private StrokeFont customFont;
	private Game mGame;
	private Text operator1;
	private Text operator2;
	private Text operator3;
	private Text operand1;
	private Text operand2;
	private Scene scene;
	private BitmapTextureAtlas customFontBigTexture;
	private StrokeFont customFontBig;
	private Sprite child;
	private Handler handler ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		handler = new Handler();
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas highscoreMenuAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (140),
				(int) (140), TextureOptions.BILINEAR);
		greenBubbleRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(highscoreMenuAtlas, this,
				"green_bubble.png", 0, 0, 1, 1);
		highscoreMenuAtlas.load();

		BitmapTextureAtlas childAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (195), (int) (192),
				TextureOptions.BILINEAR);
		childFaceRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(childAtlas, this,
				"child_face.png", 0, 0, 1, 1);
		childAtlas.load();

		BitmapTextureAtlas messageAtlas = new BitmapTextureAtlas(this.getTextureManager(), (int) (356), (int) (152),
				TextureOptions.BILINEAR);
		messageRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(messageAtlas, this,
				"message_frame.png", 0, 0, 1, 1);
		messageAtlas.load();

		BitmapTextureAtlas mBgBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 640, 960,
				TextureOptions.BILINEAR);
		this.mBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBgBitmapTextureAtlas, this,
				"bg5.jpg", 0, 0); // 64x32
		mBgBitmapTextureAtlas.load();

		customFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		customFont = FontFactory.createStrokeFromAsset(getFontManager(), customFontTexture, getAssets(),
				"UVNBanhMi.TTF", (float) 80 * ratio, true, Color.WHITE, 2, Color.RED);
		customFont.load();

		customFontBigTexture = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		customFontBig = FontFactory.createStrokeFromAsset(getFontManager(), customFontBigTexture, getAssets(),
				"UVNBanhMi.TTF", (float) 145 * ratio, true, Color.WHITE, 2, Color.RED);
		customFontBig.load();

		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), (int) (256 * ratio),
				(int) (256 * ratio), Typeface.create(Typeface.DEFAULT, Typeface.BOLD), (int) (32 * ratio));
		this.mFont.load();
		
		marginTextMath = marginTextMath * ratio;
	}

	@Override
	protected Scene onCreateScene() {
		scene = new Scene();
		mGame = new Game();
		scene.setBackground(new SpriteBackground(new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, mBgTextureRegion,
				getVertexBufferObjectManager())));
		this.mEngine.registerUpdateHandler(new FPSLogger());

		mListView = new AutoScrollHorizontalList(this, 10 * ratio, 50 * ratio, CAMERA_WIDTH - 20 * ratio,
				greenBubbleRegion.getHeight() * ratio, getVertexBufferObjectManager());
		MathAdapter adapter = new MathAdapter(greenBubbleRegion, 10, ratio, customFont, getVertexBufferObjectManager());
		scene.attachChild(mListView);
		mListView.setAdapter(adapter);
		mListView.setSelection(Integer.MAX_VALUE / 2);
		mListView.setScrollVelocity(-3f);
		scene.registerTouchArea(mListView);
		mListView.setOnItemClickListenner(new OnItemClickListenner() {

			@Override
			public void onClick(IAreaShape view, int position) {
				((Item) view).setState(State.SELECTED);
				checkAnswer(((Item) view).getValue());
				mListView.setRun(false);
			}
		});

		MathExpanableMenu mMenu = new MathExpanableMenu(10, CAMERA_HEIGHT - 110 * ratio, this, mCamera, ratio);
		mMenu.init();
		mMenu.setOnMenuItemClickListener(this);
		scene.setChildScene(mMenu);

		child = new Sprite(30, mMenu.getMenuPositionY() - childFaceRegion.getHeight() * ratio,
				childFaceRegion.getWidth() * ratio, childFaceRegion.getHeight() * ratio, childFaceRegion,
				getVertexBufferObjectManager());
		scene.attachChild(child);
		Sprite messageFrame = new Sprite(child.getX() + child.getWidth() + 30 * ratio, mMenu.getMenuPositionY()
				- messageRegion.getHeight() * ratio, messageRegion.getWidth() * ratio, messageRegion.getHeight()
				* ratio, messageRegion, getVertexBufferObjectManager());
		scene.attachChild(messageFrame);

		TickerTextManagable tickTextManagable = new TickerTextManagable(10 * ratio, 10 * ratio, mFont,
				"Giup minh giai bai toan nay voi , kho qua , hu hu", new TickerTextOptions(AutoWrap.WORDS,
						messageFrame.getWidth() - 2 * 10 * ratio, HorizontalAlign.CENTER, 15),
				getVertexBufferObjectManager());
		tickTextManagable.setTickerTextListenner(new ITickerTextListenner() {

			@Override
			public void onTickerTextComplete() {
				mListView.setRun(true);
				generateFunction();
			}
		});
		messageFrame.attachChild(tickTextManagable);

		initMathGraphicObjects();
		return scene;
	}

	protected void checkAnswer(Object value) {
		if (mGame.checkValue(value)) {
			if (mQuestionText != null) {
				mQuestionText.registerEntityModifier(new ScaleModifier(animDuration, 1, 0,
						new IEntityModifierListener() {

							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

							}

							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
								mQuestionText.setText(String.valueOf(mGame.getRightValue()));
								mQuestionText.registerEntityModifier(new ScaleModifier(animDuration, 0, 1,
										new IEntityModifierListener() {

											@Override
											public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

											}

											@Override
											public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
												moveOutMathFuntion();
											}
										}, EaseBounceOut.getInstance()));
							}
						}, EaseBounceOut.getInstance()));
			}
		}else{
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					for (IAreaShape item : mListView.getChildrents()) {
						((Item) item).onNormalState();
					}
					mListView.setRun(true);
				}
			}, 500);
		}
	}

	private void initMathGraphicObjects() {
		final float centerY = mListView.getY() + (child.getY() - mListView.getY()) / 2;
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

	}

	private float animDuration = 0.5f;
	private float marginTextMath = 30f;
	private Text mQuestionText;

	protected void generateFunction() {
		mGame.generate(10);
		operator1.clearEntityModifiers();
		operator2.clearEntityModifiers();
		operator3.clearEntityModifiers();
		operand1.clearEntityModifiers();
		operand2.clearEntityModifiers();

		operator1.setText(String.valueOf(mGame.getOperatorsVls()[0]));
		operator2.setText(String.valueOf(mGame.getOperatorsVls()[1]));
		operator3.setText(String.valueOf(mGame.getOperatorsVls()[2]));

		operand1.setText(String.valueOf(mGame.getOperand1()));
		operand2.setText(String.valueOf(mGame.getOperand2()));

		int posRs = mGame.getPosRs();
		switch (posRs) {
		case 0:
			operator1.setText("?");
			mQuestionText = operator1;
			break;

		case 1:
			operator2.setText("?");
			mQuestionText = operator2;
			break;

		case 2:
			operator3.setText("?");
			mQuestionText = operator3;
			break;

		case 3:
			operand1.setText("?");
			mQuestionText = operand1;
			break;

		case 4:
			operand2.setText("?");
			mQuestionText = operand1;
			break;

		default:
			break;
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
		operator1.registerEntityModifier(new MoveXModifier(animDuration, operator1.getX(), operator11Destiny,
				EaseBounceOut.getInstance()));
		operator2.registerEntityModifier(new MoveXModifier(animDuration, operator2.getX(), operator2Destiny,
				EaseBounceOut.getInstance()));
		operator3.registerEntityModifier(new MoveXModifier(animDuration, operator3.getX(), operator3Destiny,
				EaseBounceOut.getInstance()));

		operand1.setScale(0);
		operand2.setScale(0);
		operand1.setX(operand1Destiny);
		operand2.setX(operand2Destiny);
		operand1.registerEntityModifier(new ScaleModifier(animDuration, 0, 1, EaseBounceOut.getInstance()));
		operand2.registerEntityModifier(new ScaleModifier(animDuration, 0, 1, EaseBounceOut.getInstance()));

	}

	private void moveOutMathFuntion() {
		operator1.registerEntityModifier(new MoveXModifier(animDuration, operator1.getX(), -operator1.getWidth(),
				EaseBounceIn.getInstance()));
		operator2.registerEntityModifier(new MoveXModifier(animDuration, operator2.getX(), CAMERA_WIDTH, EaseBounceIn
				.getInstance()));
		operator3.registerEntityModifier(new MoveXModifier(animDuration, operator3.getX(), CAMERA_WIDTH, EaseBounceIn
				.getInstance()));

		operand1.registerEntityModifier(new ScaleModifier(animDuration, 1, 0, EaseBounceIn.getInstance()));
		operand2.registerEntityModifier(new ScaleModifier(animDuration, 1, 0, new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				for (IAreaShape item : mListView.getChildrents()) {
					((Item) item).onNormalState();
				}
				mListView.setRun(true);
				generateFunction();
			}
		}, EaseBounceIn.getInstance()));
	}

	@Override
	public boolean onMenuItemClicked(HUD pMenuScene, final IMenuItem pMenuItem) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(BubbleGameActivity.this, "onMenuItemClicked " + pMenuItem.getID(), Toast.LENGTH_SHORT)
						.show();
			}
		});

		return false;
	}

}
