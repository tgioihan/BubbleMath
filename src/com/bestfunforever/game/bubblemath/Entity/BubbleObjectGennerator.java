package com.bestfunforever.game.bubblemath.Entity;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class BubbleObjectGennerator extends GenericPool<Item> {

	private ITextureRegion mTiledTextureRegion;
	private VertexBufferObjectManager vertexBufferObject;
	private float ratio;
	private Font font;

	public BubbleObjectGennerator(ITextureRegion mTiledTextureRegion, Font font, float ratio,
			VertexBufferObjectManager vertexBufferObject) {
		super();
		this.mTiledTextureRegion = mTiledTextureRegion;
		this.vertexBufferObject = vertexBufferObject;
		this.ratio = ratio;
		this.font = font;
	}

	@Override
	protected Item onAllocatePoolItem() {
		Item sprite = new Item(ratio, font, mTiledTextureRegion, vertexBufferObject);
		return sprite;
	}

}
