package com.bestfunforever.game.bubblemath;

import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import com.bestfunforever.andengine.uikit.entity.BaseSprite;

public class Item extends BaseSprite {

	private Object value;
	private Font mfont;
	private Text mText;

	public Item(float ratio, Font font, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(0, 0, pTextureRegion.getWidth() * ratio, pTextureRegion.getHeight() * ratio, pTextureRegion,
				pVertexBufferObjectManager);
		this.mfont = font;

		mText = new Text(0, getHeight() / 2, mfont, String.valueOf(value), 5, new TextOptions(AutoWrap.WORDS,
				getWidth(), HorizontalAlign.CENTER), getVertexBufferObjectManager());
		mText.setColor(Color.RED);
		mText.setY(getHeight() / 2 - mText.getHeight() / 2);
		attachChild(mText);
	}

	public void setText(Object obj) {
		mText.setText(String.valueOf(obj));
		setValue(obj);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public void onSelectedState() {
		setColor(180f / 255f, 180f / 255f, 180f / 255f);
	}

	@Override
	public void onNormalState() {
		setColor(1f, 1f, 1f);
	}

	@Override
	public void onPressState() {
		setColor(180f / 255f, 180f / 255f, 180f / 255f);
	}

}
