package com.bestfunforever.game.bubblemath.Entity;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class SpriteWithValue extends Sprite {
	private int type;

	public SpriteWithValue(float pX, float pY, float ratio,
			ITextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pTiledSpriteVertexBufferObject) {
		super(pX, pY, pTiledTextureRegion.getWidth() * ratio,
				pTiledTextureRegion.getHeight() * ratio, pTiledTextureRegion,
				pTiledSpriteVertexBufferObject);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
