/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasetheory.solvers;

import databasetheory.model.FunctionalDependency;
import databasetheory.solvers.ClosureSolver;
import databasetheory.solvers.MinimalBasisSolver;
import java.util.*;
import static databasetheory.HelperFeatures.*;

/**
 *
 * @author lachlan
 */
public class PrimaryKeySolver {

	public Set<String> findPrimaryKey(Set<FunctionalDependency> inputFDs) {
		Set<FunctionalDependency> fds = new MinimalBasisSolver().findMinimal(
				inputFDs);
		ClosureSolver closureSolver = new ClosureSolver(fds);
		Set<String> allAttributes = closureSolver.allAttributesAvailable();
		Set<String> allKeyAttributes = closureSolver.allLHSAttributesAvailable();
		Set<String> primaryKey = new TreeSet<>(allKeyAttributes);
			
		Iterator<String> itr = primaryKey.iterator();
		while (itr.hasNext()) {
			String attr = itr.next();

			if (closureSolver.closure(setWithout(primaryKey, attr)).equals(allAttributes)) {
				itr.remove();
			}
		}

		return primaryKey;
	}

}
