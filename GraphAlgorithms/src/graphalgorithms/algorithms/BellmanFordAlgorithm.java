/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.algorithms;

import graphalgorithms.graph.*;
import java.util.*;

/**
 *
 * @author lachlan
 */
public class BellmanFordAlgorithm<T extends Comparable<T>> implements
		LeastCostAlgorithm<T> {

	private final Graph graph;

	public BellmanFordAlgorithm(Graph graph) {
		this.graph = graph;
	}

	public Graph getGraph() {
		return graph;
	}

	@Override
	public Map<Node<T>, Path<T>> calculatePaths(Node base) {
		Set<Node<T>> nodes = graph.getNodes();
		Map<Node<T>, Path<T>>[] hpaths = new Map[nodes.size()];


		initialise(hpaths, nodes, base);


		for (int h = 0; h < hpaths.length - 1; h++) {
			update(nodes, base, hpaths, h);
		}

		return hpaths[hpaths.length - 1];
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
