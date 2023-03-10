package com.cumdy.calculate;

import java.util.ArrayList;
import java.util.List;
import com.cumdy.entity.Objective;
import com.cumdy.entity.TableData;
import com.cumdy.entity.Variable;

/**
 * 
 * @author Paing Pyae Sone solution variable index = V+1 or last column
 *         coefficient of basis variable = V cj array . Range is = [M][1 to V] V
 *         is control of size of variable when table change
 */
public class BigMMethod {

	List<TableData> arrListTable = new ArrayList<TableData>();

	private String[] varName;
	private Objective objective;
	private Variable z;
	private Variable[][] a; // tableaux
	private int M; // number of constraints
	private int N; // number of original variables
	private int S; // number of slack variables
	private int A; // number of artificial variables
	String[] tableColName;
	private int V; // number of all variable
	private int[] indexCB;

	Variable[] cjzj;
	Variable[] zj;
	Variable[] minRatio;

	/**
	 * 
	 * @param A
	 *            is constraint
	 * @param b
	 *            is solution variable
	 * @param c
	 *            is objective function
	 * @param constraintType
	 * @param obj
	 *            is max or min
	 */
	public BigMMethod(double[][] A, double[] b, double[] c,
			int[] constraintType, Objective obj) {// A is constraint,b is
													// solution variable,c is
													// objective function
		objective = obj;
		S = 0;
		this.A = 0;
		N = c.length;
		M = b.length;

		for (int i = 0; i < constraintType.length; i++) {
			prepareConstraintSize(constraintType[i]);
		}
		V = S + N + this.A;
		convertStandartFormConstraint(A, constraintType);
		initializeVarName(N);
		initializeTableColName();
		initializeCj(c); // cj

		initializeB(b); // b is solution variable
		initializeCb(); // Coefficient of basis variable , initialize index[]
						// for cb

		int l = 0;

		boolean solve;
		do {

			solve = solve();

			// showFrame();
			l++;
			System.out.println("NormallyEnd");
		} while (solve == true && !check() && l < 30);

	}

	private void initializeTableColName() {
		tableColName = new String[varName.length];
		for (int i = 0; i < varName.length; i++) {
			tableColName[i] = varName[i];
		}

	}

	private void calculateZ() {
		z = new Variable(0.0, 0.0);
		System.out.println("Calculate Z");
		for (int i = 0; i < a.length - 1; i++) {
			System.out.println("CB : " + a[i][V] + " \t XB" + a[i][V + 1]);
			z = z.add(a[i][V].multiply(a[i][V + 1]));
		}
		System.out.println("Max Value " + z);
	}

	private void prepareConstraintSize(int compOperator) {
		if (compOperator == 0) {
			S++;

		} else if (compOperator == 1) {
			A++;
		} else {
			S++;
			A++;

		}

	}

