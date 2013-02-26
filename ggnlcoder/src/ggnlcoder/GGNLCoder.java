/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ggnlcoder;

import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import java.io.IOException;
import java.util.logging.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Lachlan
 */
public class GGNLCoder {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// Set up Logger
		try {
			FileHandler fileHandler = new FileHandler("log-%g-%u.txt");
			fileHandler.setFormatter(new SimpleFormatter());
			fileHandler.setLevel(Level.ALL);
			
			Logger.getLogger("").addHandler(fileHandler);
		} catch (IOException ioe) {
			Logger.getLogger(GGNLCoder.class.getName()).
					log(Level.WARNING, "IOException when setting up logger", ioe);
		}

		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code ">
        /*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					boolean worked = false;
					for (UIManager.LookAndFeelInfo info :
							UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							worked = true;
							break;
						}
					}

					if (!worked) {
						UIManager.setLookAndFeel(
								UIManager.getSystemLookAndFeelClassName());
					}
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(this.getClass().getName()).
							log(Level.SEVERE, "Cannot set look&feel", ex);
				} catch (InstantiationException ex) {
					Logger.getLogger(this.getClass().getName()).
							log(Level.SEVERE, "Cannot set look&feel", ex);
				} catch (IllegalAccessException ex) {
					Logger.getLogger(this.getClass().getName()).
							log(Level.SEVERE, "Cannot set look&feel", ex);
				} catch (UnsupportedLookAndFeelException ex) {
					Logger.getLogger(this.getClass().getName()).
							log(Level.SEVERE, "Cannot set look&feel", ex);
				}
			}

		});
		//</editor-fold>

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MainFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
			}

		});
	}

}
