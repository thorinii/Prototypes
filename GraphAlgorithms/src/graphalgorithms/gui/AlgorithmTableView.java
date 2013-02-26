/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.gui;

import graphalgorithms.table.Table;
import graphalgorithms.table.TableRenderer;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lachlan
 */
public class AlgorithmTableView extends JPanel {

	private TablePacker packer;

	/**
	 * Creates new form AlgorithmTableView
	 */
	public AlgorithmTableView() {
		packer = new TablePacker(TablePacker.ALL_ROWS, true);
		initComponents();
	}

	public void setTable(Table t) {
		TableRenderer renderer = new Renderer(t);
		renderer.renderTable();
	}

	@Override
	public void repaint() {
		super.repaint();

		if (algorithmTbl != null && algorithmTbl.isShowing()) {
			packer.pack(algorithmTbl);
		}
	}

	private class Renderer extends TableRenderer {

		public Renderer(Table table) {
			super(table);
		}

		@Override
		public void renderTable() {
			Table t = getTable();
			t.generate();

			DefaultTableModel rows = new DefaultTableModel();

			for (Iterator<String> it = t.getColumns().iterator(); it.hasNext();) {
				String col = it.next();
				rows.addColumn(col);
			}
			for (Iterator<List<String>> it = t.getRows().iterator(); it.hasNext();) {
				List<String> row = it.next();

				rows.addRow(row.toArray());
			}

			algorithmTbl.setModel(rows);
			repaint();
		}

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        algorithmTbl = new javax.swing.JTable();

        algorithmTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        algorithmTbl.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        algorithmTbl.setMinimumSize(new java.awt.Dimension(300, 72));
        jScrollPane2.setViewportView(algorithmTbl);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable algorithmTbl;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
