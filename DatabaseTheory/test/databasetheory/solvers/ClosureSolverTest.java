/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasetheory.solvers;

import databasetheory.model.FunctionalDependency;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static databasetheory.HelperFeatures.*;

/**
 *
 * @author lachlan
 */
public class ClosureSolverTest {

	private FunctionalDependency[] fds;

	public ClosureSolverTest() {
		fds = new FunctionalDependency[]{
			new FunctionalDependency(set("A"), set("D")),
			new FunctionalDependency(set("B"), set("C")),
			new FunctionalDependency(set("A", "D"), set("B")),
			new FunctionalDependency(set("B", "D"), set("E")),
			new FunctionalDependency(set("G", "F"), set("N")),
			new FunctionalDependency(set("E"), set("H")),
			new FunctionalDependency(set("H"), set("F", "G"))
		};
	}

	@Test
	public void testNullClosure() {
		ClosureSolver solver = new ClosureSolver(fds);

		Set<String> closure = solver.closure();

		assertTrue(closure.isEmpty());
	}

	@Test
	public void testClosureOfC() {
		ClosureSolver solver = new ClosureSolver(fds);

		Set<String> closure = solver.closure("C");

		assertEquals(set("C"), closure);
	}

	@Test
	public void testClosureOfB() {
		ClosureSolver solver = new ClosureSolver(fds);

		Set<String> closure = solver.closure("B");

		assertEquals(set("B", "C"), closure);
	}

	@Test
	public void testClosureOfA() {
		ClosureSolver solver = new ClosureSolver(fds);

		Set<String> closure = solver.closure("A");

		assertEquals(set("A", "B", "C", "D", "E", "F", "G", "H", "N"), closure);
	}

	@Test
	public void testAllAttributes() {
		ClosureSolver solver = new ClosureSolver(fds);

		Set<String> all = solver.allAttributesAvailable();

		assertEquals(set("A", "B", "C", "D", "E", "F", "G", "H", "N"), all);
	}

}
