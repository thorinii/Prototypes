/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagetextmodefilter;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 * @author lachlan
 */
public class ImageTextModeFilter {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setup();
		gui.setVisible(true);
	}
	
}
