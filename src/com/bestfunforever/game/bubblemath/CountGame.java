package com.bestfunforever.game.bubblemath;

import java.util.Random;

import com.bestfunforever.andengine.uikit.util.Util;

public class CountGame {

	private static final String tag = "CountGame ";
	Random random;
	private int maxRightAnswer;

	private String[] fruitName = new String[] {};
	private String[] fruitPng = new String[] {};

	public CountGame() {
		this(10);
	}

	public CountGame(int maxRightAnswer) {
		this.setMaxRightAnswer(maxRightAnswer);
		random = new Random();
	}

	public void generate(int max) {
		int totalItem = Util.randInt(1, max-1);
		int result = Util.randInt(1, totalItem);
//		int itemRsPos = random.nextInt(item)
	}

	/**
	 * @return the maxRightAnswer
	 */
	public int getMaxRightAnswer() {
		return maxRightAnswer;
	}

	/**
	 * @param maxRightAnswer
	 *            the maxRightAnswer to set
	 */
	public void setMaxRightAnswer(int maxRightAnswer) {
		this.maxRightAnswer = maxRightAnswer;
	}

}
