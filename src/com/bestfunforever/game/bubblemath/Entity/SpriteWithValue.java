package com.bestfunforever.game.bubblemath.Entity;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.bestfunforever.andengine.uikit.entity.Sprite.BaseSprite;

public class SpriteWithValue extends BaseSprite {

	public SpriteWithValue(float pX, float pY, float ratio,
			ITextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pTiledSpriteVertexBufferObject) {
		super(pX, pY, pTiledTextureRegion.getWidth() * ratio,
				pTiledTextureRegion.getHeight() * ratio, pTiledTextureRegion,
				pTiledSpriteVertexBufferObject);
	}

	public int getType() {
		return getId();
	}

	public void setType(int type) {
		setId(type);
	}

	@Override
	public void onSelectedState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNormalState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPressState() {
		// TODO Auto-generated method stub
		
	}
}
