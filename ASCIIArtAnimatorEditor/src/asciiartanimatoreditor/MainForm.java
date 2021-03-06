/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asciiartanimatoreditor;

import asciiartanimator.model.*;
import asciiartanimator.storage.Loader;
import asciiartanimator.storage.Storer;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author lachlan
 */
public class MainForm extends javax.swing.JFrame {

	private Animation animation;
	private int currentFrame;
	private JFileChooser jfc;
	private AnimationSettingsDialog animationSettings;

	/**
	 * Creates new form MainForm
	 */
	public MainForm() {
		initComponents();

		jfc = new JFileChooser();
		animationSettings = new AnimationSettingsDialog(this);

		newAnimation();
	}

	private void updateAnimation() {
		frameCountLbl.setText("of " + animation.getFrameCount());

		currentFrame = Math.max(0, Math.min(animation.getFrameCount() - 1,
				currentFrame));
		currentFrameTxt.setText("" + (currentFrame + 1));

		frameEditor1.setFrame(animation.getFrame(currentFrame));

		if (currentFrame >= (animation.getFrameCount() - 1)) {
			nextFrameBtn.setEnabled(false);
			lastFrameBtn.setEnabled(false);
		} else {
			nextFrameBtn.setEnabled(true);
			lastFrameBtn.setEnabled(true);
		}

		if (currentFrame <= 0) {
			prevFrameBtn.setEnabled(false);
			firstFrameBtn.setEnabled(false);
		} else {
			prevFrameBtn.setEnabled(true);
			firstFrameBtn.setEnabled(true);
		}

		if (animation.getFrameCount() <= 1) {
			removeFrameBtn.setEnabled(false);
		} else {
			removeFrameBtn.setEnabled(true);
		}
	}

