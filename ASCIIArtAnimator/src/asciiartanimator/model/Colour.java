/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asciiartanimator.model;

/**
 *
 * @author lachlan
 */
public enum Colour {

	None(-1),
	Black(0),
	Red(1),
	Green(2),
	Yellow(3),
	Blue(4),
	Purple(5),
	Cyan(6),
	White(7);
	private int ansiSequence;

	private Colour(int sequence) {
		ansiSequence = sequence;
	}

	public int getCode() {
		return ansiSequence;
	}

}
