package com.bestfunforever.game.bubblemath.Entity.MainMenu;

import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.Scene;

import com.bestfunforever.andengine.uikit.entity.IClick;

public interface IMenuRectangle {
	public void attachToScene(Scene scene);

	public void show();

	public void hide(IEntityModifierListener listenner);

	public void setClickListenner(IClick mClickListenner);

	public void detachFromScene(Scene scene);
	
	public void resetMenu();
}
