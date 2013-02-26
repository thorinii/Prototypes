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
public class DijkstraAlgorithm<T extends Comparable<T>> implements
		LeastCostAlgorithm<T> {

	private final Graph graph;

	public DijkstraAlgorithm(Graph graph) {
		this.graph = graph;
	}

	public Graph getGraph() {
		return graph;
	}

	@Override
	public Map<Node<T>, Path<T>> calculatePaths(Node base) {
		Map<Node<T>, Path<T>> paths = new HashMap<>();
		Set<Node<T>> nodes = graph.getNodes();
		Set<Node<T>> t = new TreeSet<>();


		initialise(t, base, nodes, paths);


		while (!t.containsAll(nodes)) {
			Node x = getNextNode(nodes, t, paths);
			Path xPath = paths.get(x);
			t.add(x);
			
			updatePaths(nodes, x, paths, xPath);
		}


		return paths;
	}

	private void initialise(Set<Node<T>> t, Node base, Set<Node<T>> nodes,
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

	private double w(Node i, Node j) {
		return (i.isConnectedTo(j)) ? i.getCostTo(j) : Double.POSITIVE_INFINITY;
	}

	private Map<Node<T>, Path<T>> calculatePathsOld(Node base,
			LeastCostAlgorithmListener listener) {
		Map<Node<T>, Path<T>> paths = new HashMap<>();
		Set<Node> nodes = graph.getNodes();
		Set<Node> t = new TreeSet<>();

		/**
		 * Initialisation
		 */
		t.add(base);


		/**
		 * Core Algorithm
		 */
		// Start by evaluating the base node
		if (listener != null) {
			listener.selectNode(base);
		}

		for (Node<T> node : nodes) {
			if (listener != null) {
				listener.selectReCostNode(node);
			}

			if (node.isConnectedTo(base)) {
				Path p = new Path().appendNode(base).appendNode(node);
				paths.put(node, p);

				if (listener != null) {
					listener.newCostNode(node, p);
				}
			}

			if (listener != null) {
				listener.deselectReCostNode(node);
			}
		}

		if (listener != null) {
			listener.hardenNode(base);
		}

		// then follow from there

		for (int i = 0; i < nodes.size(); i++) {

			// Find least cost node from 
			Node leastNode = null;
			double leastCost = Double.MAX_VALUE;
			Path<T> leastCostPath = null;
			for (Node<T> current : t) {
				for (Node<T> connected : current.getAdjacentEnabledNodes()) {
					if (t.contains(connected)) {
						continue;
					}

					double cost = connected.getCostTo(current);

					if (leastNode == null || leastCost > cost) {
						leastNode = connected;
						leastCost = cost;

						if (current == base) {
							leastCostPath = new Path<>();
							leastCostPath.appendNode(base).appendNode(connected);
						} else {
							leastCostPath = paths.get(current).cpy();
							leastCostPath.appendNode(connected);
						}
					}
				}
			}

			if (leastNode == null) {
				continue;
			}

			if (listener != null) {
				listener.selectNode(leastNode);
			}

			// Add that node to T
			t.add(leastNode);
			paths.put(leastNode, leastCostPath);


			/**
			 * Recalculate Paths
			 */
			for (Node<T> node : nodes) {
				if (listener != null) {
					listener.selectReCostNode(node);
				}

				Path p = paths.get(node);

				double costFromCurrent = leastCostPath.getCost() + node.
						getCostTo(leastNode);

				if (p != null) {
					if (p.getCost() > costFromCurrent) {
						p = leastCostPath.cpy().appendNode(node);
						paths.put(node, p);

						if (listener != null) {
							listener.newCostNode(node, p);
						}
					}
				} else if (node.isConnectedTo(leastNode)) {
					p = leastCostPath.cpy().appendNode(node);
					paths.put(node, p);

					if (listener != null) {
						listener.newCostNode(node, p);
					}
				}

				if (listener != null) {
					listener.deselectReCostNode(node);
				}
			}


			if (listener != null) {
				listener.hardenNode(leastNode);
			}
		}


		return paths;
	}

}
