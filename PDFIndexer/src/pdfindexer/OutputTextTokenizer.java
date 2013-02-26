/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfindexer;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lachlan
 */
public class OutputTextTokenizer implements AutoCloseable {

	private final PipedWriter stream;
	private final StreamTokenizer tokenizer;
	private final TokenProcessor processor;

	public OutputTextTokenizer(TokenProcessor proc) throws IOException {
		stream = new PipedWriter();
		PipedReader ir = new PipedReader(stream, 1024);

		tokenizer = new StreamTokenizer(ir);
		tokenizer.eolIsSignificant(false);
		tokenizer.lowerCaseMode(true);

		processor = proc;
	}

	public Writer getStream() {
		return stream;
	}

	public void process() throws IOException {
		while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			if (tokenizer.ttype == StreamTokenizer.TT_WORD) {
				processor.process(tokenizer.sval);
			} else if (tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
				if (tokenizer.nval != 0) {
					processor.process(String.valueOf(tokenizer.nval));
				}
			}

			//System.out.println(tokenizer.sval + " " + tokenizer.nval);
		}
	}

	@Override
	public void close() {
		try {
			stream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
