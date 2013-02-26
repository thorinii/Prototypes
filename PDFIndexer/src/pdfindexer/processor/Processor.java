/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfindexer.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import pdfindexer.index.*;

/**
 *
 * @author lachlan
 */
public class Processor {

	private static final String WORD_SPLIT = " ";
	private final Index index;
	private final Pattern splitPattern;
	private final Pattern keywordNormaliser;

	public Processor(Index index) {
		this.index = index;

		splitPattern = Pattern.compile(WORD_SPLIT, Pattern.MULTILINE);
		keywordNormaliser = Pattern.compile(
				"[" + Pattern.quote("`~!@#$%^&*()-_=+\\|,.<>/?;:\'\"") + "]");
	}

	public void process(File file) throws IOException {
		Document doc = new Document(file.getName());
		index.putDocument(doc);

		processText(doc, getText(file));
	}

	private String getText(File file) throws IOException {
		PDFTextStripper stripper = new PDFTextStripper();
		PDDocument document = PDDocument.load(file);

		String text = stripper.getText(document);

		document.close();
		return text;
	}

	private void processText(Document document, String text) throws IOException {
		Matcher matcher = splitPattern.matcher(text);

		List<String> documentWords = new ArrayList<>();

		int wordIndex = 0;
		int textIndex = 0;
		while (matcher.find()) {
			String word = text.substring(textIndex, matcher.start()).trim();
			String keyWord = processWordForKey(word);
			if (word.isEmpty() || keyWord.isEmpty()) {
				continue;
			}

			Key key = Key.getKey(keyWord);
			Location loc = new Location(document, wordIndex);

			documentWords.add(word);
			index.addEntry(key, loc);

			textIndex = matcher.end();
			wordIndex++;
		}

		String word = text.substring(textIndex);
		if (!word.isEmpty()) {
			Key key = Key.getKey(word);
			Location loc = new Location(document, wordIndex);

			documentWords.add(word);
			index.addEntry(key, loc);
		}


		document.setWords(
				documentWords.toArray(new String[documentWords.size()]));
	}

	private String processWordForKey(String word) {
		Matcher m = keywordNormaliser.matcher(word);
		word = m.replaceAll("");

		/*
		 * if (word.startsWith(".")) { word = word.substring(1); } if
		 * (word.endsWith(".")) { word = word.substring(0, word.length() - 1); }
		 */
		return word;
	}

}