	public void newAnimation() {
		animation = new Animation(new LinkedList<Frame>(Collections.singleton(
				new Frame())), "Untitled Animation", 20);
		updateAnimation();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        frameEditor1 = new asciiartanimatoreditor.FrameEditor();
        prevFrameBtn = new javax.swing.JButton();
        addFrameBtn = new javax.swing.JButton();
        removeFrameBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        currentFrameTxt = new javax.swing.JTextField();
        frameCountLbl = new javax.swing.JLabel();
        nextFrameBtn = new javax.swing.JButton();
        copyFrameBtn = new javax.swing.JButton();
        firstFrameBtn = new javax.swing.JButton();
        lastFrameBtn = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newMnuItm = new javax.swing.JMenuItem();
        openMnuItm = new javax.swing.JMenuItem();
        saveMnuItm = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        exitMnuItm = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        animationSettingsMnuItm = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ASCII Art Animator - Editor");
        setMinimumSize(new java.awt.Dimension(561, 285));

        frameEditor1.setForeground(new java.awt.Color(254, 254, 254));

        prevFrameBtn.setText("< Previous");
        prevFrameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevFrameBtnActionPerformed(evt);
            }
        });

        addFrameBtn.setText("Add Frame");
        addFrameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFrameBtnActionPerformed(evt);
            }
        });

        removeFrameBtn.setText("Remove Frame");
        removeFrameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFrameBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Frame");

        currentFrameTxt.setColumns(5);
        currentFrameTxt.setText("0");
        currentFrameTxt.setToolTipText("");

        frameCountLbl.setText("jLabel2");

        nextFrameBtn.setText("Next >");
        nextFrameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextFrameBtnActionPerformed(evt);
            }
        });

        copyFrameBtn.setText("Add Copy Frame");
        copyFrameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyFrameBtnActionPerformed(evt);
            }
        });

        firstFrameBtn.setText("<< First");
        firstFrameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstFrameBtnActionPerformed(evt);
            }
        });

        lastFrameBtn.setText("Last >>");
        lastFrameBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastFrameBtnActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        newMnuItm.setText("New");
        newMnuItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMnuItmActionPerformed(evt);
            }
        });
        jMenu1.add(newMnuItm);

        openMnuItm.setText("Open");
        openMnuItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMnuItmActionPerformed(evt);
            }
        });
        jMenu1.add(openMnuItm);

        saveMnuItm.setText("Save");
        saveMnuItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMnuItmActionPerformed(evt);
            }
        });
        jMenu1.add(saveMnuItm);
        jMenu1.add(jSeparator3);

        exitMnuItm.setText("Exit");
        exitMnuItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMnuItmActionPerformed(evt);
            }
        });
        jMenu1.add(exitMnuItm);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        animationSettingsMnuItm.setText("Animation Settings");
        animationSettingsMnuItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animationSettingsMnuItmActionPerformed(evt);
            }
        });
        jMenu2.add(animationSettingsMnuItm);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(frameEditor1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addFrameBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(copyFrameBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeFrameBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currentFrameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(frameCountLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(firstFrameBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(prevFrameBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextFrameBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lastFrameBtn)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(frameEditor1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prevFrameBtn)
                    .addComponent(jLabel1)
                    .addComponent(currentFrameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(frameCountLbl)
                    .addComponent(nextFrameBtn)
                    .addComponent(firstFrameBtn)
                    .addComponent(lastFrameBtn))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addFrameBtn)
                    .addComponent(removeFrameBtn)
                    .addComponent(copyFrameBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void prevFrameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevFrameBtnActionPerformed
		if (currentFrame > 0) {
			currentFrame--;
		}
		updateAnimation();
	}//GEN-LAST:event_prevFrameBtnActionPerformed

	private void nextFrameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextFrameBtnActionPerformed
		if (currentFrame < (animation.getFrameCount() - 1)) {
			currentFrame++;
		}
		updateAnimation();
	}//GEN-LAST:event_nextFrameBtnActionPerformed

	private void addFrameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFrameBtnActionPerformed
		animation.getFrames().add(currentFrame + 1, new Frame());

		updateAnimation();
	}//GEN-LAST:event_addFrameBtnActionPerformed

	private void removeFrameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFrameBtnActionPerformed
		if (animation.getFrameCount() <= 1) {
			return;
		}

		animation.getFrames().remove(currentFrame);

		updateAnimation();
	}//GEN-LAST:event_removeFrameBtnActionPerformed

	private void exitMnuItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMnuItmActionPerformed
		System.exit(0);
	}//GEN-LAST:event_exitMnuItmActionPerformed

	private void newMnuItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMnuItmActionPerformed
		newAnimation();
	}//GEN-LAST:event_newMnuItmActionPerformed

	private void openMnuItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMnuItmActionPerformed
		Loader loader = new Loader();

		jfc.setFileFilter(new FileNameExtensionFilter(
				"ASCII Art Animator animations", "asciiaa"));

		if (jfc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		String file = jfc.getSelectedFile().getAbsolutePath();

		try {
			animation = loader.loadAnimation(file);
			updateAnimation();
		} catch (Exception ioe) {
			ioe.printStackTrace();
			JOptionPane.showMessageDialog(this, ioe,
					"ASCII Art Animator - Editor", JOptionPane.ERROR_MESSAGE);
		}
	}//GEN-LAST:event_openMnuItmActionPerformed

	private void saveMnuItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMnuItmActionPerformed
		Storer storer = new Storer();

		jfc.setFileFilter(new FileNameExtensionFilter(
				"ASCII Art Animator animations", "asciiaa"));

		if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		String file = jfc.getSelectedFile().getAbsolutePath();

		if (!file.endsWith(".asciiaa")) {
			file = file + ".asciiaa";
		}

		try {
			storer.storeAnimation(animation, file);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}//GEN-LAST:event_saveMnuItmActionPerformed

	private void copyFrameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyFrameBtnActionPerformed
		animation.getFrames().add(currentFrame + 1, new Frame(frameEditor1.
				getFrame()));
		currentFrame++;
		updateAnimation();
	}//GEN-LAST:event_copyFrameBtnActionPerformed

	private void animationSettingsMnuItmActionPerformed(//GEN-FIRST:event_animationSettingsMnuItmActionPerformed
			java.awt.event.ActionEvent evt) {//GEN-HEADEREND:event_animationSettingsMnuItmActionPerformed
		animationSettings.setAnimation(animation);
		animationSettings.setVisible(true);
	}//GEN-LAST:event_animationSettingsMnuItmActionPerformed

	private void firstFrameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstFrameBtnActionPerformed
		currentFrame = 0;
		updateAnimation();
	}//GEN-LAST:event_firstFrameBtnActionPerformed

	private void lastFrameBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastFrameBtnActionPerformed
		currentFrame = animation.getFrameCount() - 1;
		updateAnimation();
	}//GEN-LAST:event_lastFrameBtnActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/*
		 * Set the Nimbus look and feel
		 */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.
					getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new MainForm().setVisible(true);
			}

		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFrameBtn;
    private javax.swing.JMenuItem animationSettingsMnuItm;
    private javax.swing.JButton copyFrameBtn;
    private javax.swing.JTextField currentFrameTxt;
    private javax.swing.JMenuItem exitMnuItm;
    private javax.swing.JButton firstFrameBtn;
    private javax.swing.JLabel frameCountLbl;
    private asciiartanimatoreditor.FrameEditor frameEditor1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JButton lastFrameBtn;
    private javax.swing.JMenuItem newMnuItm;
    private javax.swing.JButton nextFrameBtn;
    private javax.swing.JMenuItem openMnuItm;
    private javax.swing.JButton prevFrameBtn;
    private javax.swing.JButton removeFrameBtn;
    private javax.swing.JMenuItem saveMnuItm;
    // End of variables declaration//GEN-END:variables
}
