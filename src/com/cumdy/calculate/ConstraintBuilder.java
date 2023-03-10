package com.cumdy.calculate;

import com.cumdy.entity.Business;
import com.cumdy.entity.Operator;

public class ConstraintBuilder {

	double[][] rawData;
	Business[] arrBusiness;
	int nCount;// number of all variable count;
	int year;
	double capital;

	public ConstraintBuilder(Business[] arrBusiness, int year, double capital) {
		this.arrBusiness = arrBusiness;
		this.year = year;
		this.capital = capital;
		nCount = (year * (arrBusiness.length));// +1 for year ending cash in
												// hand

		/*
		 * for(double[] values:generateConstraint()){ for(double value:values)
		 * System.out.print("\t" + value ); System.out.println(); }
		 */

		for (double value : generateObjective()) {
			System.out.println("\t " + value);
		}

	}

	public double[] generateSolutionVariable() {
		int businessCount = 0;
		for (Business bus : arrBusiness) {
			if (bus.getInitial() != 0) {
				businessCount++;
			}
		}

		double[] sVariable = new double[businessCount * year + year];
		for (int i = 0; i < sVariable.length; i++) {
			if (i == 0) {
				sVariable[i] = capital;
			} else if (i < year) {
				sVariable[i] = 0.0;
			} else {
				int indexBusiness = (i - year) / year;

				while (arrBusiness[indexBusiness].getInitial() == 0.0) {
					indexBusiness++;
				}
				sVariable[i] = arrBusiness[indexBusiness].getInitial();
			}
		}
		return sVariable;
	}

	public int[] generateConstraintType() {

		int businessCount = 0;
		for (Business bus : arrBusiness) {
			if (bus.getInitial() != 0) {
				businessCount++;
			}
		}

		int[] constType = new int[businessCount * year + year];
		for (int i = 0; i < constType.length; i++) {
			if (i == 0) {
				constType[i] = Operator.Equal;
			} else {
				constType[i] = Operator.LessThanEqual;
			}
		}
		return constType;
	}

	public double[][] generateConstraint() {
		int businessCount = 0;
		for (Business bus : arrBusiness) {
			if (bus.getInitial() != 0) {
				businessCount++;
			}
		}
		System.out.println("Business Count" + businessCount);
		double[][] constraint = new double[businessCount * year + year][nCount];

		for (int i = 0; i < year; i++) {
			int xIndex = i; // x12,x23,x33 next index need plus year
			for (int j = 0; j < nCount; j++) {
				if (i == 0) {// for year 1
					if (j % year == 0)
						constraint[i][j] = 1.0;
					else
						constraint[i][j] = 0.0;

				} else {// for other year

					if (j == xIndex) {
						constraint[i][j] = 1.0;
						xIndex += year;
					} else if (j % year < i) {// last year interest e.g x11 rate
												// is 1 year

						int indexBusiness = (j / year);// -1 is for start index
														// is 0
						System.out.println("index " + indexBusiness);
						if (indexBusiness < arrBusiness.length) {
							if (arrBusiness[indexBusiness].getRetRate()
									+ (j % year) == i) {
								constraint[i][j] = -((arrBusiness[indexBusiness]
										.getInterest() / (double) 100) + 1);

							}
						}

					}

				}

			}

		}

		// limited amount of constraint
		int row = year;

		for (int i = 0; i < arrBusiness.length; i++) {
			int col = 0;
			if (arrBusiness[i].getInitial() != 0) {
				for (int j = 0; j < year; j++) { // eg . year is 6 but
													// constraint
													// x11,x12,x12,x14,x15
													// variable is 5
					for (int k = 0; k < nCount; k++) {
						if (k == (i * year) + col) {
							constraint[row][k] = 1;
							col++;
							break;
						}

					}
					row++;
				}

			}
		}

		for (int i = 0; i < constraint.length; i++) {
			for (int j = 0; j < constraint[i].length; j++) {
				System.out.print("\t" + constraint[i][j] + "\t");
			}
			System.out.println();
		}
		/*
		 * double[][] cashInHand = new double[year][year]; for(int
		 * i=0;i<cashInHand.length;i++){ for(int
		 * j=0;j<cashInHand[i].length;j++){
		 * 
		 * if(i==j){ cashInHand[i][j] =1; if(j-1 >=0) cashInHand[i][j-1]=-1; } }
		 * } double[][] comp_constraint = new
		 * double[constraint.length][constraint[0].length+cashInHand[0].length];
		 * for(int i=0;i<comp_constraint.length;i++){ for(int
		 * j=0;j<comp_constraint[i].length;j++){ if(j>=constraint[i].length){
		 * comp_constraint[i][j] = cashInHand[i][j-constraint[i].length]; }else{
		 * comp_constraint[i][j] = constraint[i][j]; } } } for(int
		 * i=0;i<cashInHand.length;i++){ for(int
		 * j=0;j<cashInHand[i].length;j++){ System.out.print("\t" +
		 * cashInHand[i][j]); } System.out.println(); }
		 */
		return constraint;

	}

	public double[] generateObjective() {
		double[] objective = new double[nCount];
		for (int i = 0; i < nCount; i++) {
			objective[i] = 0.0;
		}
		/*
		 * x11 x12 x13 x21 x22 x23 x31 x32 x33 choose on last return year
		 */

		for (int i = 0; i < arrBusiness.length; i++) {
			for (int j = 0; j < year; j++) {
				if (arrBusiness[i].getRetRate() + j <= year) {
					System.out.println(j + (i * year));
					objective[j + (i * year)] = arrBusiness[i].getInterest()
							/ (double) 100 + 1;
				}
			}
		}

		return objective;
	}

}
