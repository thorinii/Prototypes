/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagetextmodefilter;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;

/**
 *
 * @author lachlan
 */
public class ImageProcessor {

	private Image original;
	private BufferedImage bufferedOriginal;
	private BufferedImage bufferedResult;
	private BufferedImageOp operator;
	private byte[] lookupData;

	public ImageProcessor() {
		lookupData = new byte[256];
		LookupTable lookupTable = new ByteLookupTable(0, lookupData);
		operator = new LookupOp(lookupTable, null);
		setLevel(128);
	}

	public void setOriginal(Image original) {
		this.original = original;
		bufferedOriginal = ImageUtils.imageToBuffered(original);
	}

	public Image getOriginal() {
		return original;
	}

	public void setLevel(int level) {
		for (int i = 0; i < level; i++) {
			lookupData[i] = 0;
		}
		for (int i = level; i < 256; i++) {
			lookupData[i] = -1;
		}
	}

	public BufferedImage getImage() {
		if (bufferedResult == null) {
			bufferedResult = new BufferedImage(original.getWidth(
					null), original.getHeight(null),
					BufferedImage.TYPE_BYTE_GRAY);
		}

		return operator.filter(bufferedOriginal, bufferedResult);
	}

}
