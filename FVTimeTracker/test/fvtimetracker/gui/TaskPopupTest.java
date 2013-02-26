/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.gui;

import fvtimetracker.logic.ApplicationManager;
import fvtimetracker.logic.StopWatch;
import static org.junit.Assert.fail;
import org.junit.*;

/**
 *
 * @author lachlan
 */
public class TaskPopupTest {

	public TaskPopupTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	/**
	 * Test of showPopup method, of class TaskPopup.
	 */
	@Test
	public void testShowPopup() {
		TaskPopup popup = new TaskPopup(new ApplicationManager() {

			@Override
			public boolean getAlwaysOnTop() {
				return false;
			}

			@Override
			public StopWatch getTimeStopWatch() {
				return new StopWatch() {

					@Override
					public long getTime() {
						return 4 * 60 * 1000;
					}

				};
			}

		});

		popup.showPopup();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			fail("IE: " + ex);
		}

		popup.setVisible(false);
	}

}
