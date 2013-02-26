/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructuretests;

import datastructuretests.data.DSArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lachlan
 */
public class DataStructureTests {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			gc();
			test(new DSArrayList<>(), "Custom");
			gc();
			test(new ArrayList<>(), "Standard");
			
			System.out.println();
		}
	}

	private static void test(List<Object> list, String testName) {
		final int ITER = 5000000;
		System.out.printf("Test %20s - %d - ", testName, ITER);

		long start = System.nanoTime();

		Object obj;
		for (int i = 0; i < ITER; i++) {
			obj = new Object();

			list.add(obj);
		}

		long ns = System.nanoTime() - start;
		double time = ((double) ns) / 1000000000;

		System.out.println(time + " s");
	}

	private static void gc() {
		try {
			for (int i = 0; i < 10; i++) {
				Thread.sleep(30);
				System.gc();
			}
		} catch (InterruptedException ex) {
			Logger.getLogger(DataStructureTests.class.getName()).
					log(Level.SEVERE, null, ex);
		}
	}

}
