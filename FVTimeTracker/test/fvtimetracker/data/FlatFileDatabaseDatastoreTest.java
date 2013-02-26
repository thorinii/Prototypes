/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.data;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author lachlan
 */
public class FlatFileDatabaseDatastoreTest {

	private FlatFileDatabaseDatastore ffdd;

	@Before
	public void setupDatabase() throws Exception {
		ffdd = new FlatFileDatabaseDatastore();

		ffdd.connect("testdb");
		ffdd.wipeAllData();


		for (int i = 0; i < 10; i++) {
			ffdd.submitRecord("a long task " + i, 500 * i + 3, i + 2);
		}


		for (int i = 3; i < 60; i++) {
			ffdd.submitRecord("a while later",
					1000L * 60 * 60 * 24 * 7 * i, 6);
		}
	}

	@After
	public void disposeDatabase() throws Exception {
		ffdd.wipeAllData();
		ffdd.disconnect();
	}

	@Test
	public void testTaskTimesInBetween() {
		int start = 500 * 2 + 3;
		int end = 500 * 7 + 3;

		System.out.println("testTaskTimesInBetween");

		List<String[]> datas = ffdd.getTasksTimes(start, end);

		for (String[] data : datas) {
			System.out.print("{");
			for (String datum : data) {
				System.out.print(", " + datum);
			}
			System.out.println("}");
		}
		System.out.println("testTaskTimesInBetween-end");

		assertEquals("Has not got required entries", 6, datas.size());
	}

}
