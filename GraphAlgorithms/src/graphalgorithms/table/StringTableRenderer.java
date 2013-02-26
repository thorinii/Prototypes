/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.table;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author lachlan
 */
public class StringTableRenderer extends TableRenderer {

	private String cache;

	public StringTableRenderer(Table table) {
		super(table);
	}

	@Override
	public void renderTable() {
		StringBuilder builder = new StringBuilder();

		// find best colsize

		int[] colWidths = new int[getTable().getColumns().size()];
		int i = 0;
		for (Object col : getTable().getColumns()) {
			colWidths[i] = Math.max(colWidths[i], col.toString().length());

			i++;
		}
		for (Iterator<List<String>> it = getTable().getRows().iterator(); it.
				hasNext();) {
			List<String> row = it.next();
			i = 0;
			for (String col : row) {
				colWidths[i] = Math.max(colWidths[i], col.length());

				i++;
			}
		}


		// Print table

		i = 0;
		builder.append("| ");
		for (Object col : getTable().getColumns()) {
			builder.append(String.format("%-" + colWidths[i] + "s",
					col.toString()));
			builder.append(" | ");
			i++;
		}


		for (Iterator<List<String>> it = getTable().getRows().iterator(); it.
				hasNext();) {
			List<String> row = it.next();
			i = 0;
			builder.append("\n| ");
			for (String col : row) {
				builder.append(String.format("%-" + colWidths[i] + "s", col));
				builder.append(" | ");
				i++;
			}
		}

		cache = builder.toString();
	}

	@Override
	public String toString() {
		return cache;
	}

}
