/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagetextmodefilter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author lachlan
 */
public class ImageProcessorTest {

	private BufferedImage originalImage;
	private BufferedImage blankImage;
	private BufferedImage blackImage;

	@Before
	public void setUp() throws Exception {
		originalImage = new BufferedImage(500, 500, BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = originalImage.getGraphics();
		g.setColor(Color.BLACK);
		g.drawLine(4, 4, 8, 8);
		g.setColor(Color.WHITE);
		g.drawLine(9, 9, 13, 13);
		g.dispose();		
		
		blankImage = new BufferedImage(500, 500, BufferedImage.TYPE_BYTE_GRAY);
		
		blackImage = new BufferedImage(500, 500, BufferedImage.TYPE_BYTE_GRAY);
		g = blackImage.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 500);
		g.dispose();
	}

	@Test
	public void testUnChanged() {
		ImageProcessor processor = new ImageProcessor();
		processor.setLevel(128);
		processor.setOriginal(originalImage);

		BufferedImage result = (BufferedImage) processor.getImage();

		for (int i = 0; i < 500; i++) {
			for (int j = 0; j < 500; j++) {
				assertEquals(i+ ", " + j + " failed", originalImage.getRGB(i, j), result.getRGB(i, j));
			}
		}
	}


	@Test
	public void testBlank() {
		ImageProcessor processor = new ImageProcessor();
		processor.setLevel(0);
		processor.setOriginal(originalImage);

		BufferedImage result = (BufferedImage) processor.getImage();

		for (int i = 0; i < 500; i++) {
			for (int j = 0; j < 500; j++) {
				assertEquals(i+ ", " + j + " failed", (byte)blankImage.getRGB(i, j), (byte)result.getRGB(i, j));
			}
		}
	}


	@Test
	public void testBlack() {
		ImageProcessor processor = new ImageProcessor();
		processor.setLevel(255);
		processor.setOriginal(originalImage);

		BufferedImage result = (BufferedImage) processor.getImage();

		for (int i = 0; i < 500; i++) {
			for (int j = 0; j < 500; j++) {
				assertEquals(i+ ", " + j + " failed", (byte)blackImage.getRGB(i, j), (byte)result.getRGB(i, j));
			}
		}
	}
}
