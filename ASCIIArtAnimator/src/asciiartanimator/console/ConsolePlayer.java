/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asciiartanimator.console;

import asciiartanimator.model.*;
import java.util.Iterator;

/**
 *
 * @author lachlan
 */
public class ConsolePlayer {

	private ConsoleRenderer consoleRenderer;
	private Animation animation;
	private Iterator<Frame> frameIterator;
	private Thread animThread;
	private boolean running;

	public ConsolePlayer(Animation animation) {
		this.animation = animation;
		this.consoleRenderer = new ConsoleRenderer();
	}

	public void startSynchronous() {
		running = true;
		new AnimationThread().run();
	}

	public void startAsynchronous() {
		running = true;
		animThread = new Thread(new AnimationThread());
		animThread.start();
	}

	public void stop() {
		running = false;
		animThread.interrupt();
	}

	private class AnimationThread implements Runnable {

		public AnimationThread() {
		}

		@Override
		public void run() {
			int millisSleep = 1000 / animation.getFps();

			frameIterator = animation.getFrames().iterator();

			while (!Thread.currentThread().isInterrupted() && running) {
				doFrame();

				try {
					Thread.sleep(millisSleep);
				} catch (InterruptedException ie) {
					consoleRenderer.eraseScreen();
					return;
				}
			}
		}

		private void doFrame() {
			if (!frameIterator.hasNext()) {
				if (animation.isRepeating()) {
					frameIterator = animation.getFrames().iterator();
				} else {
					running = false;
					return;
				}
			}
			consoleRenderer.printFrame(frameIterator.next());
		}

	}

}
