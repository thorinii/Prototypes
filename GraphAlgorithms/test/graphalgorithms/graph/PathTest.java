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
public class PathTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNoDisconnectedNodes() {
		Node<String> n1, n2, n3;
		n1 = new Node<>("N1");
		n2 = new Node<>("N2");
		n3 = new Node<>("N3");

		n1.connectTo(n2, 3);
		n2.connectTo(n3, 4);


		Path<String> p = new Path<>();
		p.appendNode(n1);

		// Throw Exception here:
		p.appendNode(n3);
	}
	
	public void testCalculatesCost(){
		Node<String> n1, n2, n3;
		n1 = new Node<>("N1");
		n2 = new Node<>("N2");
		n3 = new Node<>("N3");

		n1.connectTo(n2, 3);
		n2.connectTo(n3, 4);
		
		Path<String> p = new Path<>();
		
		p.appendNode(n1);
		p.appendNode(n2);
		
		assertEquals(p.getCost(), 3, 0);
		
		p.appendNode(n3);
		assertEquals(p.getCost(), 7, 0);
	}

}
