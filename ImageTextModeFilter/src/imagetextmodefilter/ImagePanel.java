/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagetextmodefilter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author lachlan
 */
public class ImagePanel extends JPanel {

	private ImageProcessor processor;

	/**
	 * Creates new form ImagePanel
	 */
	public ImagePanel() {
		processor = new ImageProcessor();
	}

	public void setup() {
		try {
			Image i = ImageIO.read(new File("./lachlan-test-greyscale-cmp.png"));
			processor.setOriginal(i);
		} catch (IOException ex) {
			Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null,
					ex);
			System.exit(1);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(processor.getImage(), 0, 0, null);
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension d = new Dimension();
		Image i = processor.getOriginal();

		if (i == null) {
			setup();
			i = processor.getOriginal();
		}

		d.width = i.getWidth(null);
		d.height = i.getHeight(null);

		return d;
	}

	public void setLevel(int level) {
		processor.setLevel(level);
		repaint();
	}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
