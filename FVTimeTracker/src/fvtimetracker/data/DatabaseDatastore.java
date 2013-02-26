/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.data;

import fvtimetracker.Main;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 *
 * @author lachlan
 */
public abstract class DatabaseDatastore implements Datastore {

	public static final String TABLE_BASENAME = "fvtt_";
	public static final String TABLE_RECORD = TABLE_BASENAME + "record";
	protected Connection connection;
	protected PreparedStatement submitRecord;

	public DatabaseDatastore() {
	}

	@Override
	public void connect() throws IOException {
		String host = Main.PREF_ROOT.get("db-host", "localhost");
		String db = Main.PREF_ROOT.get("db-name", "");
		String username = Main.PREF_ROOT.get("db-user", "");
		String password = Main.PREF_ROOT.get("db-password", "");
		if (!db.equals("") && !username.equals("")) {
			connect(host, db, username, password);
		}
	}

	protected void connect(String host, String database, String username,
			String password) throws IOException {
	}

	@Override
	public void disconnect() throws IOException {
		if (connection == null) {
			return;
		}
		try {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new IOException(ex);
		}
	}

	/**
	 * Gets a list of the tasks in the database
	 *
	 * @return all the tasks
	 */
	@Override
	public List<String> getTasks() {
		if (!isConnected()) {
			return null;
		}
		try (Statement statement = connection.createStatement()) {
			ResultSet set = statement.executeQuery("SELECT `task` FROM "
					+ TABLE_RECORD + " " + "GROUP BY `task`"
					+ "ORDER BY `task` ASC");
			List<String> tasks = JDBCHelper.singleColDataSetToList(set);
			return tasks;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets a list of the tasks in the database ordered by the last use
	 *
	 * @return all the tasks
	 */
	@Override
	public List<String> getTasksOrderByRecent() {
		if (!isConnected()) {
			return null;
		}
		try (Statement statement = connection.createStatement()) {
			ResultSet set = statement.executeQuery("SELECT `task` FROM "
					+ TABLE_RECORD + " " + "GROUP BY `task`"
					+ "ORDER BY MAX(`datestamp`) DESC");
			List<String> tasks = JDBCHelper.singleColDataSetToList(set);

			return tasks;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets a list of the tasks in the database ordered by the last use, limited
	 * to a maximum number.
	 *
	 * @return count number of tasks maximum
	 */
	@Override
	public List<String> getTasksOrderByRecent(int count) {
		if (!isConnected()) {
			return null;
		}
		try (Statement statement = connection.createStatement()) {
			ResultSet set = statement.executeQuery("SELECT `task` FROM "
					+ TABLE_RECORD + " " + "GROUP BY `task` "
					+ "ORDER BY MAX(`datestamp`) DESC " + "LIMIT " + count);
			List<String> tasks = JDBCHelper.singleColDataSetToList(set);
			return tasks;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String[]> getTasksTimes() {
		if (!isConnected()) {
			return null;
		}
		try (Statement statement = connection.createStatement()) {
			ResultSet set = statement.executeQuery("SELECT `task`, SUM(`duration`) FROM "
					+ TABLE_RECORD + " " + "GROUP BY `task` "
					+ "ORDER BY MAX(`datestamp`) DESC");
			List<String[]> tasks = new ArrayList<>();
			while (set.next()) {
				String[] data = new String[2];
				data[0] = set.getString(1);
				data[1] = set.getString(2);
				tasks.add(data);
			}
			return tasks;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets list of tasks within now and
	 * <code>daysSince</code> ago.
	 *
	 * @param daysSince the number of days in the past to start from
	 * @return tasks
	 */
	@Override
	public List<String[]> getTasksTimes(long daysSince) {
		if (!isConnected()) {
			return null;
		}
		long now = System.currentTimeMillis();
		long after = daysSince * 24 * 60 * 60 * 1000;

		try (Statement statement = connection.createStatement()) {
			ResultSet set = statement.executeQuery("SELECT `task`, SUM(`duration`) FROM "
					+ TABLE_RECORD + " "
					+ "WHERE `datestamp` > (" + (now - after)
					+ ")"
					+ " GROUP BY `task` "
					+ "ORDER BY MAX(`datestamp`) DESC");

			List<String[]> tasks = new ArrayList<>();
			while (set.next()) {
				String[] data = new String[2];
				data[0] = set.getString(1);
				data[1] = set.getString(2);
				tasks.add(data);
			}

			return tasks;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets list of tasks between the two specified absolute dates. The tasks
	 * will be ordered alphabetically.
	 *
	 * @param start the date to start from
	 * @param end the date to finish with
	 * @return tasks
	 */
	@Override
	public List<String[]> getTasksTimes(long start, long end) {
		if (!isConnected()) {
			return null;
		}

		try (Statement statement = connection.createStatement()) {
			ResultSet set = statement.executeQuery("SELECT `task`, SUM(`duration`) FROM "
					+ TABLE_RECORD + " "
					+ "WHERE `datestamp` >= (" + start + ")"
					+ " AND `datestamp` <= (" + end + ")"
					+ " GROUP BY `task`"
					+ " ORDER BY `task` ASC");

			List<String[]> tasks = new ArrayList<>();
			while (set.next()) {
				String[] data = new String[2];
				data[0] = set.getString(1);
				data[1] = set.getString(2);

				if (!data[0].isEmpty()) {
					tasks.add(data);
				}
			}

			return tasks;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

	@Override
	public long[] getTaskInformation(String task) {
		if (!isConnected()) {
			return null;
		}

		try (Statement statement = connection.createStatement()) {
			ResultSet set = statement.executeQuery("SELECT SUM(`duration`), MAX(`datestamp`) FROM "
					+ TABLE_RECORD + " "
					+ "WHERE `task` = '" + task + "'"
					+ " GROUP BY `task`");

			long[] info = new long[2];
			while (set.next()) {
				info[0] = set.getLong(1);
				info[1] = set.getLong(2);
			}

			return info;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<Long, Integer> getTaskWeekInfo(String task) {
		if (!isConnected()) {
			return null;
		}

		try (Statement statement = connection.createStatement()) {
			Map<Long, Integer> info = new HashMap<>();
			
			ResultSet set = statement.executeQuery(
					"SELECT FLOOR(`datestamp` / 604800000) * 604800000 AS week_group, SUM(`duration`)"
					+ " FROM " + TABLE_RECORD + " "
					+ "WHERE `task` = '" + task + "'"
					+ " GROUP BY week_group");
			
			while (set.next()) {
				info.put(set.getLong(1), set.getInt(2));
			}

			return info;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<Long, Integer> getTaskWeekInfo(String task, int count) {
		if (!isConnected()) {
			return null;
		}

		try (Statement statement = connection.createStatement()) {
			Map<Long, Integer> info = new TreeMap<>();
			
			ResultSet set = statement.executeQuery(
					"SELECT FLOOR(`datestamp` / 604800000) * 604800000 AS week_group, SUM(`duration`)"
					+ " FROM " + TABLE_RECORD
					+ " WHERE `task` = '" + task + "'"
					+ " GROUP BY week_group"
					+ " ORDER BY week_group DESC"
					+ " LIMIT " + count);
			
			while (set.next()) {
				info.put(set.getLong(1), set.getInt(2));
			}

			return info;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}
	
	

	@Override
	public void renameTask(String oldTask, String newTask) {
		try (Statement statement = connection.createStatement()) {
			statement.execute("UPDATE "
					+ TABLE_RECORD
					+ " SET `task` = '" + newTask + "'"
					+ " WHERE `task` = '" + oldTask + "'");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	@Override
	public void deleteTask(String task) {
		try (Statement statement = connection.createStatement()) {
			statement.execute("DELETE FROM "
					+ TABLE_RECORD + " "
					+ "WHERE `task` = '" + task + "'");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	@Override
	public void wipeAllData() {
		try (Statement statement = connection.createStatement()) {
			statement.execute("DELETE FROM "
					+ TABLE_RECORD);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	@Override
	public void wipeDataBefore(long time) {
		try (Statement statement = connection.createStatement()) {
			statement.execute("DELETE FROM "
					+ TABLE_RECORD + " "
					+ "WHERE `datestamp` < (" + (time) + ")");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	@Override
	public boolean isConnected() {
		try {
			return connection != null && !connection.isClosed();
		} catch (SQLException sqle) {
			connection = null;
			return false;
		}
	}

	/**
	 * Stores a task record in the database.
	 *
	 * @param task the name of the task
	 * @param startTime the time when the task was started
	 * @param duration the duration of the task in minutes
	 */
	@Override
	public void submitRecord(String task, long startTime, int duration) {
		if (task == null) {
			return;
		}

		task = task.trim();

		if (task.isEmpty()) {
			return;
		}

		try {
			submitRecord.setLong(1, startTime);
			submitRecord.setInt(2, duration);
			submitRecord.setString(3, task);
			submitRecord.execute();
			submitRecord.clearParameters();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

}
