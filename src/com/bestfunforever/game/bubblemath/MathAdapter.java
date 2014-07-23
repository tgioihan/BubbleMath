package com.bestfunforever.game.bubblemath;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.bestfunforever.andengine.uikit.listview.SimpleAdapter;

public class MathAdapter extends SimpleAdapter {

	private TiledTextureRegion mRegion;
	private float ratio;
	private VertexBufferObjectManager pVertexBufferObjectManager;

	public MathAdapter(TiledTextureRegion iconHighScoreMenuRegion, float ratio, VertexBufferObjectManager pVertexBufferObjectManager) {
		super();
		this.mRegion = iconHighScoreMenuRegion;
		this.ratio = ratio;
		this.pVertexBufferObjectManager = pVertexBufferObjectManager;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public IAreaShape getView(int pos, IAreaShape view) {
		if (view == null) {
			view = new Item(ratio, mRegion, pVertexBufferObjectManager);
		}
		return view;
	}

	@Override
	public float getChildWidth() {
		// TODO Auto-generated method stub
		return mRegion.getWidth() * ratio;
	}

	@Override
	public float getChildHeight() {
		// TODO Auto-generated method stub
		return mRegion.getWidth() * ratio;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

}
