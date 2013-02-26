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
public class BasicSimulation extends AbstractSimulation {

    private final int[][] densityMap;

    public BasicSimulation(int width, int height) {
        densityMap = new int[height][width];
    }

    @Override
    protected void simulate(float timestep) {
        for (int y = densityMap.length - 1; y >= 0; y--) {
            for (int x = 0; x < densityMap[y].length; x++) {
                int value = densityMap[y][x];

                if (value == B) {
                    boolean u, d, l, r;
                    int up, dp, lp, rp;

                    u = d = l = r = false;
                    up = dp = lp = rp = 0;

                    if (y < densityMap.length - 1) {
                        d = densityMap[y + 1][x] != B;
                        dp = densityMap[y + 1][x];
                    }
                    if (y > 0) {
                        u = densityMap[y - 1][x] != B;
                        up = densityMap[y - 1][x];
                    }

                    if (x < densityMap[0].length - 1) {
                        r = densityMap[y][x + 1] != B;
                        rp = densityMap[y][x + 1];
                    }
                    if (x > 0) {
                        l = densityMap[y][x - 1] != B;
                        lp = densityMap[y][x - 1];
                    }

                    if (u && d) {
                        if (Math.abs(up - dp) > MAX_PRESSURE) {
                            value = 0;
                        }
                    }
                    if (l && r) {
                        if (Math.abs(lp - rp) > MAX_PRESSURE) {
                            value = 0;
                        }
                    }

                    if (u && up > MAX_SINGLE_PRESSURE) {
                        value = 0;
                    }
                    if (d && dp > MAX_SINGLE_PRESSURE) {
                        value = 0;
                    }
                    if (l && lp > MAX_SINGLE_PRESSURE) {
                        value = 0;
                    }
                    if (r && rp > MAX_SINGLE_PRESSURE) {
                        value = 0;
                    }
                } else if (value > 0) {
                    // Down
                    if (y < densityMap.length - 1 && densityMap[y + 1][x] != B) {
                        int diff = value - densityMap[y + 1][x];
                        if (diff > 2) {
                            value -= diff / 2;
                            densityMap[y + 1][x] += diff / 2;
                        } else if (diff >= 0) {
                            value--;
                            densityMap[y + 1][x]++;
                        }
                    }

                    // Left
                    if (x < densityMap[y].length - 1 && densityMap[y][x + 1]
                            != B) {
                        int diff = value - densityMap[y][x + 1];
                        if (diff > 0) {
                            value -= diff / 2;
                            densityMap[y][x + 1] += diff / 2;
                        }
                    }

                    // Right
                    if (x > 0 && densityMap[y][x - 1] != B) {
                        int diff = value - densityMap[y][x - 1];
                        if (diff > 0) {
                            value -= diff / 2;
                            densityMap[y][x - 1] += diff / 2;
                        }
                    }

                    // Up
                    if (y > 0 && densityMap[y - 1][x] != B) {
                        int diff = value - densityMap[y - 1][x] + 1;
                        if (value > densityMap[y - 1][x] + 1) {
                            value--;
                            densityMap[y - 1][x]++;
                        }
                    }
                }

                densityMap[y][x] = value;
            }
        }
    }

    @Override
    public int[][] getDensityMap() {
        return densityMap;
    }
}
