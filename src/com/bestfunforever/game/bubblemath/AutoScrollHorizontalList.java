package com.bestfunforever.game.bubblemath;

import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.bestfunforever.andengine.uikit.listview.HorizontalListView;
import com.bestfunforever.andengine.uikit.listview.ListView;

public class AutoScrollHorizontalList extends HorizontalListView {
	private float scrollVelocity = 2;

	public float getScrollVelocity() {
		return scrollVelocity;
	}

	public void setScrollVelocity(float scrollValue) {
		this.scrollVelocity = scrollValue;
	}

	private boolean run;

	public AutoScrollHorizontalList(SimpleBaseGameActivity Context, float pX,
			float pY, float pWidth, float pHeight,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(Context, pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		super.onManagedUpdate(pSecondsElapsed);
		if(run){
			if (mAdapter == null && mAdapter.getCount() == 0)
				return;
			scrollByX(scrollVelocity);
		}
	}

	/**
	 * @return the run
	 */
	public boolean isRun() {
		return run;
	}

	/**
	 * @param run
	 *            the run to set
	 */
	public void setRun(boolean run) {
		this.run = run;
	}

}
