/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfindexer.index;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 *
 * @author lachlan
 */
public interface Index {

	public void addEntry(Key key, Location location);

	public Set<SearchResult> search(String search, SearchType type);
	
	public void putDocument(Document doc);

	public Set<Document> getDocumentsIndex();

	public int getEntryCount();

	public int getKeyCount();

}
