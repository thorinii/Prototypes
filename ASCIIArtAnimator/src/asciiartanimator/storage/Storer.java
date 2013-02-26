/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asciiartanimator.storage;

import asciiartanimator.model.Animation;
import asciiartanimator.model.Frame;
import asciiartanimator.model.TextSection;
import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author lachlan
 */
public class Storer {

	public void storeAnimation(Animation animation, String filename) throws IOException {
		storeAnimation(animation, new FileOutputStream(filename));
	}

	public void storeAnimation(Animation animation, OutputStream stream) throws IOException {
		PrintWriter out = new PrintWriter(new GZIPOutputStream(stream));

		out.println(Integer.toString(animation.getFps()));
		out.println(Integer.toString(animation.getFrameCount()));
		out.println(animation.getName());
		out.println(Boolean.toString(animation.isRepeating()));
		out.println();

		for (Frame frame : animation.getFrames()) {
			storeFrame(frame, out);
			out.println();
		}

		out.close();
	}

	private void storeFrame(Frame frame, PrintWriter out) {
		String frameText = convertTextSection(frame.getText());

		out.println(frameText.length());
		out.print(frameText);
	}

	private String convertTextSection(TextSection section) {
		return section.getForeColour() + ";" + section.getBackColour()
				+ "!/"
				+ escapeText(section.getText());
	}

	private String escapeText(String text) {
		return text.replace("!", "!!");
	}

}
