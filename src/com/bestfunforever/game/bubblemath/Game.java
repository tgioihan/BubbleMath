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

	public int[] getOperatorsVls() {
		return operatorsVls;
	}

	public void setOperatorsVls(int[] operatorsVls) {
		this.operatorsVls = operatorsVls;
	}

	private String operand1;
	private String operand2;
	private int posRs;
	
	private Object rightValue;

	public int getPosRs() {
		return posRs;
	}

	public void setPosRs(int posRs) {
		this.posRs = posRs;
	}

	public void generate(int maxInt) {
		int rsType = random.nextInt(2);
		if (rsType == 0) {
			generateFromOperator(maxInt);
		} else {
			gennerateFromOperand(maxInt);
		}
	}

	private void gennerateFromOperand(int maxInt) {
		posRs = random.nextInt(2);
		int oprRsPos = random.nextInt(3);
		String operandRs = operands[oprRsPos];
		rightValue  = operandRs;
		if (posRs == 0) {
			if (oprRsPos == 0) {
				setOperand1(operandRs);
				setOperand2(operands[2]);
				operatorsVls[0] = random.nextInt(maxInt);
				operatorsVls[1] = Util.randInt(0, maxInt - operatorsVls[0]);
				operatorsVls[2] = operatorsVls[0] + operatorsVls[1];
			} else if (oprRsPos == 1) {
				setOperand1(operandRs);
				setOperand2(operands[2]);
				operatorsVls[0] = random.nextInt(maxInt);
				operatorsVls[1] = Util.randInt(0, operatorsVls[0]);
				operatorsVls[2] = operatorsVls[0] - operatorsVls[1];
			} else if (oprRsPos == 2) {
				setOperand1(operandRs);
				int tmpPos = random.nextInt(2);
				setOperand2(operands[tmpPos]);
				if (tmpPos == 0) {
					operatorsVls[0] = random.nextInt(maxInt);
					operatorsVls[1] = Util.randInt(0, operatorsVls[0]);
					operatorsVls[2] = operatorsVls[0] - operatorsVls[1];
				} else if (tmpPos == 1) {
					operatorsVls[0] = random.nextInt(maxInt);
					operatorsVls[1] = Util.randInt(0, operatorsVls[0]);
					operatorsVls[2] = operatorsVls[1] - operatorsVls[0];
				}

			}
		} else if (posRs == 1) {
			if (oprRsPos == 0) {
				setOperand2(operandRs);
				setOperand1(operands[2]);
				operatorsVls[0] = random.nextInt(maxInt);
				operatorsVls[1] = Util.randInt(0, maxInt - operatorsVls[0]);
				operatorsVls[2] = operatorsVls[0] - operatorsVls[1];
			} else if (oprRsPos == 1) {
				setOperand2(operandRs);
				setOperand1(operands[2]);
				operatorsVls[1] = random.nextInt(maxInt);
				operatorsVls[0] = Util.randInt(0, maxInt - operatorsVls[0]);
				operatorsVls[2] = operatorsVls[1] - operatorsVls[0];
			} else if (oprRsPos == 2) {
				setOperand2(operandRs);
				int tmpPos = random.nextInt(2);
				setOperand1(operands[tmpPos]);
				if (tmpPos == 0) {
					operatorsVls[0] = random.nextInt(maxInt);
					operatorsVls[1] = Util.randInt(0, maxInt - operatorsVls[0]);
					operatorsVls[2] = operatorsVls[0] + operatorsVls[1];
				} else if (tmpPos == 1) {
					operatorsVls[0] = random.nextInt(maxInt);
					operatorsVls[1] = Util.randInt(0, maxInt - operatorsVls[0]);
					operatorsVls[2] = operatorsVls[0] - operatorsVls[1];
				}
			}
		}
	}

	private void generateFromOperator(int maxInt) {
		setOperand2(operands[2]);
		posRs = random.nextInt(3);
		int operatorRs = random.nextInt(maxInt);
		rightValue  = operatorRs;
		operatorsVls[posRs] = operatorRs;
		setOperand2(operands[2]);
		if (posRs == 2) {
			if (operatorRs > maxInt / 2) {
				setOperand1("+");
				operatorsVls[0] = random.nextInt(operatorRs + 1);
				operatorsVls[1] = operatorRs - operatorsVls[0];
			} else {
				int tmp = random.nextInt(2);
				setOperand1(operands[tmp]);
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
				setOperand1("+");
				operatorsVls[0] = Util.randInt(0, maxInt - operatorRs);
				operatorsVls[2] = operatorsVls[1] + operatorsVls[0];
			} else {
				int tmp = random.nextInt(2);
				setOperand1(operands[tmp]);
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
			setOperand1(operands[tmp]);
			if (tmp == 0) {
				operatorsVls[1] = Util.randInt(0, maxInt - operatorRs);
				operatorsVls[2] = operatorsVls[0] + operatorsVls[1];
			} else {
				operatorsVls[1] = Util.randInt(0, operatorRs);
				operatorsVls[2] = operatorsVls[0] - operatorsVls[0];
			}
		}

	}

	public String getOperand1() {
		return operand1;
	}

	public void setOperand1(String operand1) {
		this.operand1 = operand1;
	}

	public String getOperand2() {
		return operand2;
	}

	public void setOperand2(String operand2) {
		this.operand2 = operand2;
	}

	public boolean checkValue(Object value) {
		if (value.equals(operatorsVls[posRs])) {
			return true;
		}
		return false;
	}
	
	public Object getRightValue(){
		return rightValue;
	}

}
