/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appstartupservice;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 *
 * @author lachlan
 */
public class AppStartupService {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			PrintStream ps = new PrintStream(AppBooter.cwd() + "/out.txt");
			System.setErr(ps);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

		AppBooter booter = new AppBooter();
		booter.loadApps();

		try {
			booter.bootAll();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

}
