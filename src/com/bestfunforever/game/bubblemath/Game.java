package com.bestfunforever.game.bubblemath;

import java.util.Random;

import com.bestfunforever.andengine.uikit.util.Util;

public class Game {
	Random random;

	public Game() {
		random = new Random();
	}

	public enum RsType {
		OPERATOR, OPERAND
	}

	private String[] operands = { "+", "-", "=" };

	private int[] operatorsVls = new int[3];

	private String operand1;
	private String operand2;
	private int posRs;

	public void generate(int maxInt) {
		int rsType = random.nextInt(2);
		if (rsType == 0) {
			generateFromOperator(maxInt);
		} else {
			gennerateFromOperand(maxInt);
		}
	}

	private void gennerateFromOperand(int maxInt) {

	}

	private void generateFromOperator(int maxInt) {
		operand2 = operands[2];
		posRs = random.nextInt(3);
		int operatorRs = random.nextInt(maxInt);
		operatorsVls[posRs] = operatorRs;
		operand2 = operands[2];
		if (posRs == 2) {
			if (operatorRs > maxInt / 2) {
				operand1 = "+";
				operatorsVls[0] = random.nextInt(operatorRs + 1);
				operatorsVls[1] = operatorRs - operatorsVls[0];
			} else {
				int tmp = random.nextInt(2);
				operand1 = operands[tmp];
				if (tmp == 0) {
					operatorsVls[0] = random.nextInt(operatorRs + 1);
					operatorsVls[1] = operatorRs - operatorsVls[0];
				} else {
					operatorsVls[0] = Util.randInt(operatorRs, maxInt);
					operatorsVls[1] = -operatorRs + operatorsVls[0];
				}
			}
		} else if (posRs == 1) {
			if (operatorRs > maxInt / 2) {
				operand1 = "+";
				operatorsVls[0] = Util.randInt(0, maxInt - operatorRs);
				operatorsVls[2] = operatorsVls[1] + operatorsVls[0];
			} else {
				int tmp = random.nextInt(2);
				operand1 = operands[tmp];
				if (tmp == 0) {
					operatorsVls[0] = Util.randInt(0, maxInt - operatorRs);
					operatorsVls[2] = operatorsVls[1] + operatorsVls[0];
				} else {
					operatorsVls[0] = Util.randInt(operatorRs, maxInt);
					operatorsVls[2] = -operatorsVls[0] + operatorsVls[0];
				}
			}
		} else if (posRs == 0) {
			int tmp = random.nextInt(2);
			operand1 = operands[tmp];
			if (tmp == 0) {
				operatorsVls[1] = Util.randInt(0, maxInt - operatorRs);
				operatorsVls[2] = operatorsVls[0] + operatorsVls[1];
			} else {
				operatorsVls[1] = Util.randInt(0, operatorRs);
				operatorsVls[2] = operatorsVls[0] - operatorsVls[0];
			}
		}

	}

}
