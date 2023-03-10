package com.cumdy.entity;

import com.cumdy.calculate.Fraction;

public class Variable implements Comparable<Variable> {
	public double m;
	public double v;
	public boolean define = true;

	public Variable(double v) {
		this(0.0, v);
	}

	public Variable(double m, double v) {
		this.m = m;
		this.v = v;
	}

	/*
	 * @Override public int compare(Variable var1, Variable var2) {
	 * 
	 * }
	 */

	@Override
	public String toString() {
		String value = new String();

		int dm = (int) m;// decimal value of m
		int dv = (int) v;// decimal value of v

		if (m != 0.0) {

			if (m - dm == 0.0) {
				if (dm == 1) {
					value = "M";
				} else if (dm == -1) {
					value = "-M";
				} else {
					value = dm + "M";
				}

			} else {
				int[] frM = Fraction.GetFraction(m);
				value = frM[0] + "/" + frM[1] + "M";
			}
			// value = m + "M";

		}

		if (v != 0.0) {

			if (v - dv == 0.0) {
				if (m != 0.0 && v > 0.0) {// e.g number 3 to avoid 3.000
					value += "+" + dv;// for e.g. 3M+4
				} else {
					value += dv; // for e.g. 3M-4
				}
			} else {
				if (m != 0.0 && v > 0.0) { // m value not found and v value is
											// greater than 0 then + show
					int[] frV = Fraction.GetFraction(v);
					value += "+" + frV[0] + "/" + frV[1];
				} else {
					int[] frV = Fraction.GetFraction(v);
					value += frV[0] + "/" + frV[1];
				}
			}
		} else if (v == 0.0 && m == 0.0) {
			value += dv;
		}

		/*
		 * if(m == 0) value = (int)v + ""; if(m>0){ if(m == 1) value += "+M";
		 * else value += "+" + (int)m + "M"; }else if(m<0){ if(m == -1) value +=
		 * "-M"; else value += (int)m + "M"; }
		 */
		return value;
	}

	public Variable add(Variable var) {
		Variable result = new Variable(m, v);
		result.v += var.v;
		result.m += var.m;

		return result;
	}

	public Variable sub(Variable var) {
		return add(new Variable(-var.m, -var.v));
	}

	public Variable multiply(Variable var) {

		Variable result = new Variable(m, v);
		result.m = result.m * var.v + result.v * var.m;
		result.v = result.v * var.v;

		return result;
	}

	public Variable divide(Variable var) {
		Variable result = new Variable(m, v);
		/*
		 * System.out.println(result.m + " " + result.v);
		 * System.out.println(var.m + " " + var.v);
		 */
		if (var.m != 0) {// if m is not zero
			result.m = result.m / var.m;
		}

		result.v = result.v / var.v;

		return result;
	}

	@Override
	public int compareTo(Variable var2) {
		if (m > var2.m) {
			return 1;
		} else if (m < var2.m) {
			return -1;
		} else {
			if (v > var2.v) {
				return 1;
			} else if (v < var2.v) {
				return -1;
			}
		}
		return 0;

	}

}
