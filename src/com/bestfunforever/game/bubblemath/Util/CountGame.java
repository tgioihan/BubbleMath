package com.bestfunforever.game.bubblemath.Util;

import java.util.Random;

import com.bestfunforever.andengine.uikit.util.Util;

public class CountGame {

	private static final String tag = "CountGame ";
	Random random;
	private int maxRightAnswer;
	private int rightAnswerCount;
	private int result;
	private int itemRsPos;
	private int totalItem;

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	public static final String[] fruitPng = new String[] { "apple.png",
			"cherry.png", "mango_icon.png", "orange_icon.png", "pear.png",
			"pineapple_icon.png" };
	public static final String[] fruitName = new String[] { "apple", "banana",
			"cherry", "pineapple", "manggo", "strawberry" };

	public CountGame() {
		this(10);
	}

	public CountGame(int maxRightAnswer) {
		this.setMaxRightAnswer(maxRightAnswer);
		random = new Random(System.currentTimeMillis());
	}

	public void generate(int max) {
		totalItem = Util.randInt(1, max - 1);
		setResult(Util.randInt(1, totalItem));
		setItemRsPos(random.nextInt(fruitPng.length));
	}

	public void reset() {
		setRightAnswerCount(0);
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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getItemRsPos() {
		return itemRsPos;
	}

	public void setItemRsPos(int itemRsPos) {
		this.itemRsPos = itemRsPos;
	}

	public void incressRightAnswer() {
		rightAnswerCount++;
	}

	public int getRightAnswerCount() {
		return rightAnswerCount;
	}

	public void setRightAnswerCount(int rightAnswerCount) {
		this.rightAnswerCount = rightAnswerCount;
	}

	public float getProcessPercent() {
		return rightAnswerCount * 100 / maxRightAnswer;
	}

	public boolean isComplete() {
		return rightAnswerCount == maxRightAnswer;
	}

}
