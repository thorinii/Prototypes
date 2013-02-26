/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.algorithms;

import graphalgorithms.graph.Node;
import graphalgorithms.graph.Path;

/**
 *
 * @author lachlan
 */
public interface LeastCostAlgorithmListener<T extends Comparable<T>> {

	public void selectNode(Node<T> node);

	public void hardenNode(Node<T> node);

	public void selectReCostNode(Node<T> node);

	public void newCostNode(Node<T> node, Path path);
	
	public void deselectReCostNode(Node<T> node);
}
