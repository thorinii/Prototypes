/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ggnlcoder;

import java.util.Map;
import org.junit.*;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author lachlan
 */
public class ProductRequesterTest {

	/**
	 * Test of getProduct method, of class ProductRequester.
	 */
	@Test
	public void testGetProduct() throws Exception {
		System.out.println("getProduct");
		String productCode = "AU0001";
		ProductRequester instance = new ProductRequester();
		Product expResult = null;
		Product result = instance.getProduct(productCode);
		assertEquals(expResult, result);
		
		fail("The test case is a prototype.");
	}

}
