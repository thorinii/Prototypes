/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.table;

import graphalgorithms.algorithms.DijkstraAlgorithm;
import graphalgorithms.algorithms.LeastCostAlgorithmListener;
import graphalgorithms.graph.*;
import java.util.*;

/**
 *
 * @author lachlan
 */
public class DijkstraTable<T extends Comparable<T>> extends Table<T> {

	private final Node<T> base;

	public DijkstraTable(Graph<T> graph, Node<T> base) {
		super(graph);
		this.base = base;
	}

	@Override
	public void generate() {
		defineColumns();

		Map<Node<T>, Path<T>> paths = new HashMap<>();
		Set<Node<T>> nodes = getGraph().getNodes();
		Set<Node<T>> t = new TreeSet<>();


		initialise(t, base, nodes, paths);
		calculateRow(1, t, paths);


		int iteration = 1;
		while (!t.containsAll(nodes)) {
			Node<T> x = getNextNode(nodes, t, paths);
			Path<T> xPath = paths.get(x);
			t.add(x);

			updatePaths(nodes, x, paths, xPath);

			iteration++;
			calculateRow(iteration, t, paths);
		}
	}

	private void calculateRow(int iteration,
			Set<Node<T>> t,
			Map<Node<T>, Path<T>> paths) {
		List<String> row = new ArrayList<>();

		row.add("" + iteration);
		row.add(t.toString());

		for (Node<T> n : getGraph().getNodes()) {
			if (n == base) {
				continue;
			}

			Path p = paths.get(n);

			if (p.getCost() == Double.POSITIVE_INFINITY) {
				row.add("inf");
				row.add("-");
			} else {
				row.add("" + p.getCost());
				row.add(p.toString());
			}
		}

		appendRow(row);
	}

	private void defineColumns() {
		List<String> cols = new ArrayList<>();

		cols.add("Itr");
		cols.add("T");

		for (Node<T> n : getGraph().getNodes()) {
			if (n == base) {
				continue;
			}

			cols.add("L(" + n.getBody() + ")");
			cols.add("Path " + n.getBody());
		}

		setColumns(cols);
	}

	private void initialise(Set<Node<T>> t, Node<T> base, Set<Node<T>> nodes,
			Map<Node<T>, Path<T>> paths) {
		t.add(base);

		for (Node n : nodes) {
			paths.put(n, Path.autoPath(base, n));
		}
	}

	private Node getNextNode(Set<Node<T>> nodes,
			Set<Node<T>> t,
			Map<Node<T>, Path<T>> paths) {
		Node x = null;
		Path shortest = null;
		for (Node n : nodes) {
			if (t.contains(n)) {
				continue;
			}

			Path currentPath = paths.get(n);
			if (shortest == null || currentPath.getCost() < shortest.getCost()) {
				shortest = currentPath;
				x = n;
			}
		}
		return x;
	}

	private void updatePaths(Set<Node<T>> nodes, Node<T> x,
			Map<Node<T>, Path<T>> paths, Path xPath) {
		for (Node n : nodes) {
			if (!x.isConnectedTo(n)) {
				continue;
			}

			Path original = paths.get(n);

			if (xPath.getCost() + w(x, n) < original.getCost()) {
				Path newPath = xPath.cpy().appendNode(n);
				paths.put(n, newPath);
			}
		}
	}

	private double w(Node<T> i, Node<T> j) {
		return (i.isConnectedTo(j)) ? i.getCostTo(j) : Double.POSITIVE_INFINITY;
	}

}
