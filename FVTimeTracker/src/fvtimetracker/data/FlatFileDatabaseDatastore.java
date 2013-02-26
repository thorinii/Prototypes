/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.data;

import java.io.*;
import java.sql.*;
import java.util.List;

/**
 *
 * @author lachlan
 */
public class FlatFileDatabaseDatastore extends DatabaseDatastore {

	@Override
	public void connect() throws IOException {
		connect("fvtimetracker");
	}

	public void connect(String database) throws IOException {
		try {
			Class.forName("org.h2.Driver");

			connection = DriverManager.getConnection("jdbc:h2:./" + database);

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
		//CREATE TABLE IF NOT EXISTS fvtt_record (`id` INT AUTO_INCREMENT PRIMARY KEY, `datestamp` BIGINT, `task` VARCHAR(255), `duration` INT)
	}

}
