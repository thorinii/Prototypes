/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demeteranalyser;

import demeteranalyser.faulty.FaultyClass;

/**
 *
 * @author lachlan
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		ClassAnalyser analyser = new ClassAnalyser(Main.class.getName());

		System.out.println(analyser.analyse());
	}

}
