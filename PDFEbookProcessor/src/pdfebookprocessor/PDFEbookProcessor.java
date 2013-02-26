/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfebookprocessor;

import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 *
 * @author lachlan
 */
public class PDFEbookProcessor {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		PDDocument book = PDDocument.load("data/voyage.pdf");
		PDDocument cover = PDDocument.load("data/voyage-cover.pdf");
		PDDocument working = new PDDocument();

		printMetrics(book);
		//printMetrics(cover);

		try {
			float bookWidth = getPDFWidth(book);
			float coverWidth = getPDFWidth(cover);
			float margin = coverWidth - bookWidth;

			PDRectangle leftBox = getPDFBox(cover);
			leftBox.setUpperRightX(bookWidth);
			
			PDRectangle rightBox = getPDFBox(cover);
			rightBox.setLowerLeftX(margin);
			rightBox.setUpperRightX(bookWidth);

			printBox("Lft: ", leftBox);
			printBox("Rgt: ", rightBox);

			List<PDPage> coverPages = cover.getDocumentCatalog().
					getAllPages();
			PDPage cover1 = new PDPage(new COSDictionary(coverPages.get(0).
					getCOSDictionary()));
			PDPage cover2 = new PDPage(new COSDictionary(coverPages.get(1).
					getCOSDictionary()));

			cover1.setCropBox(rightBox);
			cover2.setCropBox(leftBox);

			working.addPage(cover1);
			working.addPage(cover2);


			List<PDPage> bookPages = book.getDocumentCatalog().getAllPages();
			for (PDPage page : bookPages) {
				working.addPage(page);
			}


			cover1 = new PDPage(new COSDictionary(coverPages.get(0).
					getCOSDictionary()));
			cover2 = new PDPage(new COSDictionary(coverPages.get(1).
					getCOSDictionary()));

			cover1.setCropBox(leftBox);
			cover2.setCropBox(rightBox);

			working.addPage(cover1);
			working.addPage(cover2);

			savePDF(working);
		} finally {
			book.close();
			cover.close();
			working.close();
		}
	}

	private static PDRectangle getPDFBox(PDDocument doc) {
		PDPage page = (PDPage) doc.getDocumentCatalog().getAllPages().get(0);

		PDRectangle rect = page.findCropBox();
		PDRectangle copy = new PDRectangle();

		copy.setLowerLeftX(rect.getLowerLeftX());
		copy.setLowerLeftY(rect.getLowerLeftY());
		copy.setUpperRightX(rect.getUpperRightX());
		copy.setUpperRightY(rect.getUpperRightY());

		return copy;
	}

	private static float getPDFWidth(PDDocument doc) {
		PDPage page = (PDPage) doc.getDocumentCatalog().getAllPages().get(0);

		return page.findCropBox().getWidth();
	}

	private static void savePDF(PDDocument doc) throws IOException {
		try {
			doc.save("data/voyage-out.pdf");
		} catch (COSVisitorException ex) {
			throw new IOException(ex);
		}
	}

	private static void printMetrics(PDDocument doc) {
		PDPage page = (PDPage) doc.getDocumentCatalog().getAllPages().get(0);

		printBox("findCrop", page.findCropBox());
		printBox("findMedia", page.findMediaBox());
		printBox("getArt", page.getArtBox());
		printBox("getBleed", page.getBleedBox());
		printBox("getCrop", page.getCropBox());
		printBox("getMedia", page.getMediaBox());
		printBox("getTrim", page.getTrimBox());
	}

	private static void printBox(String name, PDRectangle box) {
		if (box == null) {
			System.out.println(name + ": null");
		} else {
			System.out.print(name + ": " + box.toString());
			System.out.println(" [" + box.getWidth() + " " + box.getHeight() + "]");
		}
	}

}
