/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.table;

import graphalgorithms.graph.Graph;
import graphalgorithms.graph.Node;
import java.util.*;

/**
 *
 * @author lachlan
 */
public abstract class Table<T extends Comparable<T>> {

	private final Graph<T> graph;
	private final List<String> columns;
	private final List<List<String>> rows;

	public Table(Graph<T> graph) {
		this.graph = graph;
		this.columns = new ArrayList<>();
		this.rows = new ArrayList<>();
	}

	public Graph<T> getGraph() {
		return graph;
	}

	public abstract void generate();

	protected void setColumns(List<String> cols) {
		this.columns.clear();
		this.columns.addAll(Collections.unmodifiableList(cols));
	}

	protected void appendRow(List<String> row) {
		rows.add(row);
	}

	public List<String> getColumns() {
		return Collections.unmodifiableList(columns);
	}

	public List<List<String>> getRows() {
		return Collections.unmodifiableList(rows);
	}

	@Override
	public String toString() {
		TableRenderer renderer = new StringTableRenderer(this);
		renderer.renderTable();
		return renderer.toString();
	}

}
