package com.bestfunforever.game.bubblemath;

import java.util.Random;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.bestfunforever.andengine.uikit.entity.BaseSprite.State;
import com.bestfunforever.andengine.uikit.listview.SimpleAdapter;

public class MathAdapter extends SimpleAdapter {

	private TiledTextureRegion mRegion;
	private float ratio;
	private VertexBufferObjectManager pVertexBufferObjectManager;
	private Font mFont;
	private Random random;
	private int max;

	private String[] operands = { "+", "-", "=" };

	public MathAdapter(TiledTextureRegion iconHighScoreMenuRegion, int max, float ratio, Font font,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super();
		this.mRegion = iconHighScoreMenuRegion;
		this.ratio = ratio;
		this.pVertexBufferObjectManager = pVertexBufferObjectManager;
		this.mFont = font;
		this.max = max;
		random = new Random();
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public IAreaShape getView(int pos, IAreaShape view) {
		if (view == null) {
			view = new Item(ratio, mFont, mRegion, pVertexBufferObjectManager);
		}
		int type = random.nextInt(2);
		Object obj = null;
		if (type == 0) {
			// number
			obj = random.nextInt(max);
		} else {
			obj = operands[random.nextInt(3)];
		}
		Log.d("", "MathAdapter getview at " + pos + " with value " + obj);
		((Item) view).setText(obj);
		((Item) view).setState(State.NORMAL);
		return view;
	}

	@Override
	public float getChildWidth() {
		return mRegion.getWidth() * ratio;
	}

	@Override
	public float getChildHeight() {
		return mRegion.getWidth() * ratio;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

}
