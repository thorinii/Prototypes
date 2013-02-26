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
public class BellmanFordTable<T extends Comparable<T>> extends Table<T> {

	private final Node<T> base;

	public BellmanFordTable(Graph<T> graph, Node<T> base) {
		super(graph);
		this.base = base;
	}

	@Override
	public void generate() {
		defineColumns();

		Set<Node<T>> nodes = getGraph().getNodes();
		Map<Node<T>, Path<T>>[] hpaths = new Map[nodes.size()];


		initialise(hpaths, nodes, base);


		for (int h = 0; h < hpaths.length - 1; h++) {
			update(nodes, base, hpaths, h);

			calculateRow(h, hpaths);
		}
	}

	private void defineColumns() {
		List<String> cols = new ArrayList<>();

		cols.add("h");

		for (Node<T> n : getGraph().getNodes()) {
			if (n == base) {
				continue;
			}

			cols.add("L(" + n.getBody() + ")");
			cols.add("Path " + n.getBody());
		}

		setColumns(cols);
	}

	private void calculateRow(int h,
			Map<Node<T>, Path<T>>[] hpaths) {
		List<String> row = new ArrayList<>();

		row.add("" + h);

		for (Node<T> n : getGraph().getNodes()) {
			if (n == base) {
				continue;
			}

			Path p = hpaths[h].get(n);

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

	private void initialise(Map<Node<T>, Path<T>>[] hpaths,
			Set<Node<T>> nodes, Node base) {
		for (int i = 0; i < hpaths.length; i++) {
			hpaths[i] = new HashMap<>();

			for (Node<T> n : nodes) {
				hpaths[i].put(n, Path.infinitePath(base, n));
			}

			hpaths[i].put(base, new Path(base));
		}
	}

	private void update(Set<Node<T>> nodes, Node base,
			Map<Node<T>, Path<T>>[] hpaths, int h) {
		for (Node<T> n : nodes) {
			if (n == base) {
				continue;
			}

			Path current = hpaths[h].get(n);
			Path shortest = null;

			for (Node<T> j : n.getAdjacentEnabledNodes()) {
				Path jpath = hpaths[h].get(j);


				if (shortest == null || shortest.getCost() > jpath.getCost()
						+ w(j, n)) {
					shortest = jpath.cpy().appendNode(n);
				}
			}

			if (shortest.getCost() < current.getCost()) {
				hpaths[h + 1].put(n, shortest);
			} else {
				hpaths[h + 1].put(n, current);
			}
		}
	}

	private double w(Node i, Node j) {
		return (i.isConnectedTo(j)) ? i.getCostTo(j) : Double.POSITIVE_INFINITY;
	}

}
