/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagetextmodefilter;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author lachlan
 */
public class ImageUtils {

	public static BufferedImage imageToBuffered(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		} else {
			BufferedImage b = new BufferedImage(image.getWidth(null),
					image.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
			Graphics graph = b.getGraphics();
			graph.drawImage(image, 0, 0, null);
			graph.dispose();
			return b;
		}
	}

}
