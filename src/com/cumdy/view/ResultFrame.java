package com.cumdy.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import com.cumdy.calculate.BigMMethod;
import com.cumdy.entity.Business;
import com.cumdy.entity.TableData;
import com.cumdy.file.ExportFile;

public class ResultFrame extends JFrame {

	JLabel[] listLabel;
	BigMMethod bigM;
	List<TableData> listTable;
	Business[] business;
	int year;
	JToolBar tool = new JToolBar();
	JButton btnExport = new JButton("Export HTML File");
	AnimationPanel preview = new AnimationPanel(
			"Calculation for Investment by Using Big-M Method.");
	JPanel panel = new JPanel();
	JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

	public ResultFrame(BigMMethod bigM, Business[] business, int year,
			final double capital) {
		super(
				"Maximizing The Total Return On Investment Problem Using Big-M Method");
		Image icon = Toolkit.getDefaultToolkit().getImage(
				MainForm.class.getResource("icon.jpg"));
		setIconImage(icon);
		this.bigM = bigM;
		this.business = business;
		this.year = year;

		setResizable(false);
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(null);
		tool.setEnabled(false);
		tool.setBounds(0, 0, getWidth(), 60);
		preview.setBounds(0, 60, getWidth(), 80);
		panel.setBounds(0, 0, 540, getHeight() - 10);

		JScrollPane scrollPaneRes = new JScrollPane();
		scrollPaneRes.setViewportView(panel);
		scrollPaneRes.setBounds(5, 140, 545, getHeight() - 130);

		Container c = getContentPane();
		c.setBackground(new Color(193, 219, 254));

		tool.addSeparator();
		tool.add(btnExport);
		tool.addSeparator();

		JPanel tablePanel = new JPanel();
		JPanel detailPanel = new JPanel();
		detailPanel.setBounds(555, 130, getWidth() - 560, getHeight() - 140);
		detailPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		scrollPane.setPreferredSize(new Dimension(getWidth() - 570,
				getHeight() -250));
		JLabel lblDetail = new JLabel(
				"<html><h1>Big-M Method Calculation Steps....</h1></html>");
		detailPanel.add(lblDetail);
		detailPanel.add(scrollPane);
		scrollPane.setViewportView(tablePanel);
		listTable = bigM.getAllTableData();
		tablePanel.setBackground(Color.white);

		// JButton btnDetail = new JButton("Show Detail");
		ImageIcon ico_export = new ImageIcon(
				TableInputPanel.class.getResource("ic_action_save_black.png"));
		btnExport.setIcon(ico_export);
		btnExport.setPreferredSize(new Dimension(150, 40));

		btnExport.setHorizontalAlignment(SwingConstants.CENTER);

		listLabel = new JLabel[listTable.size()];
		tablePanel.setLayout(new GridLayout(listLabel.length, 1, 10, 40));

		for (int i = 0; i < listLabel.length; i++) {
			listLabel[i] = new JLabel(listTable.get(i).toString());
			listLabel[i].setBorder(BorderFactory.createLineBorder(Color.red));
			tablePanel.add(listLabel[i]);
		}
		if (year > 0)
			showResult(capital);
		else
			showBigMResult();

		btnExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String value = "<html><body >";
				for (int i = 0; i < listTable.size(); i++) {
					value += listTable.get(i).getHtmlTable();
				}

				value += "<body></html>";
				new ExportFile(value);

			}
		});

		add(tool);
		add(preview);
		add(detailPanel);
		add(scrollPaneRes);
		preview.doExplode();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void showBigMResult() {

		JLabel lbl = new JLabel();
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setOpaque(true);
		lbl.setBackground(Color.blue.brighter().brighter());
		lbl.setForeground(Color.white.brighter());
		lbl.setFont(new Font("Arial", Font.PLAIN, 20));
		TableData data = listTable.get(listTable.size() - 1);
		int[] index = bigM.getIndexCB();
		String str = "<html><table border=1 cellspacing=0 >";
		str += "<tr><th>Variable</th><th>Value</th></tr>";
		for (int i = 0; i < index.length; i++) {
			if (index[i] < business.length) {
				str += "<tr><td>" + business[index[i]].getName() + "</td><td>"
						+ data.getA()[i][data.getA()[i].length - 1]
						+ "</td><tr>";
			} else {
				str += "<tr><td>" + data.getVarInBasis()[i] + "</td><td>"
						+ data.getA()[i][data.getA()[i].length - 1]
						+ "</td><tr>";
			}
		}
		str += "</table></html>";
		lbl.setText(str);
		panel.add(lbl);

	}

	public void showResult(double capital) {
		JLabel lbl = new JLabel();
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setOpaque(true);
		lbl.setBackground(Color.black);
		lbl.setForeground(Color.white.brighter());
		lbl.setFont(new Font("Arial", Font.PLAIN, 20));
		String str = "<html><table border=1 cellspacing=0 >";
		TableData data = listTable.get(listTable.size() - 1);
		int[] index = bigM.getIndexCB();
		str += "<tr><th>Investment Name</th><th>Year</th><th>Amount</th><th>Profit</th></tr>";
		DecimalFormat df = new DecimalFormat();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		df.setDecimalFormatSymbols(symbols);
		df.setMaximumFractionDigits(0);
		df.setMinimumFractionDigits(0);
		df.setRoundingMode(RoundingMode.HALF_UP);

		double listTotal = 0;
		// double[] busTotalAmount = new double[business.length];
		for (int i = 0; i < index.length; i++) {
			int indexBusiness = index[i] / year;

			String strYear = String.valueOf((index[i] % year) + 1);

			if (indexBusiness < business.length) {
				str += "<tr align= center ><td>";
				double totalAmount = data.getA()[i][data.getA()[0].length - 1].v;

				double profit = totalAmount
						* business[indexBusiness].getInterest() / 100;
				listTotal += profit;
				String strProfit = df.format(profit);
				String amt = df.format(totalAmount);
				str += business[indexBusiness].getName() + "</td><td>"
						+ strYear + "</td><td>" + amt + "</td><td>" + strProfit
						+ "</td></tr>";

			}

		}

		String z = df.format(capital + listTotal);
		String pro = df.format(listTotal);
		str += "<tr><td colspan=2>Maximize Total Return on Investments </td><td>"
				+ z + "</td><td>" + pro + "</td></tr>";
		str += "</table></html>";
		lbl.setText(str);
		panel.add(lbl);

	}

}
