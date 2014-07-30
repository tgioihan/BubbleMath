package com.bestfunforever.game.bubblemath.Entity.MainMenu;

import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseBackIn;
import org.andengine.util.modifier.ease.EaseBounceOut;

import android.content.SharedPreferences;

import com.bestfunforever.andengine.uikit.entity.ICheckedChange;
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
	private BubbleSprite moreSprite;

	private ICheckedChange soundCheckedChange;
	private ICheckedChange musicCheckedChange;

	SharedPreferences pref;
	private ITextureRegion mEngRegion;
	private ITextureRegion mViRegion;
	private BubbleSprite engBox;
	private BubbleSprite viBox;
	private Text soundLabel;
	private Text musicLabel;
	private StringManger stringManger;
	private IClick languageClick;
	public IClick getLanguageClick() {
		return languageClick;
	}

	public void setLanguageClick(IClick languageClick) {
		this.languageClick = languageClick;
	}

	private int language;

	public MenuSettingGameRectangle(SimpleBaseGameActivity context, SharedPreferences preferrece, StringManger stringManger,
			float pX, float pY, float pWidth, float pHeight, float ratio, Font font, TiledTextureRegion soundRegion,
			TiledTextureRegion musicRegion, TextureRegion mBackRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		setColor(Color.TRANSPARENT);
		this.pref = preferrece;
		this.stringManger = stringManger;

		onLoadResource(context);

		soundLabel = new Text(0, 0, font, stringManger.getStringFromKey(StringManger.SOUND),20, pVertexBufferObjectManager);
		soundCheckBox = new CheckBox(pWidth - soundRegion.getWidth() * ratio, 0, ratio,
				Config.getSoundState(pref) == Config.KEY_ON, soundRegion, pVertexBufferObjectManager);
		soundCheckBox.setOnCheckedChangeListenner(new ICheckedChange() {

			@Override
			public void onCheckedChange(boolean checked) {
				if (soundCheckedChange != null) {
					soundCheckedChange.onCheckedChange(checked);
				}
			}
		});
		float height1 = Math.max(soundLabel.getHeight(), soundCheckBox.getHeight());
		soundRectangle = new Rectangle(0, 0, pWidth, height1, pVertexBufferObjectManager);
		soundCheckBox.setY(height1 / 2 - soundCheckBox.getHeight() / 2);
		soundLabel.setY(height1 / 2 - soundLabel.getHeight() / 2);
		soundRectangle.setX(-soundRectangle.getWidth());
		soundRectangle.setColor(Color.TRANSPARENT);
		soundRectangle.attachChild(soundLabel);
		soundRectangle.attachChild(soundCheckBox);

		musicLabel = new Text(0, 0, font, stringManger.getStringFromKey(StringManger.MUSIC),20, pVertexBufferObjectManager);
		musicCheckBox = new CheckBox(pWidth - musicRegion.getWidth() * ratio, 0, ratio,
				Config.getMusicState(pref) == Config.KEY_ON, musicRegion, pVertexBufferObjectManager);
		musicCheckBox.setOnCheckedChangeListenner(new ICheckedChange() {

			@Override
			public void onCheckedChange(boolean checked) {
				if (musicCheckedChange != null) {
					musicCheckedChange.onCheckedChange(checked);
				}
			}
		});
		float height2 = Math.max(musicLabel.getHeight(), musicCheckBox.getHeight());
		musicRectangle = new Rectangle(0, soundRectangle.getY() + soundRectangle.getHeight(), pWidth, height2,
				pVertexBufferObjectManager);
		musicCheckBox.setY(height2 / 2 - musicCheckBox.getHeight() / 2);
		musicLabel.setY(height1 / 2 - musicLabel.getHeight() / 2);
		musicRectangle.setX(pWidth);
		musicRectangle.setColor(Color.TRANSPARENT);
		musicRectangle.attachChild(musicLabel);
		musicRectangle.attachChild(musicCheckBox);
		
		engBox = new BubbleSprite(0, musicRectangle.getY() + musicRectangle.getHeight() + 10 * ratio, ratio,
				null, null, mEngRegion, pVertexBufferObjectManager);
		viBox = new BubbleSprite(0, musicRectangle.getY() + musicRectangle.getHeight() + 10 * ratio, ratio,
				null, null, mViRegion, pVertexBufferObjectManager);
		engBox.setX(-engBox.getWidth());
		viBox.setX(pWidth);
		engBox.setClickListenner(new IClick() {
			
			@Override
			public void onCLick(IAreaShape view) {
				if(language!=Config.KEY_LANGUAGE_ENG){
					Config.setLanguage(pref, Config.KEY_LANGUAGE_ENG);
					setColorSelected(engBox);
					setColorNormal(viBox);
					if(languageClick!=null){
						languageClick.onCLick(null);
					}
				}
			}
		});
		viBox.setClickListenner(new IClick() {
			
			@Override
			public void onCLick(IAreaShape view) {
				if(language!=Config.KEY_LANGUAGE_VNI){
					Config.setLanguage(pref, Config.KEY_LANGUAGE_VNI);
					setColorSelected(viBox);
					setColorNormal(engBox);
					if(languageClick!=null){
						languageClick.onCLick(null);
					}
				}
			}
		});
		language = Config.getLanguage(pref);
		if(language==Config.KEY_LANGUAGE_ENG){
			setColorSelected(engBox);
			setColorNormal(viBox);
		}else if(language==Config.KEY_LANGUAGE_VNI){
			setColorSelected(viBox);
			setColorNormal(engBox);
		}

		moreSprite = new BubbleSprite(pWidth, engBox.getY() + engBox.getHeight(), ratio, null, null, mBackRegion,
				getVertexBufferObjectManager());
		moreSprite.setId(Config.MENU__BACK);
		attachChild(moreSprite);
		attachChild(soundRectangle);
		attachChild(musicRectangle);
		attachChild(engBox);
		attachChild(viBox);
	}
	
	private void setColorSelected(BubbleSprite sprite){
		sprite.setColor(180f / 255f, 180f / 255f, 180f / 255f);
	}
	
	private void setColorNormal(BubbleSprite sprite){
		sprite.setColor(1f, 1f, 1f);
	}


	private void onLoadResource(SimpleBaseGameActivity context) {
		BitmapTextureAtlas mEngAtlas = new BitmapTextureAtlas(context.getTextureManager(), 640, 960,
				TextureOptions.BILINEAR);
		this.mEngRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mEngAtlas, context, "usa_flag.png", 0,
				0); // 64x32
		mEngAtlas.load();

		BitmapTextureAtlas mViAtlas = new BitmapTextureAtlas(context.getTextureManager(), 640, 960,
				TextureOptions.BILINEAR);
		this.mViRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mViAtlas, context, "vietnam_flag.png",
				0, 0); // 64x32
		mViAtlas.load();
	}

	public void attachToScene(Scene scene) {
		resetMenu();
		scene.attachChild(this);
		scene.registerTouchArea(soundCheckBox);
		scene.registerTouchArea(musicCheckBox);
		scene.registerTouchArea(moreSprite);
		scene.registerTouchArea(engBox);
		scene.registerTouchArea(viBox);
	}

	public void show(IEntityModifierListener listenner) {
		soundLabel.setText(stringManger.getStringFromKey(StringManger.SOUND));
		musicLabel.setText(stringManger.getStringFromKey(StringManger.MUSIC));
		musicCheckBox.setChecked(Config.getSoundState(pref) == Config.KEY_ON);
		soundCheckBox.setChecked(Config.getMusicState(pref) == Config.KEY_ON);
		language = Config.getLanguage(pref);
		if(language==Config.KEY_LANGUAGE_ENG){
			setColorSelected(engBox);
			setColorNormal(viBox);
		}else if(language==Config.KEY_LANGUAGE_VNI){
			setColorSelected(viBox);
			setColorNormal(engBox);
		}
		
		soundRectangle.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				soundRectangle.getX(), 0, EaseBounceOut.getInstance())));
		musicRectangle.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				musicRectangle.getX(), 0, EaseBounceOut.getInstance())));
		engBox.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, engBox
				.getX(), 0, listenner, EaseBounceOut.getInstance())));
		viBox.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				viBox.getX(), getWidth()-viBox.getWidth(), listenner, EaseBounceOut.getInstance())));
		moreSprite.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				moreSprite.getX(), getWidth() / 2 - moreSprite.getWidth() / 2, EaseBounceOut.getInstance())));
	}

	public void hide(IEntityModifierListener listenner) {
		soundRectangle.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				-soundRectangle.getX(), -soundRectangle.getWidth(), EaseBackIn.getInstance())));
		musicRectangle.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				musicRectangle.getX(), getWidth(), listenner, EaseBackIn.getInstance())));
		engBox.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION, engBox
				.getX(), -engBox.getWidth(), EaseBackIn.getInstance())));
		viBox.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				viBox.getX(), getWidth(), listenner, EaseBackIn.getInstance())));
		moreSprite.registerEntityModifier(new ParallelEntityModifier(new MoveXModifier(Config.ANIMATE_DURATION,
				moreSprite.getX(), getWidth(), listenner, EaseBackIn.getInstance())));
	}

	public void setClickListenner(IClick mClickListenner) {
		moreSprite.setClickListenner(mClickListenner);
		// soundCheckBox.setClickListenner(mClickListenner);
		// musicCheckBox.setClickListenner(mClickListenner);
	}

	public void detachFromScene(Scene scene) {
		scene.unregisterTouchArea(soundCheckBox);
		scene.unregisterTouchArea(musicCheckBox);
		scene.unregisterTouchArea(moreSprite);
		scene.unregisterTouchArea(engBox);
		scene.unregisterTouchArea(viBox);
		scene.detachChild(this);
	}

	@Override
	public void resetMenu() {
		soundRectangle.setX(-soundRectangle.getWidth());
		musicRectangle.setX(getWidth());
		moreSprite.setX(getWidth());
	}

	@Override
	public void lockUserAction(boolean enable) {
		soundCheckBox.setEnable(enable);
		musicCheckBox.setEnable(enable);
		moreSprite.setEnable(enable);
		viBox.setEnable(enable);
		engBox.setEnable(enable);
	}

	public ICheckedChange getSoundCheckedChange() {
		return soundCheckedChange;
	}

	public void setSoundCheckedChange(ICheckedChange soundCheckedChange) {
		this.soundCheckedChange = soundCheckedChange;
	}

	public ICheckedChange getMusicCheckedChange() {
		return musicCheckedChange;
	}

	public void setMusicCheckedChange(ICheckedChange musicCheckedChange) {
		this.musicCheckedChange = musicCheckedChange;
	}

}
