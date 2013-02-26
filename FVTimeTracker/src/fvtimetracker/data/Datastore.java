/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.data;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lachlan
 */
public interface Datastore {

	public void connect() throws IOException;

	public void disconnect() throws IOException;

	/**
	 * Gets a list of the tasks in the database
	 *
	 * @return all the tasks
	 */
	public List<String> getTasks();

	/**
	 * Gets a list of the tasks in the database ordered by the last use
	 *
	 * @return all the tasks
	 */
	public List<String> getTasksOrderByRecent();

	/**
	 * Gets a list of the tasks in the database ordered by the last use, limited
	 * to a maximum number.
	 *
	 * @return count number of tasks maximum
	 */
	public List<String> getTasksOrderByRecent(int count);

	public List<String[]> getTasksTimes();

	public List<String[]> getTasksTimes(long daysSince);

	public List<String[]> getTasksTimes(long start, long end);

	/**
	 * Gets information about a task.
	 *
	 * The returned information is: <ol start="0"> <li>Total Hours</li> <li>Last
	 * datestamp</li> </ol>
	 *
	 *
	 * @param task
	 * @return
	 */
	public long[] getTaskInformation(String task);
	
	public Map<Long, Integer> getTaskWeekInfo(String task);
	
	public Map<Long, Integer> getTaskWeekInfo(String task, int count);

	public void renameTask(String oldTask, String newTask);

	public void deleteTask(String task);

	public void wipeAllData();

	public void wipeDataBefore(long time);

	public boolean isConnected();

	/**
	 * Stores a task record in the database.
	 *
	 * @param task the name of the task
	 * @param startTime the time when the task was started
	 * @param duration the duration of the task in minutes
	 */
	public void submitRecord(String task, long startTime, int duration);

}
