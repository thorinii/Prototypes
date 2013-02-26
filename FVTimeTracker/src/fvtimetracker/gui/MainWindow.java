/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.gui;

import fvtimetracker.Main;
import fvtimetracker.logic.ApplicationManager;

/**
 *
 * @author lachlan
 */
public class MainWindow extends javax.swing.JFrame {

	private ApplicationManager applicationManager;

	/**
	 * Creates new form MainWindow
	 */
	public MainWindow() {
		this(null);
	}

	public MainWindow(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;

		initComponents();
	}

	@Override
	public void setVisible(boolean b) {
		if (b == true) {
			setLocation(Main.PREF_ROOT.getInt("mainwindow-x", 0),
					Main.PREF_ROOT.getInt("mainwindow-y", 0));

			hoursPerTaskDisplay1.reset();
			monthTaskDisplay1.reset();
			monthChartDisplay1.reset();
			taskWeekChartDisplay1.reset();
			editingDisplay1.reset();
			
			updateState();
		} else {
			Main.PREF_ROOT.putInt("mainwindow-x", getX());
			Main.PREF_ROOT.putInt("mainwindow-y", getY());
		}

		super.setVisible(b);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timeTrackingTgl = new javax.swing.JToggleButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        hoursPerTaskDisplay1 = new HoursPerTaskDisplay(applicationManager.getDatabase());
        jPanel2 = new javax.swing.JPanel();
        monthTaskDisplay1 = new MonthTaskDisplay(applicationManager.getDatabase());
        monthChartDisplay1 = new MonthChartDisplay(applicationManager.getDatabase());
        taskWeekChartDisplay1 = new TaskWeekChartDisplay(applicationManager.getDatabase());
        jPanel3 = new javax.swing.JPanel();
        editingDisplay1 = new EditingDisplay(applicationManager.getDatabase());
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        settingsBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();

        setTitle("Flipside Virtual Time Tracker");

        timeTrackingTgl.setText("Toggle Time Tracking");
        timeTrackingTgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeTrackingTglActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout hoursPerTaskDisplay1Layout = new javax.swing.GroupLayout(hoursPerTaskDisplay1);
        hoursPerTaskDisplay1.setLayout(hoursPerTaskDisplay1Layout);
        hoursPerTaskDisplay1Layout.setHorizontalGroup(
            hoursPerTaskDisplay1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 545, Short.MAX_VALUE)
        );
        hoursPerTaskDisplay1Layout.setVerticalGroup(
            hoursPerTaskDisplay1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 292, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hoursPerTaskDisplay1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hoursPerTaskDisplay1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Hours per Task", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(monthTaskDisplay1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(monthTaskDisplay1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("Month Tasks", jPanel2);
        jTabbedPane1.addTab("Month Chart", monthChartDisplay1);
        jTabbedPane1.addTab("Task Per Week", taskWeekChartDisplay1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(editingDisplay1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(editingDisplay1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("Editing", jPanel3);

        jLabel1.setFont(jLabel1.getFont().deriveFont((float)24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("In Progress...");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Export", jPanel4);

        settingsBtn.setText("Settings...");
        settingsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsBtnActionPerformed(evt);
            }
        });

        updateBtn.setText("Update");
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
                    .addComponent(timeTrackingTgl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(updateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(settingsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timeTrackingTgl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(settingsBtn)
                    .addComponent(updateBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void timeTrackingTglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeTrackingTglActionPerformed
		if (applicationManager.isTracking()) {
			applicationManager.pauseTiming();
		} else {
			applicationManager.startTiming();
		}

		updateState();
	}//GEN-LAST:event_timeTrackingTglActionPerformed

	private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
		updateState();
	}//GEN-LAST:event_updateBtnActionPerformed

	private void settingsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsBtnActionPerformed
		SettingsDialog settingsDialog = new SettingsDialog(this);
		settingsDialog.setVisible(true);
	}//GEN-LAST:event_settingsBtnActionPerformed

	public void updateState() {
		timeTrackingTgl.setSelected(applicationManager.isTracking());

		hoursPerTaskDisplay1.updateState();
		monthTaskDisplay1.updateState();
		monthChartDisplay1.updateState();
		taskWeekChartDisplay1.updateState();
		editingDisplay1.updateState();
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private fvtimetracker.gui.EditingDisplay editingDisplay1;
    private fvtimetracker.gui.HoursPerTaskDisplay hoursPerTaskDisplay1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private fvtimetracker.gui.MonthChartDisplay monthChartDisplay1;
    private fvtimetracker.gui.MonthTaskDisplay monthTaskDisplay1;
    private javax.swing.JButton settingsBtn;
    private fvtimetracker.gui.TaskWeekChartDisplay taskWeekChartDisplay1;
    private javax.swing.JToggleButton timeTrackingTgl;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}