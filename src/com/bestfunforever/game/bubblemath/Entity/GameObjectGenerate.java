package com.bestfunforever.game.bubblemath.Entity;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class GameObjectGenerate extends GenericPool<SpriteWithValue> {

	private ITextureRegion mTiledTextureRegion;
	private VertexBufferObjectManager vertexBufferObject;
	private float ratio;

	public GameObjectGenerate(ITextureRegion mTiledTextureRegion, float ratio,
			VertexBufferObjectManager vertexBufferObject) {
		super();
		this.mTiledTextureRegion = mTiledTextureRegion;
		this.vertexBufferObject = vertexBufferObject;
		this.ratio = ratio;
	}

	@Override
	protected SpriteWithValue onAllocatePoolItem() {
		SpriteWithValue sprite = new SpriteWithValue(0, 0, ratio,
				mTiledTextureRegion, vertexBufferObject);
		return sprite;
	}

}
