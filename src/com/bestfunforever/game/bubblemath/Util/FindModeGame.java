package com.bestfunforever.game.bubblemath.Util;

import java.util.Random;

import android.util.Log;

import com.bestfunforever.andengine.uikit.util.Util;

public class FindModeGame {

	private static final String tag = "CountGame ";
	Random random;
	private int maxRightAnswer;
	private int rightAnswerCount;
	private int resultPosition;
	private int[] listNumbers;
	private int result;

	public int[] getListNumbers() {
		return listNumbers;
	}

	public void setListNumbers(int[] listNumbers) {
		this.listNumbers = listNumbers;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public FindModeGame() {
		this(10);
	}

	public FindModeGame(int maxRightAnswer) {
		this.setMaxRightAnswer(maxRightAnswer);
		random = new Random(System.currentTimeMillis());
	}

	public void generate(int max) {
		int divValue = Util.randInt(1, 3);
		int itemCountMax = max / divValue;
		Log.d(tag, tag+" divValue "+divValue+" itemCountMax "+itemCountMax);
		int itemCOunt = Util.randInt(3, itemCountMax);
		int startItem = Util.randInt(0, max - divValue * itemCOunt);
		setResultPosition(random.nextInt(itemCOunt));
		listNumbers = new int[itemCOunt];
		for (int i = 0; i < listNumbers.length; i++) {
			listNumbers[i] = startItem + i * divValue;
		}
		setResult(listNumbers[resultPosition]);
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

	public int getResultPosition() {
		return resultPosition;
	}

	public void setResultPosition(int resultPosition) {
		this.resultPosition = resultPosition;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
