/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfindexer.index;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author lachlan
 */
public class Key implements Serializable {

	private static final Map<String, Key> KEYS = new HashMap<>();
	private final String key;

	private Key(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Key)) {
			return false;
		}
		final Key other = (Key) obj;
		if (!Objects.equals(this.key, other.key)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + Objects.hashCode(this.key);
		return hash;
	}

	public static Key getKey(String key) {
		key = key.toLowerCase().trim();

		Key k = KEYS.get(key);

		if (k == null) {
			k = new Key(key);
		}

		return k;
	}

	@Override
	public String toString() {
		return key;
	}

}
