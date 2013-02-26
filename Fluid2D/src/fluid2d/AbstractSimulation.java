/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fluid2d;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lachlan
 */
public abstract class AbstractSimulation implements Simulation {

    private final List<Device> devices;

    public AbstractSimulation() {
        devices = new ArrayList<>();
    }

    @Override
    public void addDevice(Device device) {
        devices.add(device);
    }

    @Override
    public int getHeight() {
        return getDensityMap().length;
    }

    @Override
    public int getWidth() {
        return getDensityMap()[0].length;
    }

    @Override
    public void update(float timestep) {
        simulate(timestep);
        updateDevices();
    }

    protected void updateDevices() {
        for (Device d : devices) {
            d.update(this);
        }
    }

    @Override
    public abstract float[][] getDensityMap();

    protected abstract void simulate(float timestep);
}
