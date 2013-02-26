/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asciiartanimator;

import asciiartanimator.console.ConsolePlayer;
import asciiartanimator.storage.Loader;
import asciiartanimator.model.*;
import asciiartanimator.storage.Storer;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lachlan
 */
public class ASCIIArtAnimator {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Welcome to ASCII Art Animator\n");

		System.out.println(
				"You will need a console that supports ANSI colouring,");
		System.out.println(
				"though colours aren't actually used in the final animation.");

		System.out.println();
		System.out.println("Playing a test animation to test your console");

		System.out.println();
		System.out.println();

		/*
		 * Testing Animation
		 */
		Animation testingAnimation = makeTestingAnimation();

		ConsolePlayer consolePlayer = new ConsolePlayer(testingAnimation);
		printHello(testingAnimation.getName());
		consolePlayer.startSynchronous();

		/*
		 * Proper Animation
		 */
		Loader loader = new Loader();
		Animation anim;

		if (args.length > 0) {
			anim = loader.loadAnimation(args[0]);
		} else {
			anim = loader.loadAnimation(ASCIIArtAnimator.class.getClassLoader().
					getResourceAsStream("h-bday.asciiaa"));
		}

		consolePlayer = new ConsolePlayer(anim);
		printHello(anim.getName());
		consolePlayer.startAsynchronous();
	}

	public static Animation makeTestingAnimation() {
		LinkedList<Frame> frames = new LinkedList<Frame>();

		for (int i = 0; i < 8; i++) {
			frames.add(new Frame(new TextSection("Testing Colours...", Colour.None,
					Colour.None)));
		}

		for (int i = 0; i < Colour.values().length; i++) {
			Colour colour = Colour.values()[i];

			if (colour == Colour.None) {
				continue;
			}


			for (int j = 0; j < 50; j+=10) {
				frames.add(new Frame(new TextSection("Testing " + colour.name()
						+ "\n" + new String(new char[j + 1]).replace("\0",
						new String(new char[j / 2 + 1]).replace("\0", "=")
						+ "\n"),
						colour,
						Colour.Black)));
			}
		}

		return new Animation(frames, "Testing Animation", 8, false);
	}

	public static void printHello(String animation) {
		System.out.printf("Now playing %s in: ", animation);

		for (int i = 3; i > 0; i--) {
			System.out.print(i);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(ASCIIArtAnimator.class.getName()).
						log(Level.SEVERE, null, ex);
			}

			System.out.print("\b");
		}

		System.out.println("\n\n");
	}

}
