/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfindexer;

/**
 *
 * @author lachlan
 */
public class IndexTokenProcessor implements TokenProcessor {

	@Override
	public void process(String token) {
		System.out.print(token + " ");
	}

}
