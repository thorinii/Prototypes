/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fv.motiontracker3d.calibration;

import com.fv.motiontracker3d.Point;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author lachlan
 */
public class TetrahedronCalibratorTest {

	private TetrahedronCalibrator calibrator;

	@Before
	public void setup() throws Exception {
		calibrator = new TetrahedronCalibrator();

		/*
		 * Data taken from  pic1.png and pic2.png in the test-data folder.
		 */
		Point[][] data = new Point[][]{
			{
				new Point(350, 195), // Blue
				new Point(330, 395), // Yellow
				new Point(225, 405), // Green
				new Point(495, 405) // Red
			}, {
				new Point(350, 195), // Blue
				new Point(390, 395), // Yellow
				new Point(200, 400), // Green
				new Point(460, 410) // Red
			}
		};

		calibrator.initialize(data);
	}

	@Test
	public void testGet30Degrees() {
		double[] angle = calibrator.getAngleBetween();

		assertArrayEquals(new double[]{30, 30}, angle, 3);
	}

}
