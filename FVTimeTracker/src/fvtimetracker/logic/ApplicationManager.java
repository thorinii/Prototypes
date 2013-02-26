/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.logic;

import fvtimetracker.Main;
import fvtimetracker.data.*;
import fvtimetracker.gui.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author lachlan
 */
public class ApplicationManager {

	public static final int DEFAULT_POPUP_TIMEOUT = 1;
	public static final int DEFAULT_AFC_TIMEOUT = 5;
	private Datastore datastore;
	private MainWindow mainWindow;
	private TaskPopup taskPopup;
	private ReturnToComputerPopup rtcPopup;
	private TrayIconHandler trayIconHandler;
	private Timer popupTimer;
	private Timer afcTimer;
	private StopWatch timeStopWatch;

	public ApplicationManager() {
		datastore = new FlatFileDatabaseDatastore();
		mainWindow = new MainWindow(this);
		taskPopup = new TaskPopup(this);
		rtcPopup = new ReturnToComputerPopup(this);
		trayIconHandler = new TrayIconHandler(this);


		timeStopWatch = new StopWatch();


		int popupTimeout = Main.PREF_ROOT.getInt("popup-timeout-mins",
				DEFAULT_POPUP_TIMEOUT);
		popupTimer = new Timer(popupTimeout * 60 * 1000,
				new PopupTimerListener());
		popupTimer.setRepeats(false);


		int afcTimeout = Main.PREF_ROOT.getInt("afc-timeout-mins",
				DEFAULT_AFC_TIMEOUT);
		afcTimer = new Timer(afcTimeout * 60 * 1000, new AFCTimerListener());
		afcTimer.setRepeats(false);


		if (!setupDatabase()) {
			JOptionPane.showMessageDialog(null,
					"Could not connect to database", "FV Time Tracker",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void startApplication(boolean startOpen) {
		trayIconHandler.show();

		if (datastore.isConnected()) {
			startTiming();
		} else {
			trayIconHandler.setPassiveMessage("Not Connected");
		}

		if (startOpen || !datastore.isConnected()) {
			mainWindow.setVisible(true);
		}
	}

	public void startTiming() {
		if (!datastore.isConnected()) {
			setupDatabase();
			return;
		}

		timeStopWatch.restartTimer();

		popupTimer.start();
		trayIconHandler.setPassiveMessage("Active");
	}

	public void pauseTiming() {
		popupTimer.stop();
		trayIconHandler.setPassiveMessage("Paused");
	}

	private boolean setupDatabase() {
		if (datastore.isConnected()) {
			return true;
		}

		try {
			datastore.connect();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}

		return true;
	}

	public void showMainWindow() {
		mainWindow.setVisible(true);
	}

	public void exit() {
		mainWindow.dispose();
		taskPopup.dispose();
		rtcPopup.dispose();

		try {
			datastore.disconnect();
		} catch (IOException ex) {
			Logger.getLogger(ApplicationManager.class.getName()).
					log(Level.SEVERE, null, ex);
		}

		System.exit(0);
	}

	public void submitTask(String task) {
		long startTime = timeStopWatch.getStartTime();
		int duration = (int) (timeStopWatch.getTime() / 1000 / 60);

		if (duration > 0) {
			datastore.submitRecord(task, startTime, duration);

			timeStopWatch.restartTimer();
		}

		afcTimer.stop();

		int popupTimeout = Main.PREF_ROOT.getInt("popup-timeout-mins",
				DEFAULT_POPUP_TIMEOUT);
		popupTimer.setInitialDelay(popupTimeout * 60 * 1000);
		popupTimer.setDelay(popupTimeout * 60 * 1000);

		popupTimer.start();
	}

	public boolean getAlwaysOnTop() {
		return Main.PREF_ROOT.getBoolean("always-on-top", true);
	}

	public StopWatch getTimeStopWatch() {
		return timeStopWatch;
	}

	public boolean isTracking() {
		return popupTimer.isRunning();
	}

	public Datastore getDatabase() {
		return datastore;
	}

	private class PopupTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int afcTimeout = Main.PREF_ROOT.getInt("afc-timeout-mins",
					DEFAULT_AFC_TIMEOUT);
			afcTimer.setInitialDelay(afcTimeout * 60 * 1000);
			afcTimer.setDelay(afcTimeout * 60 * 1000);
			afcTimer.start();

			taskPopup.showPopup();
		}

	}

	private class AFCTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (taskPopup.isVisible()) {
				taskPopup.setVisible(false);
			}

			pauseTiming();

			rtcPopup.setVisible(true);
		}

	}

}