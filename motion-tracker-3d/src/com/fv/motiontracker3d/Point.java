/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fv.motiontracker3d;

/**
 *
 * @author lachlan
 */
public class Point {

	private double x;
	private double y;

	public Point() {
		this(0, 0);
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	protected Point clone() {
		return new Point(x, y);
	}

}
