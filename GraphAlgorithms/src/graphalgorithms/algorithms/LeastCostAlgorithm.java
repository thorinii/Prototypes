/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.algorithms;

import graphalgorithms.graph.Node;
import graphalgorithms.graph.Path;
import java.util.Map;

/**
 *
 * @author lachlan
 */
public interface LeastCostAlgorithm<T extends Comparable<T>> {

	public Map<Node<T>, Path<T>> calculatePaths(Node<T> base);

}
