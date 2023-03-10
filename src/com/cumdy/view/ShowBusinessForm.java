package com.cumdy.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cumdy.dbHelper.ConnectionHelper;
import com.cumdy.dbHelper.DataControl;
import com.cumdy.entity.Business;

public class ShowBusinessForm extends JPanel {
	BusinessPanel panel;
	// AnimationPanel preview = new AnimationPanel("List Of Business");
	JLabel preview = new JLabel("List of Business");
	JList listBusiness = new JList();
	JButton btnNewBusiness = new JButton("New Business");
	ConnectionHelper dbHelper;
	DataControl control;
	JScrollPane jsp = new JScrollPane();
	int borderX = 0;
	int borderY = 0;
	int inc = 10;
	private static ShowBusinessForm instance = null;

	private ShowBusinessForm() {
		// setType(Window.Type.POPUP);

		setSize(700, 430);
		setLocation(0, 0);
		Image icon = Toolkit.getDefaultToolkit().getImage(
				MainForm.class.getResource("icon.jpg"));
		// setIconImage(icon);
		// setLocationByPlatform(true);
		panel = new BusinessPanel();

		ImageIcon ico_save = new ImageIcon(
				TableInputPanel.class.getResource("ic_action_discard.png"));
		panel.btnSave.setIcon(ico_save);
		panel.btnSave.setPreferredSize(new Dimension(150, 40));
		panel.btnSave.setBackground(Color.black);
		panel.btnSave.setForeground(Color.white);
		panel.btnSave.setMnemonic('D');

		ImageIcon ico_add = new ImageIcon(
				TableInputPanel.class.getResource("ic_action_new.png"));
		panel.btnClose.setIcon(ico_add);
		panel.btnClose.setPreferredSize(new Dimension(180, 40));
		panel.btnClose.setBackground(Color.black);
		panel.btnClose.setForeground(Color.white);
		panel.btnClose.setMnemonic('A');

		setLayout(new BorderLayout(2, 2));
		JPanel panel1 = new JPanel();
		jsp.setViewportView(listBusiness);
		preview.setFont(new Font("Arial", Font.BOLD, 25));
		preview.setBackground(new Color(91, 155, 213));
		preview.setForeground(Color.black);
		preview.setHorizontalAlignment(SwingConstants.CENTER);
		preview.setBorder(BorderFactory.createLineBorder(Color.black));
		preview.setOpaque(true);
		// preview.setBorder(BorderFactory.createRaisedBevelBorder());

		// preview.setPreferredSize(new Dimension(1000,60));

		panel1.add(panel);
		add(panel1);
		add(preview, "North");
		add(btnNewBusiness, "South");
		panel.btnSave.setText("Delete");
		listBusiness.setFixedCellHeight(40);
		listBusiness.setFixedCellWidth(150);
		add(jsp, "West");
		btnNewBusiness.setMnemonic('N');
		panel.txtName.setEditable(false);
		panel.txtAmount.setEditable(false);
		panel.txtInterest.setEditable(false);
		panel.txtYear.setEditable(false);
		panel.btnClose.setText("Add Business To Table");
		listBusiness.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// listBusiness.setListData()
				panel.setVisible(true);
				Business business = (Business) listBusiness.getSelectedValue();
				if (business != null)
					panel.setBusiness(business);
			}
		});

		dbHelper = ConnectionHelper.getInstance();
		try {
			control = new DataControl(dbHelper.getConnection());
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		load();
		if (listBusiness.getModel().getSize() > 0)
			listBusiness.setSelectedIndex(0);
		else
			panel.setVisible(false);

		btnNewBusiness.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddedBusinessForm();

			}
		});
		panel.btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Business b = panel.getBusiness();

				System.out.println(b.toString());
				try {
					control.deleteBusiness(b);
					load();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				listBusiness.setSelectedIndex(0);
			}
		});

		panel.btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TableInputPanel tbPanel = TableInputPanel.getInstance();

				Vector<String> vector = new Vector<String>();
				Business bus = panel.getBusiness();
				System.out.println(bus.getName());
				vector.add(bus.getName());
				vector.add(String.valueOf(bus.getInitial()));
				vector.add(String.valueOf((int) bus.getRetRate()));
				vector.add(String.valueOf(bus.getInterest()));
				tbPanel.table.dm.addRow(vector);

			}
		});

		// setVisible(true);
		// setBorder(BorderFactory.createDashedBorder(,10, 5));
		Timer timer = new Timer(2, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// borderY++;
				if (borderX == getWidth() / 2) {
					inc = -1;
					// borderX = 0;
				} else if (borderX == 0) {
					inc = 1;
				}
				borderX += inc;
				if (inc == 1)
					setBorder(BorderFactory.createStrokeBorder(new BasicStroke(
							4.0f), new GradientPaint(getWidth() - borderX,
							getHeight() - borderX, Color.red, borderX, borderX,
							Color.green)));
				else
					setBorder(BorderFactory.createStrokeBorder(new BasicStroke(
							4.0f), new GradientPaint(getWidth() - borderX,
							getHeight() - borderX, Color.green, borderX,
							borderX, Color.red)));
			}
		});
		timer.start();
	}

	public void load() {
		try {
			listBusiness.setListData(control.getAllStudent().toArray());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static ShowBusinessForm getInstance() {
		if (instance == null)
			instance = new ShowBusinessForm();
		return instance;
	}

}
