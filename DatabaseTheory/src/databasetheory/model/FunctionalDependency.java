/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasetheory.model;

import java.util.*;

/**
 * An FD
 *
 * @author lachlan
 */
public class FunctionalDependency implements Comparable {

	private SortedSet<String> lhs;
	private SortedSet<String> rhs;

	public FunctionalDependency(String[] lhs, String[] rhs) {
		if (lhs.length == 0 || rhs.length == 0) {
			throw new IllegalArgumentException("Cannot be null FD");
		}

		this.lhs = new TreeSet(Arrays.asList(lhs));
		this.rhs = new TreeSet(Arrays.asList(rhs));
	}

	public FunctionalDependency(Set<String> lhs, Set<String> rhs) {
		if (lhs.isEmpty() || rhs.isEmpty()) {
			throw new IllegalArgumentException("Cannot be null FD");
		}

		this.lhs = new TreeSet(lhs);
		this.rhs = new TreeSet(rhs);
	}

	private FunctionalDependency(SortedSet<String> lhs, SortedSet<String> rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	/**
	 * Calculates the simplest form of this FD. May return null if this is
	 * completely trivial.
	 *
	 * @return a simpler FD, or null
	 */
	public FunctionalDependency simplify() {
		TreeSet newRHS = new TreeSet(rhs);
		newRHS.removeAll(lhs);

		if (newRHS.isEmpty()) {
			return null;
		}

		return new FunctionalDependency(lhs, newRHS);
	}

	public Set<String> getLHS() {
		return Collections.unmodifiableSortedSet(lhs);
	}

	public Set<String> getRHS() {
		return Collections.unmodifiableSortedSet(rhs);
	}

	@Override
	public String toString() {
		String fdString = "";

		int i = 0;
		for (String lhsStr : lhs) {
			if (i > 0) {
				fdString += ", ";
			}
			fdString += lhsStr;
			i++;
		}

		fdString += " -> ";

		i = 0;
		for (String rhsStr : rhs) {
			if (i > 0) {
				fdString += ", ";
			}
			fdString += rhsStr;
			i++;
		}

		return "{" + fdString + "}";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FunctionalDependency) {
			FunctionalDependency o = (FunctionalDependency) obj;

			return lhs.equals(o.lhs) && rhs.equals(o.rhs);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 29 * hash + Objects.hashCode(this.lhs);
		hash = 29 * hash + Objects.hashCode(this.rhs);
		return hash;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof FunctionalDependency) {
			FunctionalDependency other = (FunctionalDependency) o;

			if (lhs.first().compareTo(other.lhs.first()) == 0) {
				if (lhs.size() == other.lhs.size()) {
					if (rhs.first().compareTo(other.rhs.first()) == 0) {
						if (rhs.equals(other.rhs)) {
							return 0;
						} else {
							return rhs.size() - other.rhs.size();
						}
					} else {
						return rhs.first().compareTo(other.rhs.first());
					}
				} else {
					return lhs.size() - other.lhs.size();
				}
			} else {
				return lhs.first().compareTo(other.lhs.first());
			}
		} else {
			return -1;
		}
	}

}
