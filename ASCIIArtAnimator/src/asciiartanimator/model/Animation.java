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
public class Animation {

	private LinkedList<Frame> frames;
	private int fps;
	private String name;
	private boolean repeating;

	public Animation() {
		this(new LinkedList<Frame>(), "", 1, false);
	}

	public Animation(LinkedList<Frame> frames, String name, int fps) {
		this(frames, name, fps, false);
	}

	public Animation(LinkedList<Frame> frames, String name, int fps,
			boolean repeating) {
		this.frames = frames;
		this.name = name;
		this.fps = fps;
		this.repeating = repeating;
	}

	public LinkedList<Frame> getFrames() {
		return frames;
	}

	public void setFrames(LinkedList<Frame> frames) {
		this.frames = frames;
	}

	public Frame getFrame(int frameID) {
		return frames.get(frameID);
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public int getFrameCount() {
		return frames.size();
	}

	public boolean isRepeating() {
		return repeating;
	}

	public void setRepeating(boolean repeating) {
		this.repeating = repeating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
