/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfindexer.index;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author lachlan
 */
public class Location implements Comparable<Location>, Serializable {

	private final Document document;
	private final int wordIndex;

	public Location(Document document, int wordIndex) {
		this.document = document;
		this.wordIndex = wordIndex;
	}

	public Document getDocument() {
		return document;
	}

	public int getWordIndex() {
		return wordIndex;
	}

	@Override
	public int compareTo(Location o) {
		if (document.getName().equals(o.document.getName())) {
			return wordIndex - o.wordIndex;
		} else {
			return document.getName().compareTo(o.document.getName());
		}
	}

}
