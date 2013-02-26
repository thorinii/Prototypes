/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asciiartanimator.console;

import asciiartanimator.model.*;

/**
 *
 * @author lachlan
 */
public class ConsoleRenderer {

	public ConsoleRenderer() {
	}

	public void printFrame(Frame frame) {
		StringBuilder builder = new StringBuilder();

		eraseScreen(builder);

		// Set colour
		printColour(builder, frame.getText().getForeColour(),
				frame.getText().getBackColour());

		// Print section text
		builder.append(frame.getText().getText());

		// Reset colour
		builder.append("\u001B[m");

		System.out.println(builder);
		System.out.flush();
	}

	private void printColour(StringBuilder builder, Colour fore, Colour back) {
		if (fore == Colour.None || back == Colour.None) {
			builder.append("\u001B[m");
		} else if (back != Colour.Black) {
			builder.append("\u001B[").append(30 + fore.getCode()).append(";").
					append(40 + back.getCode()).append("m");
		} else {
			builder.append("\u001B[").append(30 + fore.getCode()).append("m");
			builder.append("\u001B[49m");
		}
	}

	public void eraseScreen(StringBuilder builder) {
		builder.append("\n\u001B[2J\u001B[1;1H");
	}


	public void eraseScreen() {
		System.out.print("\n\u001B[2J\u001B[1;1H");
		System.out.flush();
	}
}
