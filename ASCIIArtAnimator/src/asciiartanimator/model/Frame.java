/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asciiartanimator.model;

import java.util.LinkedList;

/**
 *
 * @author lachlan
 */
public class Frame {

	public static final int ROWS = 30;
	private TextSection text;

	public Frame() {
		this(new TextSection());
	}

	public Frame(Frame copy) {
		this.text = new TextSection(copy.text);
	}

	public Frame(TextSection text) {
		this.text = text;
	}

	public TextSection getText() {
		return text;
	}

	public void setText(TextSection text) {
		this.text = text;
	}

	public String toString() {
		return text.getText();
	}

}
