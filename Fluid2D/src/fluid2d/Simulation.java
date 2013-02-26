/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fluid2d;

/**
 *
 * @author lachlan
 */
public interface Simulation {

    public static final int B = -10;
    public static final int MAX_PRESSURE = 20;
    public static final int MAX_SINGLE_PRESSURE = 40;

    public void addDevice(Device device);

    public void update(float timestep);

    public float[][] getDensityMap();

    public int getWidth();

    public int getHeight();
}
