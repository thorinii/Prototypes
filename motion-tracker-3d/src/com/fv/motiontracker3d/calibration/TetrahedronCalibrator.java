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
public class TetrahedronCalibrator implements Calibrator {

	@Override
	public void initialize(Point[][] data) {
	}

	@Override
	public double[] getAngleBetween() {
		return new double[]{30, 30};
	}

	@Override
	public double[] getDistanceFromOrigin() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
