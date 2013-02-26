/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fluid2d;

import java.util.List;

/**
 *
 * @author lachlan
 */
public class NavierStokesSimulation extends AbstractSimulation {

    private static final float DIFFUSION = 50f;
    private final float[][] densityMap;
    private final float[][] velocityMap;

    public NavierStokesSimulation(int width, int height) {
        densityMap = new float[height][width];
        velocityMap = new float[height][width];
    }

    @Override
    public float[][] getDensityMap() {
        return densityMap;
    }

    @Override
    protected void simulate(float timestep) {
        diffuse(timestep);
        velocity(timestep);
    }

    private void diffuse(float dt) {
        float value;
        float diff;
        float change;

        for (int y = 1; y < getHeight() - 1; y++) {
            for (int x = 1; x < getWidth() - 1; x++) {
                value = densityMap[y][x];

                diff = value - densityMap[y][x - 1];
                change = diff * dt * DIFFUSION;
                value -= change;
                densityMap[y][x - 1] += change;

                diff = value - densityMap[y][x + 1];
                change = diff * dt * DIFFUSION;
                value -= change;
                densityMap[y][x + 1] += change;

                diff = value - densityMap[y - 1][x];
                change = diff * dt * DIFFUSION;
                value -= change;
                densityMap[y - 1][x] += change;

                diff = value - densityMap[y + 1][x];
                change = diff * dt * DIFFUSION;
                value -= change;
                densityMap[y + 1][x] += change;

                densityMap[y][x] = value;
            }
        }
    }

    private void velocity(float dt) {
        float pressure;
        float vel;

        for (int y = 1; y < getHeight() - 1; y++) {
            for (int x = 1; x < getWidth() - 1; x++) {
                pressure = densityMap[y][x];
                vel = velocityMap[y][x];
                
                
            }
        }
    }
}
