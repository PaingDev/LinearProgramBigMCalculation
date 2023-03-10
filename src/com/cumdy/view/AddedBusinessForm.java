package com.cumdy.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.cumdy.dbHelper.ConnectionHelper;
import com.cumdy.dbHelper.DataControl;
import com.cumdy.entity.Business;

public class AddedBusinessForm extends JFrame {
	DataControl dc;
	ConnectionHelper dbHelper;
	BusinessPanel panel;

	public AddedBusinessForm() {
		super("Add Business Form");
		Image icon = Toolkit.getDefaultToolkit().getImage(
				MainForm.class.getResource("icon.jpg"));
		setIconImage(icon);
		dbHelper = ConnectionHelper.getInstance();
		try {
			dc = new DataControl(dbHelper.getConnection());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		panel = new BusinessPanel();
		add(panel);
		setSize(400, 200);
		setResizable(false);
		setLocationByPlatform(true);
		setVisible(true);

		ImageIcon ico_save = new ImageIcon(
				TableInputPanel.class.getResource("ic_action_new.png"));
		panel.btnSave.setIcon(ico_save);
		panel.btnSave.setPreferredSize(new Dimension(150, 40));
		panel.btnSave.setBackground(Color.black);
		panel.btnSave.setForeground(Color.white);
		panel.btnSave.setMnemonic('S');

		ImageIcon ico_close = new ImageIcon(
				TableInputPanel.class.getResource("close.png"));
		panel.btnClose.setIcon(ico_close);
		panel.btnClose.setPreferredSize(new Dimension(150, 40));
		panel.btnClose.setBackground(Color.black);
		panel.btnClose.setForeground(Color.white);
		panel.btnClose.setMnemonic('C');

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panel.btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Business b = panel.getBusiness();
					dc.insertIntoTable(b.getName(),
							String.valueOf(b.getInitial()),
							String.valueOf(b.getRetRate()),
							String.valueOf(b.getInterest()));
				} catch (NumberFormatException exn) {
					JOptionPane.showMessageDialog(null, exn.getMessage());
					return;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ShowBusinessForm.getInstance().load();
				dispose();
			}
		});

		panel.btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();

			}
		});
	}

}
