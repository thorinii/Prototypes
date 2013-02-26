/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import raytracer.tree.*;

/**
 *
 * @author lachlan
 */
public class RenderView extends JPanel {

	private static final Color MOUSE_HOVER = new Color(100, 100, 100,
			100);
	private static final Color[] COLOURS = {Color.RED, Color.YELLOW, Color.BLUE,
		Color.RED, Color.YELLOW, Color.BLUE, Color.RED, Color.YELLOW, Color.BLUE,
		Color.RED, Color.YELLOW, Color.BLUE, Color.RED, Color.YELLOW, Color.BLUE};
	private Node data = makeFractal(2);
	private int mouseDepth = 1;

	/**
	 * Creates new form RenderView
	 */
	public RenderView() {
		MouseAdapter ma = new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				repaint();
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getPreciseWheelRotation() > 0) {
					mouseDepth = Math.max(mouseDepth - 1, 1);
				} else {
					mouseDepth++;
				}
				data = makeFractal(mouseDepth);
				repaint();
			}

		};
		addMouseMotionListener(ma);
		addMouseWheelListener(ma);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		renderTree(g, data);

		Point p = getMousePosition();
		if (p != null) {
			double pow = Math.pow(2, mouseDepth);

			double gW = getWidth() / pow;
			double gH = getHeight() / pow;

			p.x = (int) (((int) (p.x / gW)) * gW);
			p.y = (int) (((int) (p.y / gH)) * gH);

			g.setColor(MOUSE_HOVER);
			g.fillRect(p.x, p.y, getWidth() / (int) pow, getHeight() / (int) pow);
		}
	}

	private void renderTree(Graphics g, Node n) {
		renderTree(g, n, 0, 0, getWidth(), getHeight(), 0);
	}

	private void renderTree(Graphics g, Node n, float x, float y, float width,
			float height, int depth) {
		if (n == null) {
//			g.setColor(COLOURS[depth]);
//			g.fillRect((int) x, (int) y, (int) width, (int) height);
		} else if (n instanceof Leaf) {
			if (width < 0.5 || height < 0.5) {
				return;
			} else {
				width = Math.max(width, 1);
				height = Math.max(height, 1);
			}

			g.setColor(Color.RED);
			g.fillRect((int) x, (int) y, (int) width, (int) height);
		} else {
			Node[] kids = ((Tree) n).getChildren();
			float hW = Math.max(width / 2, 1);
			float hH = Math.max(height / 2, 1);

			renderTree(g, kids[0], x, y, hW, hH, depth + 1);
			renderTree(g, kids[1], x + hW, y, hW, hH, depth + 1);
			renderTree(g, kids[2], x, y + hH, hW, hH, depth + 1);
			renderTree(g, kids[3], x + hW, y + hH, hW, hH, depth + 1);
		}
	}

	private Node makeFractal(int depth) {
		if (depth <= 0) {
			return new Leaf();
		}

		Tree t = new Tree();

		t.put(Tree.Position.UPPER_LEFT, makeFractal(depth - 1));
		t.put(Tree.Position.UPPER_RIGHT, makeFractal(depth - 1));
		t.put(Tree.Position.BOTTOM_LEFT, makeFractal(depth - 1));

		return t;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
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
