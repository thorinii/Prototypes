/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphalgorithms.gui;

import graphalgorithms.graph.Graph;
import graphalgorithms.graph.Node;
import graphalgorithms.graph.Node.Edge;
import java.awt.Color;
import java.awt.Graphics;
import java.security.SecureRandom;
import java.util.*;
import javax.swing.JPanel;

/**
 *
 * @author lachlan
 */
public class GraphView extends JPanel {

	private static final int NODE_RADIUS = 7;
	private Graph graph;
	private Map<Node, NodeView> nodes;
	private Random random;
	private Thread simulate;
	private boolean keepCentred;
	private NodeView selected;

	/**
	 * Creates new form GraphView
	 */
	public GraphView() {
		this(null);
	}

	public GraphView(Graph graph) {
		initComponents();
		random = new SecureRandom();

		this.graph = graph;
		keepCentred = true;
		makeGraph();
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
		makeGraph();
		repaint();
	}

	public Graph getGraph() {
		return graph;
	}

	public void randomise() {
		if (simulate != null) {
			simulate.interrupt();
		}
		for (NodeView n : nodes.values()) {
			n.randomise();
		}

		makeGraph();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (graph != null && nodes == null) {
			makeGraph();
		}

		drawGraph(g);
	}

	private void makeGraph() {
		if (graph == null) {
			return;
		}
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}

		nodes = new HashMap<>();

		for (Iterator<Node> it = graph.getNodes().iterator(); it.hasNext();) {
			Node n = it.next();
			NodeView v = new NodeView(n);
			v.randomise();
			nodes.put(n, v);
		}

		if (simulate != null) {
			simulate.interrupt();
		}
		simulate = new Thread(new Runnable() {

			public void run() {
				try {
					simulateGraph();
					repaint();

					simulate = null;
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}

		}, "simulation");
		simulate.start();
	}

	@SuppressWarnings("SleepWhileInLoop")
	private void simulateGraph() throws InterruptedException {
		long start = System.currentTimeMillis();

		do {
			for (int i = 0; i < 100; i++) {
				for (NodeView n : nodes.values()) {
					n.solveConstraints();
				}
			}

			for (NodeView n : nodes.values()) {
				n.update();
			}
		} while (!Thread.currentThread().isInterrupted() && (System.
				currentTimeMillis() - start) < 100);
	}

	private void centreGraph() {
		if (!keepCentred) {
			return;
		}

		int cx = 0;
		int cy = 0;

		for (NodeView n : nodes.values()) {
			cx += n.x;
			cy += n.y;
		}

		cx /= nodes.size();
		cy /= nodes.size();

		int dx = getWidth() / 2 - cx;
		int dy = getHeight() / 2 - cy;

		for (NodeView n : nodes.values()) {
			n.x += dx;
			n.y += dy;
		}
	}

	private void drawGraph(Graphics g) {
		if (graph != null && nodes != null) {
			centreGraph();

			for (NodeView n : nodes.values()) {
				if (selected == n) {
					g.setColor(Color.CYAN);
				} else {
					g.setColor(Color.BLACK);
				}

				g.drawOval(n.x - NODE_RADIUS, n.y - NODE_RADIUS, NODE_RADIUS * 2,
						NODE_RADIUS
						* 2);

				for (Iterator<Edge> it = n.node.getEdges().iterator(); it.
						hasNext();) {
					Edge e = it.next();
					NodeView cn = nodes.get(e.getOther(n.node));

					g.setColor(Color.BLACK);
					g.drawLine(n.x, n.y, cn.x, cn.y);

					String cost = String.valueOf(e.getCost());

					g.setColor(Color.MAGENTA);
					g.drawString(cost, (n.x + cn.x) / 2, (n.y + cn.y)
							/ 2);
				}

				g.setColor(Color.RED);
				g.drawString(n.node.toString(), n.x, n.y);
			}
		}
	}

	@Override
	public void validate() {
		super.validate();
	}

	private class NodeView {

		final Node node;
		final int m;
		volatile int x, y;
		int lx, ly;
		boolean pin;

		public NodeView(Node node) {
			this.node = node;
			m = (int) node.getAdjacentEnabledNodes().size();

			x = y = 0;
			lx = ly = 0;
		}

		void randomise() {
			x = lx = random.nextInt(getWidth());
			y = ly = random.nextInt(getHeight());
		}

		void update() {
			if (pin) {
				x = getWidth() / 2;
				y = getHeight() / 2;
				return;
			}

			float dx = x - lx;
			float dy = y - ly;

			dx *= 0.99;
			dy *= 0.99;

			float nx = x + dx;
			float ny = y + dy;

			lx = x;
			ly = y;

			x = (int) nx;
			y = (int) ny;
		}

		void solveConstraints() {
			for (NodeView n : nodes.values()) {
				if (node.isConnectedTo(n.node)) {
					solve(n);
				}
			}

			if (x < 1) {
				x = 2 - x;
			} else if (x >= getWidth()) {
				x = 2 * (getWidth() - 1) - x;
			}
			if (y < 1) {
				y = 2 - y;
			} else if (y >= getHeight()) {
				y = 2 * (getHeight() - 1) - y;
			}

			if (pin) {
				x = getWidth() / 2;
				y = getHeight() / 2;
			}
		}

		void solve(NodeView o) {
			float resting = (float) Math.min(20 * node.getCostTo(o.node), 300);

			float diffx = x - o.x;
			float diffy = y - o.y;

			float d = (float) Math.sqrt(diffx * diffx + diffy * diffy);

			float difference = (resting - d) / d;

			float transx = diffx * difference * 0.9f;
			float transy = diffy * difference * 0.9f;

			int mt = m + o.m;

			x += transx * m / mt;
			y += transy * m / mt;
			o.x -= transx * o.m / mt;
			o.y -= transy * o.m / mt;
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

        setBackground(java.awt.Color.white);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });

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

	private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
		keepCentred = false;

		for (NodeView n : nodes.values()) {
			int dx = n.x - evt.getX();
			int dy = n.y - evt.getY();

			if (Math.abs(dx) < 10 && Math.abs(dy) < 10) {
				selected = n;
				break;
			}
		}
		
		repaint();
	}//GEN-LAST:event_formMousePressed

	private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
		keepCentred = true;
		selected = null;
		
		repaint();
	}//GEN-LAST:event_formMouseReleased

	private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
		keepCentred = false;
		if (selected == null) {
			return;
		}
		
		selected.x = evt.getX();
		selected.y = evt.getY();

		repaint();
	}//GEN-LAST:event_formMouseDragged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
