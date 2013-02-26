/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc.test;

import java.sql.*;

/**
 *
 * @author lachlan
 */
public class JDBCTest {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");

		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost/fvburp", "fvburp", "test");
		try {
			doStuff(con);
		} finally {
			con.close();
		}
	}

	public static void doStuff(Connection con) throws Exception {
		Statement stat = con.createStatement();
		
		ResultSet results = stat.executeQuery(
				"SELECT `key`, COUNT(*) FROM `test` GROUP BY `key`");

		while (results.next()) {
			for (int i = 1;; i++) {
				try {
					System.out.print(results.getString(i) + "\t");
				} catch (SQLException sqle) {
					break;
				}
			}

			System.out.println();
		}
	}

}
