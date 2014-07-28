package com.bestfunforever.game.bubblemath.Util;

import java.util.Random;

import com.bestfunforever.andengine.uikit.util.Util;

public class FindModeGame {

	private static final String tag = "CountGame ";
	Random random;
	private int maxRightAnswer;
	private int rightAnswerCount;
	private int operator1;
	private int operator2;
	private int operator3;

	public int getOperator1() {
		return operator1;
	}

	public void setOperator1(int operator1) {
		this.operator1 = operator1;
	}

	public int getOperator2() {
		return operator2;
	}

	public void setOperator2(int operator2) {
		this.operator2 = operator2;
	}

	public int getOperator3() {
		return operator3;
	}

	public void setOperator3(int operator3) {
		this.operator3 = operator3;
	}

	public FindModeGame() {
		this(10);
	}

	public FindModeGame(int maxRightAnswer) {
		this.setMaxRightAnswer(maxRightAnswer);
		random = new Random(System.currentTimeMillis());
	}

	public void generate(int max) {
		operator1 = random.nextInt(max - 2);
		operator2 = operator1 + 1;
		operator3 = operator2 + 1;
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

}
