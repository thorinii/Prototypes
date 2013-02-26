/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasetheory;

import org.junit.*;
import static org.junit.Assert.*;
import static databasetheory.HelperFeatures.*;

/**
 *
 * @author lachlan
 */
public class HelperFeaturesTest {

	/**
	 * Test of list method, of class HelperFeatures.
	 */
	@Test
	public void testList() {
	}

	/**
	 * Test of set method, of class HelperFeatures.
	 */
	@Test
	public void testSet_GenericType() {
	}
	
	@Test
	public void testSetWithoutSingular() {
		assertEquals(set("A"), setWithout(set("A", "B"), "B"));
	}

	/**
	 * Test of set method, of class HelperFeatures.
	 */
	@Test
	public void testSet_2args_2() {
	}
	
}