	private void showTable() {
		TableData data = new TableData();
		Variable[] cj = new Variable[a[0].length - 2];

		for (int j = 0; j < a[0].length - 2; j++) {
			cj[j] = a[a.length - 1][j];
		}

		data.setCj(cj);
		data.setVarName(tableColName);

		Variable[][] storeA = new Variable[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] != null)
					storeA[i][j] = new Variable(a[i][j].m, a[i][j].v);
				else
					storeA[i][j] = new Variable(0.0, 0.0);
			}
		}
		int[] storeIndexCB = new int[indexCB.length];
		for (int i = 0; i < storeIndexCB.length; i++) {
			storeIndexCB[i] = indexCB[i];
		}
		String[] varInBasis = new String[storeIndexCB.length];
		for (int i = 0; i < storeIndexCB.length; i++) {
			varInBasis[i] = (M > i) ? varName[storeIndexCB[i]] : "";
		}
		data.setVarInBasis(varInBasis);

		data.setA(storeA);
		data.setMinRatio(minRatio);

		data.setZj(zj);
		data.setCj_zj(cjzj);
		data.setZ(z);

		arrListTable.add(data);

	}

	private void convertStandartFormConstraint(double[][] constraint,
			int[] constraintType) {
		a = new Variable[constraint.length + 1][constraint[0].length + S + A
				+ 2];
		int sIndex = 0;
		int aIndex = S;
		for (int i = 0; i < M; i++) {

			for (int j = 0; j <= N; j++) {

				if (j == N) {

					for (int k = 0; k < S + A; k++) {

						if (constraintType[i] == 0 && sIndex == k) {

							a[i][j + k] = new Variable(1.0); // for +s
							sIndex++;
							constraintType[i] = -1;

						} else if (constraintType[i] == 1 && aIndex == k) {

							a[i][j + k] = new Variable(1.0); // for +a
							aIndex++;
							constraintType[i] = -1;

						} else if (constraintType[i] == 2 && sIndex == k) {

							a[i][j + k] = new Variable(-1.0); // for -s
							sIndex++;
							constraintType[i] = 1;

						} else {

							a[i][j + k] = new Variable(0.0);

						}

					} // k loop end

				} else {

					a[i][j] = new Variable(constraint[i][j]);

				}
			} // j loop end

		} // i loop end

	}

	private void initializeVarName(int n) {
		varName = new String[V];
		int i = 0;

		for (; i < n; i++) {

			varName[i] = new String("x<sub>" + (i + 1) + "</sub>");

		}
		for (int j = 0; j < S; j++, i++) {

			varName[i] = new String("s<sub>" + (j + 1) + "</sub>");
		}
		for (int j = 0; j < A; j++, i++) {

			varName[i] = new String("A<sub>" + (j + 1) + "</sub>");
		}

	}

	private void generateZj() {
		System.out.println("Generate ZJ");
		zj = new Variable[V];
		for (int i = 0; i < V; i++) {
			zj[i] = new Variable(0.0, 0.0);

		}

		for (int i = 0; i < M; i++) {
			for (int j = 0; j < V; j++) {

				zj[j] = a[i][V].multiply(a[i][j]).add(zj[j]);

			}

		}
	}

	private boolean solve() {

		generateZj();
		generateCjZj(zj);
		calculateZ();
		int col = selectColumn();

		System.out.println();
		System.out.println("Select Column" + col);

		int row = pivot(col);

		showTable();

		while (row == -1) {

			col = maxIndexSame(cjzj, col);

			if (col != -1) {
				row = pivot(col);
			} else {
				return false;
			}

		}
		if (indexCB[row] >= N + S) { // remove column of A and change CB

			A--;
			V--;

			Variable[] tempZj = new Variable[V];
			Variable[] tempCjZj = new Variable[V];
			String[] tempColName = new String[V];
			Variable[][] temp = new Variable[M + 1][V + 2];

			System.out.println("Before");
			for (Variable[] arr : a) {
				System.out.println();
				for (Variable p : arr) {
					System.out.print(p + "\t");
				}
			}
			for (int i = 0; i < a.length; i++) {
				// System.out.println();
				for (int j = 0, tempj = 0; j < a[i].length; j++) {
					if (indexCB[row] != j) {
						if (j < tableColName.length) {
							tempColName[tempj] = tableColName[j];
							tempZj[tempj] = zj[j];
							tempCjZj[tempj] = cjzj[j];
						}

						temp[i][tempj] = a[i][j];
						tempj++;
					} else {
						System.out.println("Egnore Index of row :value:"
								+ a[i][j]);
					}
				}
			}
			System.out.println("After");
			a = temp;
			tableColName = tempColName;
			zj = tempZj;
			cjzj = tempCjZj;
			System.out.println();
			for (Variable[] arr : a) {
				System.out.println();
				for (Variable p : arr) {
					System.out.print(p + "\t");
				}
			}
			for (int i = 0; i < indexCB.length; i++) {
				if (indexCB[row] < indexCB[i]) {
					indexCB[i] = indexCB[i] - 1;
				}
			}
			indexCB[row] = col; // change CB index
			a[row][V] = a[M][col]; // change CB

		} else { // change CB

			a[row][V] = a[M][col];
			indexCB[row] = col;

		}

		eliminate(row, col);

		return true;

	}

	public List<TableData> getAllTableData() {
		return arrListTable;
	}

	private boolean check() {

		for (Variable v : cjzj) {

			if (objective == Objective.Minimize) {
				if (v.compareTo(new Variable(0.0, 0.0)) == -1) {// less than 0

					return false;
				}
			} else {
				if (v.compareTo(new Variable(0.0, 0.0)) == 1) {// greater than 0

					return false;
				}
			}
		}

		return true;
	}

	private void eliminate(int row, int col) {
		System.out.println("Elimination --------------------- ROW" + row + ":"
				+ "COL" + col);

		int[] rowMultiplier = Fraction.GetFraction(a[row][col].v); // M value is
																	// not to
																	// think
		int temp = rowMultiplier[0];
		rowMultiplier[0] = rowMultiplier[1];
		rowMultiplier[1] = temp;

		if (rowMultiplier[1] != 0) {
			for (int i = 0; i < V + 2; i++) {
				if (i != V) {
					int[] resFra;
					if (a[row][i].v != 0) {
						resFra = Fraction.multiply(
								Fraction.GetFraction(a[row][i].v),
								rowMultiplier);
					} else {
						resFra = new int[2];
						resFra[0] = 0;
						resFra[1] = 1;
					}
					a[row][i].v = (double) resFra[0] / (double) resFra[1];
				}

			}

			for (int i = 0; i < M; i++) {

				rowMultiplier = Fraction.GetFraction(a[i][col].v);

				for (int j = 0; j < V + 2; j++) { // V+2 control is lead to
													// calculate
													// solutionVariable b(=xB)
					if (row != i && j != V) { // j!=V is to skip CB & pivote row

						int[] resFra = Fraction.sub(Fraction
								.GetFraction(a[i][j].v), Fraction.multiply(
								Fraction.GetFraction(a[row][j].v),
								rowMultiplier));

						if (resFra[1] != 0)
							a[i][j].v = (double) resFra[0] / (double) resFra[1];
						else
							a[i][j].v = 0.0;

					}

				}

			}
		}// end if
	}

	private int selectColumn() {

		if (objective == Objective.Maximize) {
			return maxIndex(cjzj);
		} else {
			return minIndex(cjzj);
		}

	}

	private void generateCjZj(Variable[] zj) {
		cjzj = new Variable[zj.length];
		for (int i = 0; i < zj.length; i++) {
			cjzj[i] = a[M][i].sub(zj[i]);

		}

	}

	private int maxIndex(Variable[] arr) {
		Variable max = arr[0];
		int maxIndex = 0;
		for (int i = 1; i < arr.length; i++) {

			if (max.m < arr[i].m) {

				maxIndex = i;
				max = arr[maxIndex];
			} else if (max.m == arr[i].m && max.v < arr[i].v) {
				maxIndex = i;
				max = arr[maxIndex];

			}

		}
		return maxIndex;
	}

	private int maxIndexSame(Variable[] arr, int startIndex) {

		int maxIndex = startIndex;
		for (int i = startIndex + 1; i < arr.length; i++) {

			if (arr[maxIndex].m == arr[i].m && arr[maxIndex].v == arr[i].v) {
				maxIndex = i;
				break;
			}
		}
		if (maxIndex == startIndex) {
			return -1;
		}
		return maxIndex;
	}

	private int minIndexForMinRatio(Variable[] arr) { // mvalue is neglect for
														// minratio

		int minIndex = -1;

		for (int i = 0; i < arr.length; i++) {
			if (arr[i].define == true) {
				if (arr[i].v <= 0)
					continue;
				else if (minIndex == -1)
					minIndex = i;
				else if ((arr[i].v < arr[minIndex].v)) {
					minIndex = i;
				}
			}
		}
		/*
		 * if(minIndex == -1){ for(int i=0;i<arr.length;i++){ if(arr[i].define
		 * == true ){ if(arr[i].v<0)continue; else if(minIndex == -1) minIndex =
		 * i; else if((arr[i].v<arr[minIndex].v)){ minIndex = i; } } } }
		 */

		return minIndex;
	}

	// this method is used in objective function minimize
	private int minIndex(Variable[] arr) {
		Variable min = arr[0];
		int minIndex = 0;
		for (int i = 1; i < arr.length; i++) {

			if (min.m > arr[i].m) {

				minIndex = i;
				min = arr[minIndex];
			} else if (min.m == arr[i].m && min.v > arr[i].v) {

				minIndex = i;
				min = arr[minIndex];

			}

		}
		return minIndex;
	}

	public int[] getIndexCB() {
		return indexCB;
	}

	private int pivot(int col) {
		System.out.println("PIVOTE");
		minRatio = new Variable[M];

		for (int i = 0; i < M; i++) {// prepare min ratio

			if (a[i][col].v != 0)
				minRatio[i] = new Variable(a[i][V + 1].v / (a[i][col].v));// solution
																			// variable/select
																			// column
			else {
				minRatio[i] = new Variable(0, 0);
				minRatio[i].define = false;
			}
			System.out.println("MINRATIO Item : " + minRatio[i]);
		}

		return minIndexForMinRatio(minRatio);
	}

	private void initializeB(double[] b) {

		for (int i = 0; i < b.length; i++) {
			a[i][V + 1] = new Variable(b[i]);
		}

	}

	private void initializeCj(double[] c) {

		for (int j = 0; j < N; j++)
			a[M][j] = new Variable(c[j]);// cj for original variable
		for (int j = N; j < N + S; j++)
			a[M][j] = new Variable(0.0); // cj for s in objective function

		if (objective == Objective.Maximize) {
			for (int j = N + S; j < V; j++)
				a[M][j] = new Variable(-1, 0.0);// - for maximize
		} else {
			for (int j = N + S; j < V; j++)
				a[M][j] = new Variable(1, 0.0);// - for minimize
		}

	}

	private void initializeCb() {
		indexCB = new int[M];
		for (int i = 0; i < M; i++) { // loop with number of constraint
			for (int j = V - 1; j >= 0; j--) {// cb = specifed cj
				if (a[i][j].v != 0.0) {
					a[i][V] = a[M][j];
					indexCB[i] = j;
					break;
				}
			}
		}
	}
}
