/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasetheory.solvers;

import static databasetheory.HelperFeatures.*;
import databasetheory.model.FunctionalDependency;
import java.util.*;

/**
 *
 * @author lachlan
 */
public class MinimalBasisSolver {

	public Set<FunctionalDependency> findMinimal(
			Collection<FunctionalDependency> input) {
		List<FunctionalDependency> working = new ArrayList<>(input);

		ListIterator<FunctionalDependency> itr = working.listIterator();
		while (itr.hasNext()) {
			FunctionalDependency fd = itr.next();

			FunctionalDependency simplified = fd.simplify();
			if (simplified == null) {
				itr.remove();
			} else {
				itr.set(simplified);
			}
		}

		working = splitFDs(working);

		boolean changed;
		List<FunctionalDependency> newWorking;

		do {
			changed = false;

			newWorking = removeRedundant(working);
			changed = (!newWorking.equals(working)) ? true : changed;
			working = newWorking;

			newWorking = removeRedundantAttributes(working);
			changed = (!newWorking.equals(working)) ? true : changed;
			working = newWorking;
		} while (changed);

		return new TreeSet(working);
	}

	private List<FunctionalDependency> splitFDs(List<FunctionalDependency> fds) {
		List<FunctionalDependency> split = new ArrayList<>();

		for (FunctionalDependency fd : fds) {
			for (String rhsAttr : fd.getRHS()) {
				FunctionalDependency partFD = new FunctionalDependency(
						fd.getLHS(), set(rhsAttr));
				split.add(partFD);
			}
		}

		return split;
	}

	private List<FunctionalDependency> removeRedundant(
			List<FunctionalDependency> fds) {
		List<FunctionalDependency> clean = new ArrayList<>(fds);

		Iterator<FunctionalDependency> itr = clean.iterator();
		while (itr.hasNext()) {
			FunctionalDependency fd = itr.next();

			ClosureSolver initial = new ClosureSolver(clean);
			Set<String> initialClosure = initial.closure(fd.getLHS());

			ClosureSolver closureSolver = new ClosureSolver(listWithout(clean, fd));
			if (closureSolver.closure(fd.getLHS()).equals(initialClosure)) {
				itr.remove();
			} else {
			}
		}

		return clean;
	}

	private List<FunctionalDependency> removeRedundantAttributes(
			List<FunctionalDependency> fds) {
		List<FunctionalDependency> clean = new ArrayList<>();

		ClosureSolver initial = new ClosureSolver(fds);

		for (FunctionalDependency fd : fds) {
			if (fd.getLHS().size() == 1) {
				clean.add(fd);
				continue;
			}

			Set<String> newLHS = new TreeSet<>(fd.getLHS());
			Set<String> initialClosure = initial.closure(fd.getLHS());

			Iterator<String> itr = newLHS.iterator();
			while (itr.hasNext()) {
				String lhsAttr = itr.next();

				FunctionalDependency newFD = new FunctionalDependency(
						setWithout(newLHS, lhsAttr), fd.getRHS());

				ClosureSolver closureSolver = new ClosureSolver(listReplace(fds,
						fd, newFD));

				Set<String> newClosure = closureSolver.closure(lhsAttr);
				if (newClosure.equals(initialClosure)) {
					itr.remove();
				} else {
				}
			}

			FunctionalDependency newFD = new FunctionalDependency(newLHS, fd.
					getRHS());

			clean.add(newFD);
		}

		return clean;
	}

}
