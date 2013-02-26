/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.table;

/**
 *
 * @author lachlan
 */
public abstract class TableRenderer {
	private final Table<?> table;

	public TableRenderer(Table<?> table) {
		this.table = table;
	}

	public Table<?> getTable() {
		return table;
	}
	
	public abstract void renderTable();
}
