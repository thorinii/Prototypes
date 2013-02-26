/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.graph;

import java.util.*;

/**
 *
 * @author lachlan
 */
public class Path<T extends Comparable<T>> {

	private final List<Node<T>> nodes;

	public Path() {
		nodes = new ArrayList<>();
	}

	public Path(Node... nodes) {
		this();

		for (Node n : nodes) {
			appendNode(n);
		}
	}

	public List<Node<T>> getNodes() {
		return Collections.unmodifiableList(nodes);
	}

	public double getCost() {
		Iterator<Node<T>> itr = nodes.listIterator();
		Node<T> curr;
		Node<T> prev = null;
		double cost = 0;

		while (itr.hasNext()) {
			curr = itr.next();

			if (prev != null) {
				Node.Edge e = curr.getEdgeTo(prev);

				cost += e.getCost();
			}

			prev = curr;
		}

		return cost;
	}

	public Path appendNode(Node<T> node) {
		if (nodes.isEmpty()) {
			nodes.add(node);
		} else {
			Node<T> n = nodes.get(nodes.size() - 1);

			if (n.getEdgeTo(node) == null) {
				throw new IllegalArgumentException(
						"Cannot append disconnected node");
			} else {
				nodes.add(node);
			}
		}

		return this;
	}

	@Override
	public Path clone() {
		return cpy();
	}

	public Path cpy() {
		Path p = new Path();
		p.nodes.addAll(nodes);
		return p;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");

		int i = 0;
		for (Node<T> n : nodes) {
			if (i != 0) {
				builder.append(" -> ");
			}

			builder.append(n.toString());

			i++;
		}

		return builder.append("]").toString();
	}

	public static Path autoPath(Node... nodes) {
		Node last = null;
		boolean connected = true;

		for (Node n : nodes) {
			if (last != null) {
				connected &= last.isConnectedTo(n);
			}
			last = n;
		}

		return (connected) ? new Path(nodes) : new InfinitePath(nodes);
	}

	public static <T extends Comparable<T>> Path<T> infinitePath(Node<T>... n) {
		return new InfinitePath<>(n);
	}

	private static class InfinitePath<T extends Comparable<T>> extends Path<T> {

		public InfinitePath() {
			super();
		}

		public InfinitePath(Node... nodes) {
			this();

			for (Node n : nodes) {
				super.nodes.add(n);
			}
		}

		@Override
		public double getCost() {
			return Double.POSITIVE_INFINITY;
		}

		@Override
		public Path cpy() {
			Path p = new InfinitePath();
			p.nodes.addAll(super.nodes);
			return p;
		}

	}

}
