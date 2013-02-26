/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asciiartanimatoreditor;

import asciiartanimator.model.Colour;
import java.awt.Color;

/**
 *
 * @author lachlan
 */
public class ColourConverter {

	public static Color fromColour(Colour colour) {
		switch (colour) {
			case Black:
				return Color.BLACK;
			case Blue:
				return Color.BLUE;
			case Cyan:
				return Color.CYAN;
			case Green:
				return Color.GREEN;
			case Purple:
				return Color.MAGENTA;
			case Red:
				return Color.RED;
			case White:
				return Color.WHITE;
			case Yellow:
				return Color.YELLOW;
		}
		return null;
	}

}
