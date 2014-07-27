package com.bestfunforever.game.bubblemath.Entity;

import java.util.Random;

public class GameUtil {
	public static int[] gennerateRandomArray(Random random, int count,
			int totlaItemCOunt) {
		int[] rsPositions = new int[count];
		int i = 0;
		int vl = -1;
		boolean accespt = false;
		while (i < count) {
			if (i == 0) {
				rsPositions[i] = random.nextInt(totlaItemCOunt);
				i++;
			} else {
				vl = random.nextInt(totlaItemCOunt);
				accespt = true;
				for (int j = 0; j < i; j++) {
					if (vl == rsPositions[j]) {
						accespt = false;
					}
				}
				if (accespt) {
					rsPositions[i] = vl;
					i++;
				}
			}
		}
		return rsPositions;
	}

	public static int[] gennerateRandomArrayExcept(Random random, int count,
			int excep, int totlaItemCOunt) {
		int[] rsPositions = new int[count];
		int i = 0;
		int vl = -1;
		boolean accespt = false;
		while (i < count) {
			vl = random.nextInt(totlaItemCOunt);
			accespt = true;
			if (i == 0) {
				if (vl == excep) {
					accespt = false;
				}
			} else {
				for (int j = 0; j < i; j++) {
					if (vl == rsPositions[j] || vl == excep) {
						accespt = false;
					}
				}
			}
			if (accespt) {
				rsPositions[i] = vl;
				i++;
			}
		}
		return rsPositions;
	}

	public static boolean matchItemInArray(int obj, int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == obj) {
				return true;
			}
		}
		return false;
	}

	public static int genneratePosionNotMatch(Random random, int obj, int max) {
		int rs = -1;
		int tmp = -1;
		while (rs == -1) {
			tmp = random.nextInt(max);
			if (tmp != obj) {
				rs = tmp;
				return rs;
			}
		}
		return rs;
	}
}
