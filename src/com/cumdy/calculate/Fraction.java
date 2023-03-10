package com.cumdy.calculate;

public class Fraction {
	public static int[] GetFraction(double input) {
		int p0 = 1;
		int q0 = 0;
		int p1 = (int) Math.floor(input);
		int q1 = 1;
		int p2;
		int q2;

		double r = input - p1;
		double next_cf;
		while (true) {
			r = 1.0 / r;
			next_cf = Math.floor(r);
			p2 = (int) (next_cf * p1 + p0);
			q2 = (int) (next_cf * q1 + q0);

			// Limit the numerator and denominator to be 256 or less
			if (p2 > 256 || q2 > 256)
				break;

			// remember the last two fractions
			p0 = p1;
			p1 = p2;
			q0 = q1;
			q1 = q2;

			r -= next_cf;
		}

		return new int[] { p1, q1 };
	}

	public static int[] sum(int[] f1, int[] f2) {
		int[] fr = new int[2];
		fr[1] = f1[1] * f2[1];
		fr[0] = f1[0] * f2[1] + f2[0] * f1[1];
		double result = (double) fr[0] / (double) fr[1];
		return GetFraction(result);
	}

	public static int[] sub(int[] f1, int[] f2) {
		int[] fr = new int[2];
		fr[1] = f1[1] * f2[1];
		fr[0] = f1[0] * f2[1] - f2[0] * f1[1];
		double result = (double) fr[0] / (double) fr[1];
		return GetFraction(result);
	}

	public static int[] multiply(int[] f1, int[] f2) {
		int[] fr = new int[2];
		fr[0] = f1[0] * f2[0];
		fr[1] = f1[1] * f2[1];
		double result = (double) fr[0] / (double) fr[1];
		return GetFraction(result);
	}
}
