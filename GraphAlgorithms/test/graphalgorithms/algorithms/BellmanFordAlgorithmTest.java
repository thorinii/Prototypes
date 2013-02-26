/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.algorithms;

import graphalgorithms.graph.*;
import java.util.Map;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author lachlan
 */
public class BellmanFordAlgorithmTest {
	
	@Test
	public void testTrivialPath() {
		Graph graph = new Graph();

		Node<String> nA = new Node<>("A");
		Node<String> nB = new Node<>("B");
		Node<String> nC = new Node<>("C");

		nA.connectTo(nB, 1);
		nA.connectTo(nC, 4);

		graph.addNode(nA);
		graph.addNode(nB);
		graph.addNode(nC);


		LeastCostAlgorithm alg = new BellmanFordAlgorithm(graph);

		Map<Node, Path<String>> paths = alg.calculatePaths(nA);

		assertTrue(paths.containsKey(nB));
		assertTrue(paths.containsKey(nC));
		assertEquals(1, paths.get(nB).getCost(), 0);
		assertEquals(4, paths.get(nC).getCost(), 0);
	}

	@Test
	public void testBasicPath() {
		Graph graph = new Graph();

		Node<String> nA = new Node<>("A");
		Node<String> nB = new Node<>("B");
		Node<String> nC = new Node<>("C");

		nA.connectTo(nB, 1);
		nA.connectTo(nC, 4);
		nB.connectTo(nC, 2);

		graph.addNode(nA);
		graph.addNode(nB);
		graph.addNode(nC);


		LeastCostAlgorithm alg = new BellmanFordAlgorithm(graph);

		Map<Node, Path<String>> paths = alg.calculatePaths(nA);

		assertTrue(paths.containsKey(nB));
		assertTrue(paths.containsKey(nC));
		assertEquals(1, paths.get(nB).getCost(), 0);
		assertEquals(3, paths.get(nC).getCost(), 0);
	}

	@Test
	public void testComplexPath() {
		Graph graph = new Graph();

		Node<String> nA = new Node<>("A");
		Node<String> nB = new Node<>("B");
		Node<String> nC = new Node<>("C");
		Node<String> nD = new Node<>("D");
		Node<String> nE = new Node<>("E");
		Node<String> nF = new Node<>("F");
		Node<String> nG = new Node<>("G");
		Node<String> nH = new Node<>("H");

		nA.connectTo(nB, 1);
		nA.connectTo(nC, 4);

		nB.connectTo(nC, 2);
		nB.connectTo(nD, 9);
		nB.connectTo(nG, 4);
		nB.connectTo(nH, 2);

		nC.connectTo(nD, 1);
		nC.connectTo(nE, 3);

		nD.connectTo(nE, 1);
		nD.connectTo(nF, 3);
		nD.connectTo(nG, 1);

		nE.connectTo(nF, 1);

		nF.connectTo(nG, 6);

		nG.connectTo(nH, 14);


		graph.addNode(nA);
		graph.addNode(nB);
		graph.addNode(nC);
		graph.addNode(nD);
		graph.addNode(nE);
		graph.addNode(nF);
		graph.addNode(nG);
		graph.addNode(nH);


		LeastCostAlgorithm alg = new BellmanFordAlgorithm(graph);
		Map<Node, Path<String>> paths = alg.calculatePaths(nA);


		assertEquals(1, paths.get(nB).getCost(), 0);
		assertEquals(3, paths.get(nC).getCost(), 0);
		assertEquals(4, paths.get(nD).getCost(), 0);
		assertEquals(5, paths.get(nE).getCost(), 0);
		assertEquals(6, paths.get(nF).getCost(), 0);
		assertEquals(5, paths.get(nG).getCost(), 0);
		assertEquals(3, paths.get(nH).getCost(), 0);
	}

	@Test
	public void testCustom() {
		/**
		 * Shaped like:
		 *
		 * ____9 __A -- B 1 |____| 1 __D -- C ____1
		 */
		Graph graph = new Graph();

		Node<String> nA = new Node<>("A");
		Node<String> nB = new Node<>("B");
		Node<String> nC = new Node<>("C");
		Node<String> nD = new Node<>("D");

		nA.connectTo(nB, 9);
		nB.connectTo(nC, 1);
		nC.connectTo(nD, 1);
		nD.connectTo(nA, 1);

		graph.addNode(nA);
		graph.addNode(nB);
		graph.addNode(nC);
		graph.addNode(nD);


		LeastCostAlgorithm alg = new BellmanFordAlgorithm(graph);

		Map<Node, Path<String>> paths = alg.calculatePaths(nA);

		assertTrue(paths.containsKey(nB));
		assertTrue(paths.containsKey(nC));
		assertEquals(3, paths.get(nB).getCost(), 0);
	}

	@Test
	public void testAnother() {
		Graph graph = new Graph();

		Node<String> nA = new Node<>("A");
		Node<String> nB = new Node<>("B");
		Node<String> nC = new Node<>("C");
		Node<String> nD = new Node<>("D");
		Node<String> nE = new Node<>("E");
		Node<String> nF = new Node<>("F");
		Node<String> nG = new Node<>("G");

		nA.connectTo(nB, 20);
		nA.connectTo(nD, 16);
		nA.connectTo(nF, 1);
		nA.connectTo(nC, 12);

		nB.connectTo(nC, 18);
		nB.connectTo(nD, 1);
		nB.connectTo(nF, 6);

		nC.connectTo(nD, 16);
		nC.connectTo(nE, 18);
		nC.connectTo(nF, 20);
		nC.connectTo(nG, 14);

		nD.connectTo(nG, 9);

		nE.connectTo(nF, 5);
		nE.connectTo(nG, 10);

		graph.addNode(nA);
		graph.addNode(nB);
		graph.addNode(nC);
		graph.addNode(nD);
		graph.addNode(nE);
		graph.addNode(nF);
		graph.addNode(nG);


		LeastCostAlgorithm alg = new BellmanFordAlgorithm(graph);

		Map<Node, Path<String>> paths = alg.calculatePaths(nG);

		assertEquals(16, paths.get(nA).getCost(), 0);
		assertEquals(10, paths.get(nB).getCost(), 0);
		assertEquals(14, paths.get(nC).getCost(), 0);
		assertEquals(9, paths.get(nD).getCost(), 0);
		assertEquals(10, paths.get(nE).getCost(), 0);
		assertEquals(15, paths.get(nF).getCost(), 0);
	}

}
