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
public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

	private T body;
	private final Map<Node<T>, Edge<T>> nodeToEdges;
	private final Set<Edge<T>> edges;

	public Node() {
		this(null);
	}

	public Node(T body) {
		this.body = body;
		this.nodeToEdges = new HashMap<>();
		this.edges = new HashSet<>();
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	@Override
	public int hashCode() {
		return body.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Node<T> other = (Node<T>) obj;
		if (!Objects.equals(this.body, other.body)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Node<T> t) {
		return body.compareTo(t.body);
	}

	public Set<Edge<T>> getEdges() {
		return Collections.unmodifiableSet(edges);
	}

	public boolean isConnectedTo(Node other) {
		return nodeToEdges.containsKey(other);
	}

	public Edge getEdgeTo(Node other) {
		return nodeToEdges.get(other);
	}

	public double getCostTo(Node other) {
		if (isConnectedTo(other)) {
			return nodeToEdges.get(other).getCost();
		} else {
			return Double.POSITIVE_INFINITY;
		}
	}

	public Set<Node<T>> getAdjacentNodes() {
		return Collections.unmodifiableSet(nodeToEdges.keySet());
	}

	public Set<Node<T>> getAdjacentEnabledNodes() {
		Set<Node<T>> enabledNodes = new TreeSet<>();

		for (Map.Entry<Node<T>, Edge<T>> entry : nodeToEdges.entrySet()) {
			if (entry.getValue().isActive()) {
				enabledNodes.add(entry.getKey());
			}
		}

		return Collections.unmodifiableSet(enabledNodes);
	}

	public void connectTo(Node<T> other, double cost) {
		Edge edge;

		if (isConnectedTo(other)) {
			edge = nodeToEdges.get(other);
			edge.setCost(cost);
		} else {
			edge = new Edge(cost, this, other);
			connectUp(other, edge);
			other.connectUp(this, edge);
		}

	}

	private void connectUp(Node other, Edge edge) {
		nodeToEdges.put(other, edge);
		edges.add(edge);
	}

	@Override
	public String toString() {
		return "(" + body + ")";
	}

	public static class Edge<T extends Comparable<T>> {

		private double cost;
		private final Node<T> n1;
		private final Node<T> n2;
		private boolean active;

		public Edge(double cost, Node<T> n1, Node<T> n2) {
			this.cost = cost;
			this.n1 = n1;
			this.n2 = n2;
			this.active = true;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Edge other = (Edge) obj;
			if (!Objects.equals(this.n1, other.n1)) {
				return false;
			}
			if (!Objects.equals(this.n2, other.n2)) {
				return false;
			}
			return true;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 89 * hash + Objects.hashCode(this.n1);
			hash = 89 * hash + Objects.hashCode(this.n2);
			return hash;
		}

		public double getCost() {
			return cost;
		}

		public Node<T>[] getNodes() {
			return new Node[]{n1, n2};
		}

		public Node<T> getOther(Node one) {
			if (n1 == one) {
				return n2;
			} else {
				return n1;
			}
		}

		public void setCost(double cost) {
			this.cost = cost;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

	}

}
