/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.graph;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author lachlan
 */
public class NodeTest {

	@Test
	public void testNoConnection() {
		Node<String> node = new Node<>("Node 1");
		
		assertEquals(node.getBody(), "Node 1");
		
		assertEquals(node.getAdjacentNodes().size(), 0);
	}
	
	@Test
	public void testSingleConnection(){
		Node<String> n1, n2;
		
		n1 = new Node<>("N1");
		n2 = new Node<>("N2");
		
		n1.connectTo(n2, 4);
		
		assertTrue(n1.isConnectedTo(n2));
		assertTrue(n2.isConnectedTo(n1));
	}
	
	@Test
	public void testNodeBus(){
		/**
		 * 3 Nodes, connected as in:
		 * 
		 * N1 --- N2 --- N3
		 */
		
		Node<String> n1, n2, n3;
		
		n1 = new Node<>("N1");
		n2 = new Node<>("N2");
		n3 = new Node<>("N3");
		
		n1.connectTo(n2, 3);
		n2.connectTo(n3, 4);
		
		assertTrue(n1.isConnectedTo(n2));
		assertTrue(n2.isConnectedTo(n1));
		assertTrue(n2.isConnectedTo(n3));
		assertTrue(n3.isConnectedTo(n2));
		
		assertFalse(n1.isConnectedTo(n3));
		assertFalse(n3.isConnectedTo(n1));
	}
	
}
