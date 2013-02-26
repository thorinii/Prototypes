/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagetextmodefilter;

import java.awt.Image;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author lachlan
 */
public class ImageMatcher {

	private ForkJoinPool forkPool;
	private BufferedImage original;
	private BufferedImage compare;
	private int[] bestMatch;

	public ImageMatcher(String originalFile, String compareFile) {
		forkPool = new ForkJoinPool(3);

		try {
			System.out.println("Loading Images...");
			original = ImageIO.read(new File(originalFile));
			compare = ImageIO.read(new File(compareFile));

			ImageProcessor tmp = new ImageProcessor();
			tmp.setLevel(128);
			tmp.setOriginal(compare);
			compare = tmp.getImage();
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	public int findLevel() {
		System.out.println("Finding brightness level...");

		ImageProcessor processor = new ImageProcessor();
		processor.setOriginal(original);
		processor.setLevel(128);

		bestMatch = new int[256];

		LevelFinderForkTask task = new LevelFinderForkTask(processor);

		try {
			forkPool.invoke(task);

			int min = -1, minValue = Integer.MAX_VALUE;
			for (int i = 0; i < 256; i++) {
				if (minValue > bestMatch[i]) {
					min = i;
					minValue = bestMatch[i];
				}
			}
			return min;
		} catch (CancellationException ce) {
		}

		return -1;
	}

	class LevelFinderForkTask extends RecursiveAction {

		private final ImageProcessor processor;
		private final int start, length;

		public LevelFinderForkTask(ImageProcessor processor) {
			this(0, 255, processor);
		}

		private LevelFinderForkTask(int start,
				int length, ImageProcessor processor) {
			this.processor = processor;
			this.start = start;
			this.length = length;
		}

		@Override
		protected void compute() {
			System.gc();

			if (length <= 1) {
				checkMatchesLevel();
			} else {
				ImageProcessor newProc = new ImageProcessor();
				newProc.setOriginal(processor.getOriginal());

				invokeAll(
						new LevelFinderForkTask(start,
						length / 2, processor),
						new LevelFinderForkTask(start + length / 2,
						length / 2, newProc));
			}
		}

		private void checkMatchesLevel() {
			ImageProcessor tmp = processor;
			tmp.setLevel(start);
			tmp.setOriginal(original);
			BufferedImage filtered = tmp.getImage();

			tmp = new ImageProcessor();
			tmp.setLevel(128);
			tmp.setOriginal(compare);
			BufferedImage compareFiltered = tmp.getImage();

			int res = compareImages(compareFiltered, filtered);
			bestMatch[start] = res;
		}

	}

	public int compareImages(BufferedImage img1, BufferedImage img2) {
		DataBuffer d1 = img1.getData().getDataBuffer();
		DataBuffer d2 = img2.getData().getDataBuffer();

		int size = Math.min(d1.getSize(), d2.getSize());

		int differences = 0;

		for (int i = 0; i < size; i++) {
			if (d1.getElem(i) != d2.getElem(i)) {
				differences++;
			}
		}

		return differences;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		ImageMatcher imageMatcher;

		if (args.length != 2) {
			imageMatcher = new ImageMatcher(
					"./lachlan-test-greyscale.png",
					"./lachlan-test-greyscale-cmp.png");
		} else {
			imageMatcher = new ImageMatcher(args[0], args[1]);
		}
		System.out.println("Finding level...");
		int level = imageMatcher.findLevel();
		System.out.println("Level is: " + level);
		System.out.println("Distance is: " + imageMatcher.bestMatch[level]);
	}

	private static void printUsage() {
		System.out.println("Usage:");
		System.out.println(
				" java ImageMatcher [greyscale original] [to compare with]");
	}

}
