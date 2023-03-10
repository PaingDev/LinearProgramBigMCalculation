package com.cumdy.entity;

public class TableData {
	static int tableCount;
	Variable[] cj_zj;
	Variable[] cj;
	Variable[] zj;
	Variable[][] a;
	Variable z;
	String[] varName;
	String[] varInBasis;
	Variable[] minRatio;

	public TableData() {

		tableCount++;
	}

	public Variable getZ() {
		return z;
	}

	public void setZ(Variable z) {
		this.z = z;
	}

	public Variable[] getCj_zj() {
		return cj_zj;
	}

	public Variable[] getCj() {
		return cj;
	}

	public Variable[][] getA() {
		return a;
	}

	public void setCj_zj(Variable[] cj_zj) {
		this.cj_zj = cj_zj;
	}

	public void setCj(Variable[] cj) {
		this.cj = cj;
	}

	public void setZj(Variable[] zj) {
		this.zj = zj;
	}

	public Variable[] getZj() {
		return this.zj;
	}

	public void setA(Variable[][] a) {
		this.a = a;
	}

	public String[] getVarName() {
		return varName;
	}

	public String[] getVarInBasis() {
		return varInBasis;
	}

	public Variable[] getMinRatio() {
		return minRatio;
	}

	public void setVarName(String[] varName) {
		this.varName = varName;
	}

	public void setVarInBasis(String[] varInBasis) {
		this.varInBasis = varInBasis;
	}

	public void setMinRatio(Variable[] minRatio) {
		this.minRatio = minRatio;
	}

	public String getHtmlTable() {
		String str = "<table border=1 cellspacing=0><tr bgcolor=#5b9bd5 text=white><td colspan=3>CJ&gt&gt&gt</td>";
		for (int i = 0; i < cj.length; i++) {
			str += "<td>" + cj[i] + "</td>";
		}

		str += "</tr>";
		str += "<tr ><th>CB</th><th>B</th><th>b(=xB)</th>";
		for (int i = 0; i < varName.length; i++) {
			str += "<th>" + varName[i] + "</th>";
		}
		str += "<th> Min Ratio </th></tr>";

		for (int i = 0; i < a.length - 1; i++) {
			str += "<tr>";
			for (int j = a[i].length - 2; j < a[i].length; j++) {
				if (j == a[i].length - 1) {
					str += "<td>" + varInBasis[i] + "</td>";
				}
				str += "<td>" + a[i][j] + "</td>";
			}
			for (int j = 0; j < a[i].length - 2; j++) {

				str += "<td>" + a[i][j] + "</td>";
			}
			if (i < minRatio.length) {
				String min = (minRatio[i].define == true) ? minRatio[i]
						.toString() : "undefine";
				str += "<td>" + min + "</td>";
			}
			str += "</tr>";

		}

		str += "<tr>";
		str += "<td color=green colspan = 2> Z = " + z + "</td>";
		str += "<td  aling='right'>zj</td>";
		for (int i = 0; i < zj.length; i++) {
			str += "<td>" + zj[i] + "</td>";
		}
		str += "<td></td></tr>";

		str += "<tr color=green><td colspan=3 align='right'>cj-zj</td>";
		for (int i = 0; i < cj_zj.length; i++) {
			str += "<td>" + cj_zj[i] + "</td>";
		}
		str += "<td></td></tr></table>";
		return str;
	}

	@Override
	public String toString() {
		String str = "<html><table border=1 cellspacing=0><tr bgcolor=#5b9bd5 text=white><td colspan=3>CJ&gt&gt&gt</td>";
		for (int i = 0; i < cj.length; i++) {
			str += "<td>" + cj[i] + "</td>";
		}

		str += "</tr>";
		str += "<tr ><th>CB</th><th>B</th><th>b(=xB)</th>";
		for (int i = 0; i < varName.length; i++) {
			str += "<th>" + varName[i] + "</th>";
		}
		str += "<th> Min Ratio </th></tr>";

		for (int i = 0; i < a.length - 1; i++) {
			str += "<tr>";
			for (int j = a[i].length - 2; j < a[i].length; j++) {
				if (j == a[i].length - 1) {
					str += "<td>" + varInBasis[i] + "</td>";
				}
				str += "<td>" + a[i][j] + "</td>";
			}
			for (int j = 0; j < a[i].length - 2; j++) {

				str += "<td>" + a[i][j] + "</td>";
			}
			if (i < minRatio.length) {
				String min = (minRatio[i].define == true) ? minRatio[i]
						.toString() : "undefine";
				str += "<td>" + min + "</td>";
			}
			str += "</tr>";

		}

		str += "<tr>";
		str += "<td color=green colspan = 2> Z = " + z + "</td>";
		str += "<td  aling='right'>zj</td>";
		for (int i = 0; i < zj.length; i++) {
			str += "<td>" + zj[i] + "</td>";
		}
		str += "<td></td></tr>";

		str += "<tr color=green><td colspan=3 align='right'>cj-zj</td>";
		for (int i = 0; i < cj_zj.length; i++) {
			str += "<td>" + cj_zj[i] + "</td>";
		}
		str += "<td></td></tr>";
		str += "</table></html>";

		return str;
	}

}
