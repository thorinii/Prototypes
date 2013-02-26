/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asciiartanimator.model;

/**
 *
 * @author lachlan
 */
public class TextSection {

	private String text;
	private Colour fColour;
	private Colour bColour;

	public TextSection() {
		this("", Colour.White, Colour.Black);
	}

	public TextSection(String text) {
		this(text, Colour.White, Colour.Black);
	}

	public TextSection(TextSection copy) {
		this(copy.text, copy.fColour, copy.bColour);
	}

	public TextSection(String text, TextSection copy) {
		this(text, copy.fColour, copy.bColour);
	}

	public TextSection(String text, Colour fColour, Colour bColour) {
		this.text = text;
		this.fColour = fColour;
		this.bColour = bColour;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Colour getForeColour() {
		return fColour;
	}

	public void setForeColour(Colour fColour) {
		this.fColour = fColour;
	}

	public Colour getBackColour() {
		return bColour;
	}

	public void setBackColour(Colour bColour) {
		this.bColour = bColour;
	}

	public String toString() {
		return "[" + fColour + ";" + bColour + ";" + text + "]";
	}

}
