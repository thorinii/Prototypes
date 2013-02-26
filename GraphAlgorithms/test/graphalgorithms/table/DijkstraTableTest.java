/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.table;

import graphalgorithms.algorithms.DijkstraAlgorithm;
import graphalgorithms.algorithms.LeastCostAlgorithm;
import graphalgorithms.graph.*;
import java.util.List;
import java.util.Map;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author lachlan
 */
public class DijkstraTableTest {

	@Test
	public void testBasic() {
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


		DijkstraTable<String> table = new DijkstraTable<>(graph, nA);
		table.generate();

		System.out.println(table.toString());
	}

	@Test
	public void testComplex() {
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


		DijkstraTable<String> table = new DijkstraTable<>(graph, nA);
		table.generate();

		System.out.println(table.toString());
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


		DijkstraTable<String> table = new DijkstraTable<>(graph, nG);
		table.generate();

		System.out.println(table.toString());
	}

}
