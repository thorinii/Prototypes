/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.gui;

import fvtimetracker.logic.ApplicationManager;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author lachlan
 */
public class TrayIconHandler {

	private ApplicationManager applicationManager;
	private TrayIcon trayIcon;
	private JPopupMenu popupMenu;

	public TrayIconHandler(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;

		Image img;
		try {
			img = ImageIO.read(new File("clock.png"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(1);
			return;
		}

		trayIcon = new TrayIcon(img, "FV Time Tracker", null);
		trayIcon.setImageAutoSize(true);
		trayIcon.setActionCommand("show");
		trayIcon.addActionListener(new MenuHandler());

		popupMenu = new JPopupMenu();

		MenuHandler menuHandler = new MenuHandler();

		JMenuItem showItem = new JMenuItem("Show FV Time Tracker");
		showItem.setActionCommand("show");
		showItem.addActionListener(menuHandler);
		popupMenu.add(showItem);

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setActionCommand("exit");
		exitItem.addActionListener(menuHandler);
		popupMenu.add(exitItem);

		trayIcon.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
					popupMenu.setLocation(e.getX(), e.getY());
					popupMenu.setInvoker(popupMenu);
					popupMenu.setVisible(true);
				}
			}

		});
	}

	public void show() {
		boolean worked = showTray();

		while (!worked) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				System.exit(0);
				return;
			}

			worked = showTray();
		}
	}

	private boolean showTray() {
		try {
			SystemTray.getSystemTray().add(trayIcon);
			return true;
		} catch (AWTException awte) {
			return false;
		}
	}

	public void hide() {
		SystemTray.getSystemTray().remove(trayIcon);
	}

	public void setPassiveMessage(String message) {
		if (trayIcon == null) {
			throw new IllegalStateException(
					"Cannot set a message on nonexistant icon");
		}

		trayIcon.setToolTip("FV Time Tracker - " + message);
	}

	public void showPopupMessage(String message) {
		if (trayIcon == null) {
			throw new IllegalStateException(
					"Cannot show a message on nonexistant icon");
		}

		trayIcon.displayMessage("FV Time Tracker", message,
				TrayIcon.MessageType.INFO);
	}

	private class MenuHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();

			if (action.equals("exit")) {
				applicationManager.exit();
			}

			if (action.equals("show")) {
				applicationManager.showMainWindow();
			}
			
			popupMenu.setVisible(false);
		}

	}

}
