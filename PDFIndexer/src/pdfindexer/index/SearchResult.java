/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfindexer.index;

/**
 *
 * @author lachlan
 */
public class SearchResult {

	private final Key key;
	private final Location location;

	public SearchResult(Key key, Location location) {
		this.key = key;
		this.location = location;
	}

	public Key getKey() {
		return key;
	}

	public Location getLocation() {
		return location;
	}

}
