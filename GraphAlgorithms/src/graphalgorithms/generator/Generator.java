/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.generator;

import graphalgorithms.graph.Graph;
import graphalgorithms.graph.Node;
import java.security.SecureRandom;
import java.util.*;

/**
 *
 * @author lachlan
 */
public class Generator {

	private final Random r = new SecureRandom(String.valueOf(System.
			currentTimeMillis() ^ 67).getBytes());

	public Graph generate() {
		long start = System.currentTimeMillis();


		Graph graph = new Graph();

		int count = Math.max(r.nextInt(9), 2);
		for (int i = 0; i < count; i++) {
			Node<String> node = new Node<>("" + map(i));
			graph.addNode(node);
		}

		List<Node<String>> nodes = new ArrayList<>(graph.getNodes());
		for (int i = 0; i < nodes.size(); i++) {
			Node<String> n1 = nodes.get(i);

			int connections = Math.max(r.nextInt(3), 2);
			for (int con = 0; con < connections; con++) {
				Node<String> n2;

				do {
					n2 = nodes.get(r.nextInt(nodes.size()));

					if (System.currentTimeMillis() - start > 100) {
						return null;
					}
				} while (n1 == n2 || n1.isConnectedTo(n2));

				n1.connectTo(n2, r.nextInt(20) + 1);
			};
		}

		return graph;
	}

	private char map(int i) {
		char c = (char) i;

		c += 'A';

		return c;
	}

	public Node chooseBase(Graph g) {
		while (true) {
			for (Node n : (Iterable<Node>) g.getNodes()) {
				if (r.nextDouble() < 0.1) {
					return n;
				}
			}
		}
	}

}
