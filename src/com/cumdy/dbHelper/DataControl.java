package com.cumdy.dbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cumdy.entity.Business;

public class DataControl {
	private Connection conn;
	private static String insertStudent = "INSERT INTO Business(name,amount,year,interest) VALUES( ? , ? , ? , ? );";
	private static String deleteBusiness = "DELETE * FROM Business WHERE name = ? AND amount = ? AND year = ? AND interest = ? ;";

	private static String selectAll = "SELECT * FROM Business";

	public DataControl(Connection conn) {
		this.conn = conn;
	}

	public void insertIntoTable(String name, String initial, String year,
			String interest) throws SQLException {
		PreparedStatement pst = conn.prepareStatement(insertStudent);
		pst.setString(1, name);
		pst.setString(2, initial);
		pst.setString(3, year);
		pst.setString(4, interest);
		pst.executeUpdate();
		pst.close();
	}

	public void deleteBusiness(Business bus) throws SQLException {
		PreparedStatement pst = conn.prepareStatement(deleteBusiness);
		pst.setString(1, bus.getName());
		pst.setString(2, String.valueOf(bus.getInitial()));
		pst.setString(3, String.valueOf(bus.getRetRate()));
		pst.setString(4, String.valueOf(bus.getInterest()));
		pst.executeUpdate();
		pst.close();
	}

	public List<Business> getAllStudent() throws SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(selectAll);
		List<Business> list = new ArrayList<Business>();
		while (rs.next()) {
			String name = rs.getString("name");
			double amount = rs.getDouble("amount");
			double interest = rs.getDouble("interest");
			float year = rs.getFloat("year");

			list.add(new Business(name, amount, year, interest));

		}
		rs.close();
		st.close();
		return list;
	}

}
