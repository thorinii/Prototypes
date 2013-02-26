/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfindexer;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import pdfindexer.index.*;
import pdfindexer.processor.Processor;

/**
 *
 * @author lachlan
 */
public class PDFIndexer {

	private static final int KEY_RANGE = 30;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Loading index store...");
		Index index = loadIndex();

		System.out.println("Indexing files...");
		indexFiles(index);

		System.out.println("Saving index...");
		saveIndex(index);

		System.out.println();
		System.out.println("# of Keys: " + index.getKeyCount());
		System.out.println("# of Entries: " + index.getEntryCount());
		System.out.println();

		doSearching(index);
	}

	private static Index loadIndex() {
		File indexFile = new File("index.bin");

		if (indexFile.exists()) {
			try {
				return new IndexPersister().loadIndex(indexFile);
			} catch (IOException ex) {
				ex.printStackTrace();
				indexFile.delete();
				return new RAMIndex();
			}
		} else {
			return new RAMIndex();
		}
	}

	private static void saveIndex(Index index) {
		File indexFile = new File("index.bin");

		try {
			new IndexPersister().saveIndex(indexFile, index);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void indexFiles(Index index) throws IOException {
		File dataDirectory = new File("data");
		final Processor proc = new Processor(index);
		ExecutorService es = Executors.newFixedThreadPool(2);

		// Index all data
		for (final File data : dataDirectory.listFiles()) {
			if (!data.getName().endsWith(".pdf")) {
				continue;
			}

			boolean contains = false;
			for (Document d : index.getDocumentsIndex()) {
				if (d.getName().equals(data.getName())) {
					contains = true;
					break;
				}
			}
			if (contains) {
				continue;
			}

			es.execute(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println("Indexing " + data.getName());
						proc.process(data);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}

			});
		}

		es.shutdown();
		try {
			es.awaitTermination(1000, TimeUnit.SECONDS);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private static void doSearching(Index index) {
		Scanner in = new Scanner(System.in);
		String search;

		System.out.print("Enter a search term, blank to exit: ");
		search = in.nextLine().trim();

		while (!search.isEmpty()) {
			Set<SearchResult> results = index.search(search,
					SearchType.ExactPhrase);

			if (results.isEmpty()) {
				System.out.println("No results for '" + search + "'");
			} else {
				processSearchResults(results, search);
			}

			System.out.print("\nEnter a search term, blank to exit: ");
			search = in.nextLine().trim();
		}
	}

	private static void processSearchResults(Set<SearchResult> results,
			String search) {
		System.out.println(results.size() + " results for '" + search
				+ "':");

		for (SearchResult result : results) {
			Location loc = result.getLocation();
			System.out.println(
					loc.getDocument().getName() + ":");
			System.out.print("  ");


			int start, end;
			start = Math.max(0, loc.getWordIndex() - KEY_RANGE);
			end = Math.min(loc.getWordIndex() + KEY_RANGE, loc.getDocument().
					getWords().length);

			for (int i = start; i < end; i++) {
				String word = loc.getDocument().getWords()[i];
				System.out.print(" " + word.trim());
			}

			System.out.println();
			System.out.println();
		}
	}

}
