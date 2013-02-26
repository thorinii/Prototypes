/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer.tree;

import java.util.Arrays;

/**
 *
 * @author lachlan
 */
public class Tree extends Node {

	public enum Position {

		UPPER_LEFT, UPPER_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
	}

	private final Node[] kids;

	public Tree() {
		kids = new Node[4];
	}

	public void put(Position position, Node node) {
		switch (position) {
			case UPPER_LEFT:
				kids[0] = node;
				break;
			case UPPER_RIGHT:
				kids[1] = node;
				break;
			case BOTTOM_LEFT:
				kids[2] = node;
				break;
			case BOTTOM_RIGHT:
				kids[3] = node;
				break;
		}
	}

	public Node[] getChildren() {
		return Arrays.copyOf(kids, 4);
	}

}
