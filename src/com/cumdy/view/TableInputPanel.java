package com.cumdy.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.cumdy.calculate.BigMMethod;
import com.cumdy.calculate.ConstraintBuilder;
import com.cumdy.entity.Business;
import com.cumdy.entity.Objective;

public class TableInputPanel extends JPanel {
	JLabel lblBusiness = new JLabel("Number of Business");
	JLabel lblYear = new JLabel("Total Return Year");
	JLabel lblCapital = new JLabel("Capital");

	JTextField txtBusiness = new JTextField(10);
	JTextField txtYear = new JTextField(10);
	JTextField txtCapital = new JTextField(10);

	JButton btnClear = new JButton("Clear");
	JButton btnCalculate = new JButton("Calculate");
	CustomTable table = new CustomTable(0);
	JScrollPane jsp = new JScrollPane(
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private static TableInputPanel instance;

	public static TableInputPanel getInstance() {
		if (instance == null)
			instance = new TableInputPanel();

		return instance;
	}

	private TableInputPanel() {
		// super("Big-M Calculator, Maximizing Total Return on Investment ");
		setLayout(new BorderLayout(5, 5));

		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

		panel1.setBackground(Color.white);

		/*
		 * lblBusiness.setForeground(Color.black);
		 * lblYear.setForeground(Color.white);
		 * lblCapital.setForeground(Color.white);
		 */
		lblBusiness.setFont(new Font("Zawgyi-One", Font.PLAIN, 17));
		lblCapital.setFont(new Font("Zawgyi-One", Font.PLAIN, 17));
		lblYear.setFont(new Font("Zawgyi-One", Font.PLAIN, 17));
		txtBusiness.setFont(new Font("Zawgyi-One", Font.PLAIN, 17));
		txtCapital.setFont(new Font("Zawgyi-One", Font.PLAIN, 17));
		txtYear.setFont(new Font("Zawgyi-One", Font.PLAIN, 17));

		panel1.add(lblBusiness);
		panel1.add(txtBusiness);

		panel1.add(lblCapital);
		panel1.add(txtCapital);
		panel1.add(lblYear);
		panel1.add(txtYear);

		add(panel1, "North");
		JPanel center = new JPanel();
		center.setLayout(null);
		center.add(jsp);

		table.setBackground(Color.white);

		jsp.setBounds(750, 10, 600, 430);
		jsp.setViewportView(table);
		add(center, "Center");
		JPanel btnPanel = new JPanel();
		ImageIcon ico_calculate = new ImageIcon(
				TableInputPanel.class.getResource("ic_action_dock.png"));
		btnCalculate.setIcon(ico_calculate);
		btnCalculate.setPreferredSize(new Dimension(150, 40));
		btnCalculate.setBackground(Color.black);
		btnCalculate.setForeground(Color.white);

		ImageIcon ico_clear = new ImageIcon(
				TableInputPanel.class.getResource("ic_action_cancel.png"));
		btnClear.setIcon(ico_clear);
		btnClear.setPreferredSize(new Dimension(150, 40));
		btnClear.setBackground(Color.black);
		btnClear.setForeground(Color.white);

		btnPanel.add(btnCalculate);
		btnPanel.add(btnClear);
		btnPanel.setBackground(Color.blue);
		add(btnPanel, "South");
		btnCalculate.setMnemonic('C');
		btnClear.setMnemonic('l');

		setVisible(true);
		actionStart();
	}

	private void actionStart() {
		btnCalculate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Business[] bus = new Business[table.getRow()];
				double interest = 0;
				double amount = 0;
				float year = 0;
				for (int i = 0; i < table.getRow(); i++) {

					String name = (table.dm.getValueAt(i, 0) == null) ? new String()
							: table.dm.getValueAt(i, 0).toString();
					String initialInvestment = (table.dm.getValueAt(i, 1) == null) ? new String()
							: table.dm.getValueAt(i, 1).toString();
					String returnYear = (table.dm.getValueAt(i, 2) == null) ? new String()
							: table.dm.getValueAt(i, 2).toString();
					String interestRate = (table.dm.getValueAt(i, 3) == null) ? new String()
							: table.dm.getValueAt(i, 3).toString();

					try {
						if (interestRate.length() == 0) {
							JOptionPane.showMessageDialog(null,
									"Intrest is empty",
									"Please Enter Interest",
									JOptionPane.WARNING_MESSAGE);
							throw new Exception();

						} else if (returnYear.length() == 0) {
							JOptionPane.showMessageDialog(null,
									"Return Year is empty",
									"Please Enter Return year",
									JOptionPane.WARNING_MESSAGE);
							throw new Exception();
						} else if (name.length() == 0) {
							JOptionPane.showMessageDialog(null,
									"Business name is empty",
									"Please Enter Business Name",
									JOptionPane.WARNING_MESSAGE);
							throw new Exception();
						}

						interest = Double.parseDouble(interestRate);
						amount = Double.parseDouble(initialInvestment);
						year = Integer.parseInt(returnYear);

					} catch (NumberFormatException ne) {
						JOptionPane.showMessageDialog(null, "Enter Number Only"
								+ ne.getMessage(), "Please Enter Number",
								JOptionPane.WARNING_MESSAGE);
						return;
					} catch (Exception ee) {
						return;

					}

					bus[i] = new Business(name, amount, year, interest);
				}

				/*
				 * bus[0] = new Business("a", 100000, 1, 19); bus[1] = new
				 * Business("a", 0, 2, 16); bus[2] = new Business("a", 50000, 3,
				 * 20);
				 */
				try {
					int returnYear = Integer.parseInt(txtYear.getText());
					double capital = Double.parseDouble(txtCapital.getText());
					ConstraintBuilder constr = new ConstraintBuilder(bus,
							returnYear - 1, capital);

					BigMMethod bigM = new BigMMethod(constr
							.generateConstraint(), constr
							.generateSolutionVariable(), constr
							.generateObjective(), constr
							.generateConstraintType(), Objective.Maximize);
					new ResultFrame(bigM, bus, returnYear - 1, capital);
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(null, exc.getMessage());
				}

			}

		});

		txtBusiness.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				String str = txtBusiness.getText();
				if (str.length() != 0 && str.length() < 3) {
					try {
						int value = Integer.parseInt(str);
						table.setRow(value);
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage(),
								"Enter Number Only", JOptionPane.ERROR_MESSAGE);
						txtBusiness.setText("");
					}
				} else {
					table.setRow(0);
				}

			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		btnClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				txtCapital.setText("");
				txtYear.setText("");
				txtBusiness.setText("");
				table.setRow(0);
				// txtBusiness.setText("");
				/*
				 * for(int i=0;i<table.getRowCount();i++){ for(int
				 * j=0;j<table.getColumnCount();j++){ table.dm.setValueAt("", i,
				 * j);
				 * 
				 * } }
				 */

			}

		});

	}

}
