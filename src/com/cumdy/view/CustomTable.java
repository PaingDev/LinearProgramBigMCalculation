package com.cumdy.view;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CustomTable extends JTable {
	DefaultTableModel dm = new DefaultTableModel();

	public CustomTable(int row) {
		super();
		setFont(new Font("Zawgyi-One", Font.PLAIN, 17));
		dm.setColumnCount(4);

		getTableHeader().setReorderingAllowed(false);
		// setSelectionBackground(Color.white.darker().darker());
		dm.setRowCount(row);
		setModel(dm);

		initializeTableHeader();

	}

	public void setRow(int row) {
		dm.setRowCount(row);
	}

	public int getRow() {

		return dm.getRowCount();
	}

	private void initializeTableHeader() {
		String[] strHeader = new String[] { "Investment Name",
				"Limited Amount", "Return Year", "Investment Interest %" };
		for (int i = 0; i < strHeader.length; i++) {
			getColumnModel().getColumn(i).setWidth(100);

			getColumnModel().getColumn(i).setHeaderValue(strHeader[i]);

		}

		setRowHeight(30);

		// setFont(new Font("Zawgyi-One", Font.PLAIN, 17));
		getTableHeader().setFont(new Font("Zawgyi-One", Font.PLAIN, 17));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
