/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fluid2d;

import javax.swing.JFrame;

/**
 *
 * @author lachlan
 */
public class Fluid2D {

    public static final int B = -10;
    public static final int MAX_PRESSURE = 20;
    public static final int MAX_SINGLE_PRESSURE = 40;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Simulation sim = new NavierStokesSimulation(20, 20);

        sim.addDevice(new Pump(Where.UP_LEFT, 2));
        sim.addDevice(new Pump(Where.UP_RIGHT, 2));
        sim.addDevice(new Pump(Where.DOWN_RIGHT, 4));

        sim.addDevice(new Outlet(Where.DOWN_LEFT, 8));


        JFrame jf = new JFrame();
        jf.setSize(sim.getWidth() * 25, sim.getHeight() * 25);
        jf.add(new View(sim));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

        for (int i = 0;; i++) {
            jf.repaint();

            sim.update(0.01f);

            Thread.sleep(800);
        }
    }
}
