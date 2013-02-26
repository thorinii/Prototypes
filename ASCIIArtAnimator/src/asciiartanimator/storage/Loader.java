/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asciiartanimator.storage;

import asciiartanimator.model.*;
import java.io.*;
import java.nio.CharBuffer;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

/**
 *
 * @author lachlan
 */
public class Loader {

	public Animation loadAnimation(String filename) throws IOException {
		return loadAnimation(new FileInputStream(filename));
	}

	public Animation loadAnimation(InputStream stream) throws IOException {
		int fps, frameCount;
		String animationName;
		boolean repeating;
		LinkedList<Frame> frames = new LinkedList<Frame>();

		BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
				stream)));

		fps = Integer.parseInt(in.readLine());
		frameCount = Integer.parseInt(in.readLine());
		animationName = in.readLine();
		repeating = Boolean.parseBoolean(in.readLine());
		in.readLine();

		for (int i = 0; i < frameCount; i++) {
			frames.add(readFrame(in));
			in.readLine();
		}

		in.close();

		Animation animation = new Animation(frames, animationName, fps, repeating);

		return animation;
	}

	private Frame readFrame(BufferedReader in) throws IOException {
		int length = Integer.parseInt(in.readLine());

		char[] buffer = new char[length];
		int readLength = in.read(buffer, 0, length);

		String frameText = new String(buffer).trim();

		Frame frame = new Frame(parseTextSection(frameText));

		return frame;
	}

	private TextSection parseTextSection(String section) {
		try {
			if(section.startsWith("!-")){
				section = section.substring(2);
			}
			
			String[] split = section.split("!/");

			String colours[] = split[0].split(";");

			String text;
			if (split.length == 2) {
				text = split[1].replace("!!", "!");
			} else {
				text = "";
			}

			Colour fore = Colour.valueOf(colours[0]);
			Colour back = Colour.valueOf(colours[1]);

			return new TextSection(text, fore, back);
		} catch (RuntimeException e) {
			System.out.println("Error while processing: " + section);
			throw e;
		}
	}

}
