/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasetheory.solvers;

import databasetheory.model.FunctionalDependency;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import static databasetheory.HelperFeatures.*;

/**
 *
 * @author lachlan
 */
public class MinimalBasisSolverTest {

	private MinimalBasisSolver minimalBasisSolver;

	public MinimalBasisSolverTest() {
		minimalBasisSolver = new MinimalBasisSolver();
	}

	@Test
	public void checkAlreadyMinimal() {
		// A -> B
		// B -> C
		// B -> D

		Set<FunctionalDependency> input = set(
				new FunctionalDependency(set("A"), set("B")),
				new FunctionalDependency(set("B"), set("C")),
				new FunctionalDependency(set("B"), set("D")));

		Set<FunctionalDependency> output = minimalBasisSolver.findMinimal(input);

		assertEquals(input, output);
	}

	@Test
	public void checkSimpleMinimal1() {
		Set<FunctionalDependency> input = set(
				new FunctionalDependency(set("A"), set("A", "B", "E")),
				new FunctionalDependency(set("B"), set("C")),
				new FunctionalDependency(set("B"), set("D")),
				new FunctionalDependency(set("A"), set("D")));

		Set<FunctionalDependency> expected = set(
				new FunctionalDependency(set("A"), set("B")),
				new FunctionalDependency(set("A"), set("E")),
				new FunctionalDependency(set("B"), set("C")),
				new FunctionalDependency(set("B"), set("D")));

		Set<FunctionalDependency> output = minimalBasisSolver.findMinimal(input);


		assertEquals(expected, output);
	}

	@Test
	public void checkComplexMinimal() {
		Set<FunctionalDependency> input = set(
				new FunctionalDependency(set("A"), set("E", "F")),
				new FunctionalDependency(set("A"), set("G")),
				new FunctionalDependency(set("A", "B"), set("D")),
				new FunctionalDependency(set("B"), set("C")),
				new FunctionalDependency(set("E", "F"), set("G")),
				new FunctionalDependency(set("A"), set("D")));

		Set<FunctionalDependency> expected = set(
				new FunctionalDependency(set("A"), set("D")),
				new FunctionalDependency(set("A"), set("E")),
				new FunctionalDependency(set("A"), set("F")),
				new FunctionalDependency(set("B"), set("C")),
				new FunctionalDependency(set("E", "F"), set("G")));

		Set<FunctionalDependency> output = minimalBasisSolver.findMinimal(input);


		assertEquals(expected, output);
	}

	@Test
	public void checkAnotherMinimal1() {
		Set<FunctionalDependency> input = set(
				new FunctionalDependency(set("A"), set("B", "C")),
				new FunctionalDependency(set("A", "B", "C"), set("D")));

		Set<FunctionalDependency> expected = set(
				new FunctionalDependency(set("A"), set("B")),
				new FunctionalDependency(set("A"), set("C")),
				new FunctionalDependency(set("B", "C"), set("D")));

		Set<FunctionalDependency> output = minimalBasisSolver.findMinimal(input);


		assertEquals(expected, output);
	}

	@Test
	public void checkAnotherMinimal2() {
		Set<FunctionalDependency> input = set(
				new FunctionalDependency(set("A"), set("B", "C")),
				new FunctionalDependency(set("A", "B", "C"), set("D")),
				new FunctionalDependency(set("A"), set("D")));

		Set<FunctionalDependency> expected = set(
				new FunctionalDependency(set("A"), set("B")),
				new FunctionalDependency(set("A"), set("C")),
				new FunctionalDependency(set("B", "C"), set("D")));

		Set<FunctionalDependency> output = minimalBasisSolver.findMinimal(input);

		assertEquals(expected, output);
	}

	@Test
	public void checkAnotherMinimal3() {
		Set<FunctionalDependency> input = set(
				new FunctionalDependency(set("A"), set("B", "C")),
				new FunctionalDependency(set("A", "B", "C"), set("D")),
				new FunctionalDependency(set("A"), set("D")),
				new FunctionalDependency(set("B", "C"), set("D")));

		Set<FunctionalDependency> expected = set(
				new FunctionalDependency(set("A"), set("B")),
				new FunctionalDependency(set("A"), set("C")),
				new FunctionalDependency(set("B", "C"), set("D")));

		Set<FunctionalDependency> output = minimalBasisSolver.findMinimal(input);

		assertEquals(expected, output);
	}

	@Test
	public void checkAnotherMinimal4() {
		Set<FunctionalDependency> input = set(
				new FunctionalDependency(set("A"), set("B", "C")),
				new FunctionalDependency(set("A"), set("D")),
				new FunctionalDependency(set("A", "B", "C"), set("D")));

		Set<FunctionalDependency> expected = set(
				new FunctionalDependency(set("A"), set("B")),
				new FunctionalDependency(set("A"), set("C")),
				new FunctionalDependency(set("B", "C"), set("D")));

		Set<FunctionalDependency> output = minimalBasisSolver.findMinimal(input);

		assertEquals(expected, output);
	}

}
