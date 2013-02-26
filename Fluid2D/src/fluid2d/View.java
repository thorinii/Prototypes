/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fluid2d;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author lachlan
 */
public class View extends JPanel {

    private final Simulation sim;

    public View(Simulation sim) {
        this.sim = sim;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        float[][] data = sim.getDensityMap();

        int dw = data[0].length;
        int dh = data.length;

        int width = getWidth() / dw;
        int height = getHeight() / dh;

        for (int y = 0; y < dh; y++) {
            for (int x = 0; x < dw; x++) {
                int av = (int) data[y][x];
                int v = Math.max(Math.min(40, av), 0);
                Color c;

                if (v == Fluid2D.B) {
                    c = Color.BLACK;
                } else if (v == 0) {
                    c = Color.WHITE;
                } else if (v < 21) {
                    c = new Color(200 - v * 10, 200 - v * 10, 255);
                } else if (v < 40) {
                    c = new Color((v - 21) * 10, 0, 255 - (v - 21) * 10);
                } else {
                    c = new Color(255, 0, 0);
                }

                g.setColor(c);
                g.fillRect(x * width, y * height, width, height);

                g.setColor(Color.BLACK);
                g.drawString("" + av, x * width + 5, y * height + 15);
            }
        }
    }
}
