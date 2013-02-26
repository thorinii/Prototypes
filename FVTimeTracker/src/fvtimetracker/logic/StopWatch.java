/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.logic;

/**
 *
 * @author lachlan
 */
public class StopWatch {

	private long startTime;

	public StopWatch() {
		startTime = -1;
	}

	public void restartTimer() {
		startTime = System.currentTimeMillis();
	}

	public long getTime() {
		if (startTime < 0) {
			throw new IllegalStateException(
					"Cannot request time when StopWatch is not started.");
		}

		return System.currentTimeMillis() - startTime;
	}

	public long getStartTime() {
		return startTime;
	}

}
