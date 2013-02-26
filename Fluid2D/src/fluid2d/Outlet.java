/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fluid2d;

/**
 *
 * @author lachlan
 */
public class Outlet implements Device {

    private final Where where;
    private final int rate;

    public Outlet(Where where, int rate) {
        this.where = where;
        this.rate = rate;
    }

    @Override
    public void update(Simulation sim) {
        float[][] density = sim.getDensityMap();
        int width = density[0].length;
        int height = density.length;
        int x, y;

        switch (where) {
            case UP_LEFT:
                x = 0;
                y = 0;
                break;
            case UP_RIGHT:
                x = width - 1;
                y = 0;
                break;
            case DOWN_LEFT:
                x = 0;
                y = height - 1;
                break;
            case DOWN_RIGHT:
                x = width - 1;
                y = height - 1;
                break;
            default:
                x = 0;
                y = 0;
        }

        density[y][x] = Math.max(0, density[y][x] - rate);
    }
}
