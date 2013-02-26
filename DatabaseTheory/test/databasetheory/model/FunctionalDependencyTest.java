/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasetheory.model;

import static databasetheory.HelperFeatures.set;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author lachlan
 */
public class FunctionalDependencyTest {

	@Test
	public void testSimplifyPartlyTrivial() {
		FunctionalDependency trivial = new FunctionalDependency(
				new String[]{"A", "C", "D"},
				new String[]{"B", "C", "E"});

		FunctionalDependency result = trivial.simplify();

		assertEquals("{A, C, D -> B, E}", result.toString());


		trivial = new FunctionalDependency(set("A"), set("A", "B"));
		assertEquals("{A -> B}", trivial.simplify().toString());

	}

	@Test
	public void testSimplifyVeryTrivial() {
		FunctionalDependency trivial = new FunctionalDependency(
				new String[]{"A", "B", "C"},
				new String[]{"A", "B", "C"});

		FunctionalDependency result = trivial.simplify();

		assertEquals(null, result);
	}

	@Test
	public void testToString() {
		// Plain toString
		FunctionalDependency fd = new FunctionalDependency(
				new String[]{"A", "B"}, new String[]{"C", "D", "E"});

		String toString = fd.toString();
		assertEquals("{A, B -> C, D, E}", toString);

		// Sorted toString
		fd = new FunctionalDependency(
				new String[]{"B", "A"}, new String[]{"C", "E", "D"});

		toString = fd.toString();
		assertEquals("{A, B -> C, D, E}", toString);
	}
	
	@Test
	public void testEquals(){
		FunctionalDependency fd1, fd2;
		
		fd1 = new FunctionalDependency(set("D"), set("F"));
		fd2 = new FunctionalDependency(set("D"), set("F", "G", "H"));
		
		assertFalse(fd1.equals(fd2));
		assertFalse(fd2.equals(fd1));
	}

	@Test
	public void testCompareTo(){
		FunctionalDependency fd1, fd2;
		
		fd1 = new FunctionalDependency(set("D"), set("F"));
		fd2 = new FunctionalDependency(set("D"), set("F", "G", "H"));
		
		assertTrue("fd1 - fd2 = " + fd1.compareTo(fd2), fd1.compareTo(fd2)<0);
		assertTrue("fd1 - fd2 = " + fd2.compareTo(fd1), fd2.compareTo(fd1)>0);
	}
}
