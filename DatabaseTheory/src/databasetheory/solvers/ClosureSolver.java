/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasetheory.solvers;

import databasetheory.model.FunctionalDependency;
import java.util.*;

/**
 *
 * @author lachlan
 */
public class ClosureSolver {

	private Collection<FunctionalDependency> fds;

	public ClosureSolver(FunctionalDependency... fds) {
		this.fds = Arrays.asList(fds);
	}

	public ClosureSolver(Collection<FunctionalDependency> fds) {
		this.fds = fds;
	}

	/**
	 * Computes a closure from the specified starting attributes.
	 *
	 * @param starting
	 * @return
	 */
	public Set<String> closure(String... starting) {
		return closure(new TreeSet(Arrays.asList(starting)));
	}

	/**
	 * Computes a closure from the specified starting attributes.
	 *
	 * @param starting
	 * @return
	 */
	public Set<String> closure(Set<String> starting) {
		Set<String> result = new TreeSet<>();

		result.addAll(starting);

		boolean changed;

		do {
			changed = false;

			for (FunctionalDependency fd : fds) {
				if (result.containsAll(fd.getLHS())) {
					changed = (result.addAll(fd.getRHS())) ? true : changed;
				}
			}
		} while (changed);

		return result;
	}

	public Set<String> allAttributesAvailable() {
		Set<String> result = new TreeSet<>();

		for (FunctionalDependency fd : fds) {
			result.addAll(fd.getLHS());
			result.addAll(fd.getRHS());
		}

		return result;
	}

	public Set<String> allLHSAttributesAvailable() {
		Set<String> result = new TreeSet<>();

		for (FunctionalDependency fd : fds) {
			result.addAll(fd.getLHS());
		}

		return result;
	}

}