package com.cumdy.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.cumdy.calculate.BigMMethod;
import com.cumdy.entity.Business;

public class EquationForm extends JPanel {

	JLabel txtObjective = new JLabel("Objective Function Maximize");
	ButtonGroup rdbGroup = new ButtonGroup();
	JButton btnEq = new JButton("Enter equation");
	JButton btnCalculate = new JButton("Calculate");
	JTextField[] txtVariable;
	JLabel[] lblVariable;
	MatrixInputPanel matrixPanel;
	JScrollPane jsp = new JScrollPane();

	public EquationForm() {
		setLayout(new BorderLayout(30, 30));
		ImageIcon ico_new = new ImageIcon(
				TableInputPanel.class.getResource("ic_action_new.png"));
		btnEq.setIcon(ico_new);
		btnEq.setPreferredSize(new Dimension(150, 40));
		btnEq.setBackground(Color.black);
		btnEq.setForeground(Color.white);
		ImageIcon ico_calculate = new ImageIcon(
				TableInputPanel.class.getResource("ic_action_dock.png"));
		btnCalculate.setIcon(ico_calculate);
		btnCalculate.setPreferredSize(new Dimension(150, 40));
		btnCalculate.setBackground(Color.black);
		btnCalculate.setForeground(Color.white);

		add(btnEq, "North");
		btnEq.setMnemonic('E');
		btnCalculate.setMnemonic('C');

		setBackground(new Color(193, 219, 254));
		setForeground(Color.white);
		setSize(500, 500);
		actionStart();
	}

	private void actionStart() {
		btnCalculate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double[][] constraints = matrixPanel.getConstraints();
				double[] objective = matrixPanel.getObjective();
				for (double value : objective) {
					System.out.println("Objectvie " + value);
				}
				double[] cb = matrixPanel.getSolutionValue();
				for (double value : cb) {
					System.out.println("Solution Value" + value);
				}
				for (int index : matrixPanel.getConstraintsType()) {
					System.out.println("Constr" + index);
				}
				matrixPanel.getObjectiveFunction();
				Business[] bus = new Business[txtVariable.length];
				for (int i = 0; i < txtVariable.length; i++) {
					bus[i] = new Business(
							(txtVariable[i].getText().length() == 0) ? "x"
									+ (i + 1) : txtVariable[i].getText());
				}

				BigMMethod bigM = new BigMMethod(constraints, cb, objective,
						matrixPanel.getConstraintsType(), matrixPanel
								.getObjectiveFunction());
				new ResultFrame(bigM, bus, 0, 0);

			}

		});

		btnEq.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (matrixPanel != null) {
					remove(jsp);
					validate();
				}
				int nCount = 0;
				int eqCount = 0;
				nCount = inputDialog("Enter numbers of Original Variable");
				eqCount = inputDialog("Enter numbers of Equation");

				matrixPanel = new MatrixInputPanel(nCount, eqCount);
				txtVariable = new JTextField[nCount];
				lblVariable = new JLabel[nCount];
				JPanel gridPanel = new JPanel();
				JPanel panel = new JPanel();
				JScrollPane gridScroll = new JScrollPane();

				gridScroll.setViewportView(gridPanel);
				gridPanel.setBackground(Color.black);
				gridPanel.setForeground(Color.white);
				panel.setBackground(Color.blue);
				panel.setLayout(new BorderLayout());
				JLabel lblTitle = new JLabel("Assign Variable Name");
				JButton btnClear = new JButton("Clear All", new ImageIcon(
						MainForm.class.getResource("ic_action_cancel.png")));
				btnClear.setBackground(Color.black);
				btnClear.setForeground(Color.white);
				btnClear.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						for (JTextField txtField : txtVariable) {
							txtField.setText("");
							matrixPanel.clearText();
						}

					}
				});

				lblTitle.setFont(new Font("Arial", Font.PLAIN, 20));
				lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblTitle, "North");
				panel.add(btnClear, "South");
				JPanel insidePanel = new JPanel();
				insidePanel.add(gridScroll);
				panel.add(insidePanel);

				panel.setBorder(BorderFactory.createLineBorder(Color.black));
				gridPanel.setLayout(new GridLayout(nCount, 2, 5, 5));
				for (int i = 0; i < lblVariable.length; i++) {
					txtVariable[i] = new JTextField(10);
					lblVariable[i] = new JLabel("x" + (i + 1));
					lblVariable[i].setForeground(Color.white);
					gridPanel.add(lblVariable[i]);
					gridPanel.add(txtVariable[i]);
				}

				add(panel, "West");

				jsp.setViewportView(matrixPanel);
				add(jsp);
				add(btnCalculate, "South");

				jsp.validate();
				validate();
				gridPanel.validate();

			}
		});

	}

	private int inputDialog(String dialogName) {

		String value = JOptionPane.showInputDialog(dialogName);
		int intValue = 0;
		if (value != null && value.trim().length() != 0) {
			try {
				intValue = Integer.parseInt(value);
			} catch (NumberFormatException ne) {
				JOptionPane.showMessageDialog(null, "Enter Number Only");
				return inputDialog(dialogName);
			}

			return intValue;

		} else {
			return inputDialog(dialogName);
		}

	}

}
