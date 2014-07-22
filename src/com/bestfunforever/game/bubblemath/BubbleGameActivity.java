package com.bestfunforever.game.bubblemath;

import org.andengine.entity.scene.Scene;

import com.bestfunforever.andengine.uikit.activity.PortraitAdmobGameActivity;

public class BubbleGameActivity extends PortraitAdmobGameActivity {

	@Override
	protected void onCreateResources() {
		
	}

	@Override
	protected Scene onCreateScene() {
		Scene scene = new Scene();
		MathExpanableMenu mMenu = new MathExpanableMenu(10, 300, this, mCamera, ratio);
		mMenu.init();
		scene.setChildScene(mMenu);
		return scene;
	}

}
