/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.data;

import fvtimetracker.Main;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 *
 * @author lachlan
 */
public class MySQLDatastore extends DatabaseDatastore {

	public MySQLDatastore() {
	}

	public void connect(String host, String database, String username,
			String password) throws IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection(
					"jdbc:mysql://" + host + "/" + database, username, password);

			if (!checkInstalled()) {
				install();
			}

			submitRecord = connection.prepareStatement(
					"INSERT INTO " + TABLE_RECORD
					+ " (`datestamp`, `duration`, `task`) VALUES (?, ?, ?)");
		} catch (ClassNotFoundException | SQLException cnfe) {
			throw new IOException(cnfe);
		}
	}

	private boolean checkInstalled() throws SQLException {
		if (!isConnected()) {
			return false;
		}
		try (Statement statement = connection.createStatement()) {
			ResultSet set = statement.executeQuery("SHOW TABLES");

			List<String> tables = JDBCHelper.singleColDataSetToList(set);

			if (tables.contains(TABLE_RECORD)) {
				return true;
			}
		}

		return false;
	}

	private void install() throws SQLException {
		try (Statement statement = connection.createStatement()) {
			statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_RECORD
					+ " ("
					+ "`id` INT AUTO_INCREMENT PRIMARY KEY, "
					+ "`datestamp` BIGINT, "
					+ "`task` VARCHAR(255), "
					+ "`duration` INT)");
		}
	}

}
