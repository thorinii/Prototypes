/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker;

import fvtimetracker.logic.ApplicationManager;
import java.util.prefs.Preferences;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author lachlan
 */
public class Main {

	public static final Preferences PREF_ROOT;

	static {
		PREF_ROOT = Preferences.userNodeForPackage(Main.class);
	}

	public static void main(String[] args) {
		/*
		 * Set the System look and feel
		 */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
		//</editor-fold>

		try {
			startApplication(args);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static void startApplication(String[] args) throws Exception {

		boolean startOpen = true;
		if (args.length > 0) {
			if (args[0].equals("-hide")) {
				startOpen = false;
			} else {
				printUsage();
				return;
			}
		}


		ApplicationManager applicationManager = new ApplicationManager();
		applicationManager.startApplication(startOpen);
	}

	private static void printUsage() {
		System.out.println("Flipside Virtual Time Tracker");
		System.out.println("Usage:");
		System.out.println(
				"  java -jar FVTimeTracker.jar [-hide]");
		System.out.println(
				"  java -classpath FVTimeTracker.jar fvtimetracker.Main [-hide]");
		System.out.println();
		System.out.println(
				"  -hide\t Starts the application in tray icon mode, if left out will open GUI");
	}

}
