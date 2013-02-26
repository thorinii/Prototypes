/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package niawtscan;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import niawtscan.gui.StartJobFrame;

/**
 *
 * @author lachlan
 */
public class NiawtScanMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws InterruptedException {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					setLookandFeel();
				}

			});
		} catch (InvocationTargetException ite) {
			Logger.getLogger(StartJobFrame.class.getName()).log(
					Level.WARNING, "Problem setting look and feel", ite);
		}

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				StartJobFrame mainFrame = new StartJobFrame();
				mainFrame.setVisible(true);
			}

		});
	}

	private static void setLookandFeel() {
		try {
			String systemLaF = UIManager.getSystemLookAndFeelClassName();

			if (systemLaF != null && !systemLaF.isEmpty()) {
				UIManager.setLookAndFeel(systemLaF);
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			Logger.getLogger(StartJobFrame.class.getName()).log(
					Level.WARNING, "Could not set system look and fell", ex);
		}
	}

}
