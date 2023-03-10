package com.cumdy.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cumdy.entity.Business;

public class BusinessPanel extends JPanel {
	JLabel lblName = new JLabel("   Name");
	JLabel lblYear = new JLabel("   Year");
	JLabel lblInterest = new JLabel("   Interest");
	JLabel lblAmout = new JLabel("   Initial Amount");
	JTextField txtName = new JTextField(20);
	JTextField txtYear = new JTextField(20);
	JTextField txtInterest = new JTextField(20);
	JTextField txtAmount = new JTextField(20);
	JButton btnClose = new JButton("Close");
	JButton btnSave = new JButton("Save");

	public BusinessPanel() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		JPanel sPanel = new JPanel();
		panel.setLayout(new GridLayout(4, 2));
		panel.add(lblName);
		panel.add(txtName);

		panel.add(lblAmout);
		panel.add(txtAmount);

		panel.add(lblYear);
		panel.add(txtYear);

		panel.add(lblInterest);
		panel.add(txtInterest);
		sPanel.add(btnSave);
		sPanel.add(btnClose);

		add(panel);
		add(sPanel, "South");
		setSize(400, 200);

	}

	public void setBusiness(Business business) {
		txtName.setText(business.getName());
		txtAmount.setText(String.valueOf(business.getInitial()));
		txtInterest.setText(String.valueOf(business.getInterest()));
		txtYear.setText(String.valueOf(business.getRetRate()));

	}

	public Business getBusiness() {
		Business business = new Business(txtName.getText(),
				Double.parseDouble(txtAmount.getText()),
				Float.parseFloat(txtYear.getText()),
				Double.parseDouble(txtInterest.getText()));
		return business;

	}

}
