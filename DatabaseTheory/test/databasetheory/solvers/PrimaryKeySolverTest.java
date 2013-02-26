/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasetheory.solvers;

import org.junit.*;
import static org.junit.Assert.*;
import static databasetheory.HelperFeatures.*;
import databasetheory.model.FunctionalDependency;
import java.util.Set;

/**
 *
 * @author lachlan
 */
public class PrimaryKeySolverTest {

	@Test
	public void checkVerySimplePK() {
		PrimaryKeySolver solver = new PrimaryKeySolver();

		Set<String> pk = solver.findPrimaryKey(set(new FunctionalDependency(set(
				"A"), set("B"))));

		assertEquals(set("A"), pk);
	}

	@Test
	public void checkSimplePK() {
		PrimaryKeySolver solver = new PrimaryKeySolver();

		Set<String> pk = solver.findPrimaryKey(set(
				new FunctionalDependency(set("A"), set("B")),
				new FunctionalDependency(set("B"), set("C"))));

		assertEquals(set("A"), pk);
	}

	@Test
	public void checkAnotherPK1() {
		PrimaryKeySolver solver = new PrimaryKeySolver();

		Set<String> pk = solver.findPrimaryKey(set(
				new FunctionalDependency(set("A"), set("E", "F")),
				new FunctionalDependency(set("A"), set("G")),
				new FunctionalDependency(set("A", "B"), set("D")),
				new FunctionalDependency(set("B"), set("C")),
				new FunctionalDependency(set("E", "F"), set("G")),
				new FunctionalDependency(set("A"), set("D"))));

		assertEquals(set("A", "B"), pk);
	}

	@Test
	public void checkAnotherPK2() {
		PrimaryKeySolver solver = new PrimaryKeySolver();

		Set<String> pk = solver.findPrimaryKey(set(
				new FunctionalDependency(set("ParentName"), set("Phone",
				"Address")),
				new FunctionalDependency(set("JobNo"), set("ParentName")),
				new FunctionalDependency(set("JobNo"), set("PercentComplete")),
				new FunctionalDependency(set("ParentName"), set("Tools"))));

		assertEquals(set("JobNo"), pk);
	}

}
