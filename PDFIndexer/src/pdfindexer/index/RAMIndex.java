/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfindexer.index;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author lachlan
 */
public class RAMIndex implements Index, Serializable {

	private final Map<Key, List<Location>> entries;
	private final Set<Document> indexedFiles;

	public RAMIndex() {
		entries = new HashMap<>();
		indexedFiles = Collections.synchronizedSet(new HashSet<Document>());
	}

	@Override
	public void addEntry(Key key, Location location) {
		List<Location> keyEntries = entries.get(key);

		if (keyEntries == null) {
			keyEntries = new ArrayList<>();
			entries.put(key, keyEntries);
		}

		keyEntries.add(location);
	}

	@Override
	public void putDocument(Document doc) {
		indexedFiles.add(doc);
	}

	@Override
	public Set<Document> getDocumentsIndex() {
		return Collections.unmodifiableSet(indexedFiles);
	}

	@Override
	public int getKeyCount() {
		return entries.size();
	}

	@Override
	public int getEntryCount() {
		int count = 0;

		for (List<Location> keyEntries : entries.values()) {
			count += keyEntries.size();
		}

		return count;
	}

	@Override
	public Set<SearchResult> search(String search, SearchType type) {
		if (type == SearchType.ExactPhrase) {
			return exactSearch(search);
		} else if (type == SearchType.ExactWords) {
			return exactSearch(search);
		} else if (type == SearchType.Near) {
			return exactSearch(search);
		}
		return null;
	}

	private Set<SearchResult> exactSearch(String search) {
		String[] searchWords = search.split(" ");

		Key key = Key.getKey(searchWords[0]);
		Set<SearchResult> results = new HashSet<>();

		List<Location> keyEntries = entries.get(key);

		if (keyEntries == null) {
			return results;
		}

		for (Location loc : keyEntries) {
			boolean matches = true;

			int i = loc.getWordIndex();
			String[] document = loc.getDocument().getWords();

			for (String word : searchWords) {
				if (!document[i].equalsIgnoreCase(word)) {
					matches = false;
					break;
				}

				i++;
			}

			if (matches) {
				results.add(new SearchResult(key, loc));
			}
		}


		return results;
	}

}
