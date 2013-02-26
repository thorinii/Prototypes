/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfindexer.index;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author lachlan
 */
public class IndexPersister {

	public void saveIndex(File out, Index index) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(
						new GZIPOutputStream(
						new FileOutputStream(out)))) {
			oos.writeObject(index);
		}
	}

	public Index loadIndex(File in) throws IOException {
		try (ObjectInputStream ois = new ObjectInputStream(
						new GZIPInputStream(
						new FileInputStream(in)))) {
			return (Index) ois.readObject();
		} catch (ClassNotFoundException cnfe) {
			throw new IOException(cnfe);
		}
	}

}
