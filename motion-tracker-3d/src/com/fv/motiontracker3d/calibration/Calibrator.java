/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fv.motiontracker3d.calibration;

import com.fv.motiontracker3d.Point;

/**
 *
 * @author lachlan
 */
public interface Calibrator {
	public void initialize(Point[][] data);
	public double[] getAngleBetween();
	public double[] getDistanceFromOrigin();
}
