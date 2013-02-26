/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms;

import graphalgorithms.gui.MainFrame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author lachlan
 */
public class GraphAlgorithms {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			Logger.getLogger(GraphAlgorithms.class.getName()).
					log(Level.SEVERE, null, ex);
		}

		MainFrame frame = new MainFrame();
		frame.setVisible(true);

		frame.newProblem();
	}

}
