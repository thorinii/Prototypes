/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasetheory;

import databasetheory.model.FunctionalDependency;
import databasetheory.solvers.*;
import java.util.*;

/**
 *
 * @author lachlan
 */
public class DatabaseTheory {

	private Scanner in;

	public DatabaseTheory() {
		in = new Scanner(System.in);
	}

	public void doMenu() {
		while (true) {
			Set<FunctionalDependency> fds = enterFDs(in);

			System.out.println("You entered:");
			for (FunctionalDependency fd : fds) {
				System.out.println("  " + fd.toString());
			}
			System.out.println();

			while (true) {
				System.out.println();
				printMenu();

				int menuOption = Integer.parseInt(in.nextLine());

				if (menuOption == 4) {
					break;
				} else if (menuOption == 0) {
					return;
				}

				performMenu(menuOption, in, fds);
			}
		}
	}

	private Set<FunctionalDependency> enterFDs(Scanner in) {
		Set<FunctionalDependency> fds = new TreeSet<>();

		String line;

		do {
			System.out.println("Enter a Functional Dependency "
					+ "A[, B, ...] -> C[, D, ...] (blank to finish): ");

			line = in.nextLine();

			if (line.length() == 0) {
				break;
			}

			String[] fdSides = line.split("->");

			FunctionalDependency fd = new FunctionalDependency(readAttributes(
					fdSides[0]), readAttributes(fdSides[1]));
			fds.add(fd);
		} while (line.length() > 0);

		return fds;
	}

	private Set<String> readAttributes(String input) {
		StringTokenizer attributesTok = new StringTokenizer(input, ", ");
		Set<String> attributes = new TreeSet<>();
		while (attributesTok.hasMoreTokens()) {
			String attr = attributesTok.nextToken();
			if (!attr.equals("")) {
				attributes.add(attr);
			}
		}

		return attributes;
	}

	private void printMenu() {
		System.out.println("Choose an operation to perform:");
		System.out.println("1): Perform a Closure");
		System.out.println("2): Find the Minimal Basis");
		System.out.println("3): Find the Primary Key");
		System.out.println("4): Enter a new set of FDs");
		System.out.println("0): Exit");
	}

	private void performMenu(int option, Scanner in,
			Set<FunctionalDependency> fds) {
		switch (option) {
			case 1:
				performClosure(in, fds);
				break;
			case 2:
				findMinimalBasis(in, fds);
				break;
			case 3:
				findPK(in, fds);
				break;

			default:
				System.out.println(option + " is an invalid option. "
						+ "Please try again.");
		}
	}

	private void performClosure(Scanner in, Set<FunctionalDependency> fds) {
		System.out.println("Starting Closure Solver... ");
		ClosureSolver solver = new ClosureSolver(fds);

		System.out.println();

		System.out.print("Please specify a set of attributes"
				+ " to compute the closure of: ");

		Set<String> starting = readAttributes(in.nextLine());
		Set<String> closure = solver.closure(starting);

		System.out.println("{" + setToStr(starting) + "}+ = {" + setToStr(
				closure)
				+ "}");
	}

	private void findMinimalBasis(Scanner in, Set<FunctionalDependency> fds) {
		System.out.println("Starting Minimal Basis Solver... ");
		MinimalBasisSolver solver = new MinimalBasisSolver();

		System.out.println();

		Set<FunctionalDependency> minimalBasis = solver.findMinimal(fds);

		for (FunctionalDependency fd : minimalBasis) {
			System.out.println("  " + fd.toString());
		}
	}

	private void findPK(Scanner in, Set<FunctionalDependency> fds) {
		System.out.println("Starting Primary Key Solver... ");
		PrimaryKeySolver solver = new PrimaryKeySolver();

		System.out.println();

		Set<String> pk = solver.findPrimaryKey(fds);

		System.out.println("Primary Key = {" + setToStr(pk) + "}");
	}

	private String setToStr(Set<String> set) {
		String ret = "";

		int i = 0;
		for (String str : set) {
			if (i != 0) {
				ret += ", ";
			}
			ret += str;
			i++;
		}

		return ret;
	}

	public static void main(String[] args) {
		System.out.println("Welcome to the Database Theory test.");
		System.out.println();
		System.out.println("FDs are of the form:");
		System.out.println(" A, B -> D, E");
		System.out.println(" A,B -> D,E");
		System.out.println(" A B -> D E");
		
		System.out.println();

		DatabaseTheory databaseTheory = new DatabaseTheory();
		databaseTheory.doMenu();
	}

}
