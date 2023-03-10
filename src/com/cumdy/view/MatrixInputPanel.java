package com.cumdy.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cumdy.entity.Objective;

public class MatrixInputPanel extends JPanel {
	JTextField[][] txtConstraint;
	JLabel[][] arrLbl;
	JTextField[] txtObjective;
	JTextField[] txtSolutionValue;
	JComboBox<String>[] comboOperator;
	JComboBox<String> comboObjective;

	String[] arrVarName;
	int nCount;
	int sCount;
	int aCount;
	int eqCount;
	int totalCount;

	public MatrixInputPanel(int nCount, int eqCount) {
		super();

		arrVarName = new String[] { "x", "S", "A" };

		this.nCount = nCount;
		this.eqCount = eqCount;
		this.totalCount = nCount;

		init();

	}

	private void init() {

		JPanel centerPanel = new JPanel();
		JPanel objectivePanel = new JPanel();
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		add(centerPanel);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(objectivePanel, "North");
		centerPanel.add(leftPanel, "West");
		centerPanel.add(rightPanel, "East");
		GridLayout gLayout = new GridLayout(eqCount, nCount * 2 + 1);
		rightPanel.setLayout(gLayout);
		comboOperator = new JComboBox[eqCount];
		comboObjective = new JComboBox<String>(new String[] { "Minimize",
				"Maximize" });
		txtSolutionValue = new CustomJTextField[eqCount];
		txtObjective = new CustomJTextField[nCount];
		objectivePanel.add(new JLabel("Objective "));
		objectivePanel.add(comboObjective);
		for (int i = 0; i < nCount; i++) {

			objectivePanel.add(txtObjective[i] = new CustomJTextField(2));
			objectivePanel.add(new JLabel("<html>" + "x" + "<sub>" + (i + 1)
					+ "</sub></html>"));

		}
		txtConstraint = new JTextField[eqCount][nCount];
		arrLbl = new JLabel[eqCount][nCount];
		leftPanel.add(new JLabel("Constraints"));
		for (int i = 0; i < eqCount; i++) {

			for (int j = 0; j < nCount + 1; j++) {
				if (j != nCount) {
					rightPanel
							.add(txtConstraint[i][j] = new CustomJTextField(2));

					rightPanel.add(arrLbl[i][j] = new JLabel("<html>" + "x"
							+ "<sub>" + (j + 1) + "</sub></html>"));
				} else {
					rightPanel.add(comboOperator[i] = new JComboBox<String>(
							new String[] { "<=", "=", ">=" }));
					comboOperator[i].setPreferredSize(new Dimension(50, 25));
					rightPanel
							.add(txtSolutionValue[i] = new CustomJTextField(2));
				}
			}

		}

		for (int i = 0; i < txtConstraint.length; i++) {
			for (int j = 0; j < txtConstraint[i].length; j++) {
				if (i != txtConstraint.length - 1
						|| j != txtConstraint[i].length - 1)
					if (j != txtConstraint[i].length - 1)
						txtConstraint[i][j]
								.addActionListener(new TextNextFocusAction(i, j));
					else
						txtConstraint[i][j]
								.addActionListener(new TextNextFocusAction(
										i + 1, -1));

			}
		}

		setBorder(BorderFactory.createLineBorder(Color.black));

	}

	class TextNextFocusAction implements ActionListener {
		int i;
		int j;

		public TextNextFocusAction(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			txtConstraint[i][j + 1].getCaret().setVisible(true);
			txtConstraint[i][j + 1].requestFocus();

		}

	}

	public double[][] getConstraints() {
		double[][] value = new double[txtConstraint.length][txtConstraint[0].length];
		for (int i = 0; i < value.length; i++) {
			for (int j = 0; j < value[i].length; j++) {
				System.out.println(i + "," + j);
				String str = txtConstraint[i][j].getText();
				System.out.println("Value = " + str);
				if (str.length() != 0)
					value[i][j] = Double.parseDouble(str);
			}
		}
		return value;
	}

	public double[] getObjective() {
		double[] value = new double[txtObjective.length];
		for (int i = 0; i < value.length; i++) {
			String str = txtObjective[i].getText();
			value[i] = Double.parseDouble(str);
		}
		return value;
	}

	public Objective getObjectiveFunction() {
		Objective obj;
		if (comboObjective.getSelectedIndex() == 0) {
			obj = Objective.Minimize;
		} else {

			obj = Objective.Maximize;
		}
		return obj;
	}

	public double[] getSolutionValue() {
		double[] value = new double[txtSolutionValue.length];
		for (int i = 0; i < value.length; i++) {
			String str = txtSolutionValue[i].getText();
			value[i] = Double.parseDouble(str);
		}

		return value;
	}

	public int[] getConstraintsType() {
		int[] operator = new int[comboOperator.length];
		for (int i = 0; i < operator.length; i++) {
			operator[i] = comboOperator[i].getSelectedIndex();
		}
		return operator;
	}

	public void clearText() {
		for (JTextField[] txtFields : txtConstraint) {
			for (JTextField txtField : txtFields) {
				txtField.setText("");
			}
		}

		for (JTextField txtField : txtObjective) {
			txtField.setText("");
		}

		for (JTextField txtField : txtSolutionValue) {
			txtField.setText("");
		}

	}

}
