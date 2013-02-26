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
public class Graph<T extends Comparable<T>> {

	private final Set<Node<T>> nodes;

	public Graph() {
		nodes = new TreeSet<>();
	}

	public Set<Node<T>> getNodes() {
		return Collections.unmodifiableSet(nodes);
	}

	public void addNode(Node<T> node) {
		nodes.add(node);
	}

	public void removeNode(Node<T> node) {
		nodes.remove(node);
	}

}
