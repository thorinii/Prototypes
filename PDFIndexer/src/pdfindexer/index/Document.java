/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfindexer.index;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author lachlan
 */
public class Document implements Serializable {

	private final String name;
	private String[] words;

	public Document(String name) {
		this.name = name;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	public String getName() {
		return name;
	}

	public String[] getWords() {
		return words;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Document other = (Document) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return true;
	}

}
