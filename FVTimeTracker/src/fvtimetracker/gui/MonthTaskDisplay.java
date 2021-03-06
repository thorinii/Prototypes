/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.gui;

import fvtimetracker.data.Datastore;
import java.awt.LayoutManager;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lachlan
 */
public class MonthTaskDisplay extends javax.swing.JPanel {

	private Datastore datastore;
	private NumberFormat hourFormat;
	private boolean layoutAlreadySet;

	public MonthTaskDisplay() {
		this(null);
	}

	public MonthTaskDisplay(Datastore datastore) {
		this.datastore = datastore;
		hourFormat = NumberFormat.getNumberInstance();
		hourFormat.setMinimumFractionDigits(2);
		hourFormat.setMaximumFractionDigits(2);

		initComponents();

		limitStartSpnr.setEditor(new JSpinner.DateEditor(limitStartSpnr,
				"MMM yyyy"));
	}

	public void reset() {
		Calendar startOfMonth = Calendar.getInstance();
		startOfMonth.set(Calendar.MILLISECOND, 0);
		startOfMonth.set(Calendar.SECOND, 0);
		startOfMonth.set(Calendar.MINUTE, 0);
		startOfMonth.set(Calendar.HOUR, 0);
		startOfMonth.set(Calendar.DAY_OF_MONTH, 1);

		((SpinnerDateModel) limitStartSpnr.getModel()).setValue(startOfMonth.
				getTime());
	}

	@Override
	public void setLayout(LayoutManager mgr) {
		if (layoutAlreadySet) {
			return;
		}
		layoutAlreadySet = mgr instanceof GroupLayout;
		super.setLayout(mgr);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        hoursPerTaskTbl = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        limitStartSpnr = new javax.swing.JSpinner();

        hoursPerTaskTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Task", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        hoursPerTaskTbl.setEnabled(false);
        jScrollPane1.setViewportView(hoursPerTaskTbl);

        jLabel2.setText("Month");

        limitStartSpnr.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));
        limitStartSpnr.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                limitStartSpnrStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(limitStartSpnr))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(limitStartSpnr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void limitStartSpnrStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_limitStartSpnrStateChanged
		updateState();
	}//GEN-LAST:event_limitStartSpnrStateChanged

	public void updateState() {
		DefaultTableModel model = ((DefaultTableModel) hoursPerTaskTbl.getModel());
		model.setRowCount(0);

		long start, end;
		start = ((SpinnerDateModel) limitStartSpnr.getModel()).getDate().getTime();

		Calendar endDate = Calendar.getInstance();
		endDate.setTime(((SpinnerDateModel) limitStartSpnr.getModel()).getDate());
		endDate.add(Calendar.MONTH, 1);

		end = endDate.getTimeInMillis();

		List<String[]> tasks = datastore.getTasksTimes(
				start, end);

		if (tasks != null) {
			for (String[] taskTime : tasks) {
				int mins = Integer.parseInt(taskTime[1]);
				float hours = mins / 60f;
				taskTime[1] = hourFormat.format(hours) + "h";
				model.addRow(taskTime);
			}
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable hoursPerTaskTbl;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner limitStartSpnr;
    // End of variables declaration//GEN-END:variables
}
