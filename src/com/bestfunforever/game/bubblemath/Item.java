package com.bestfunforever.game.bubblemath;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Item extends Sprite {

	public Item(float ratio, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(0, 0, pTextureRegion.getWidth() * ratio, pTextureRegion.getHeight() * ratio, pTextureRegion,
				pVertexBufferObjectManager);
	}

}
