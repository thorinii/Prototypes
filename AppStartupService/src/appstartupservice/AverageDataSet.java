/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appstartupservice;

/**
 *
 * @author lachlan
 */
public class AverageDataSet {

	private final int samples;
	private double total;
	private int sampleCount;

	public AverageDataSet(int sampleCount) {
		this.sampleCount = sampleCount;
		this.samples = 0;
		this.total = 0;
	}

	public void addSample(double sample) {
		if (sampleCount < samples) {
			total += sample;
			sampleCount++;
		} else {
			total -= total / sampleCount;
			total += sample;
		}
	}

	public double getAverage() {
		return total / sampleCount;
	}

}
