/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.logic;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author lachlan
 */
public class StopWatchTest {
	private void do60Millis(StopWatch stopWatch){
		final int SIXTY_MILLIS = 60;
		final int ERROR_RANGE = 10;

		stopWatch.restartTimer();

		try {
			Thread.sleep(SIXTY_MILLIS);
		} catch (InterruptedException ex) {
			fail("Interrupted Exception");
			return;
		}

		long time = stopWatch.getTime();

		System.out.println(" - value=" + time);

		assertTrue(time > (SIXTY_MILLIS - ERROR_RANGE));
		assertTrue(time < (SIXTY_MILLIS + ERROR_RANGE));
	}
	
	@Test
	public void checkCanRestart(){
		System.out.println("checkCanRestart - 3 times");

		StopWatch stopWatch = new StopWatch();
		do60Millis(stopWatch);
		do60Millis(stopWatch);
		do60Millis(stopWatch);
	}

	@Test
	public void check60Millis() {
		System.out.println("check60Millis");

		StopWatch stopWatch = new StopWatch();
		do60Millis(stopWatch);
	}

	@Test(expected = IllegalStateException.class)
	public void checkThrowsIllegalStateExceptionWhenNotStarted() {
		System.out.println("checkThrowsIllegalStateExceptionWhenNotStarted");

		StopWatch stopWatch = new StopWatch();

		// Exception throwing method
		stopWatch.getTime();
	}

}
