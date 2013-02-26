/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ggnlcoder;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.xpath.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author Lachlan
 */
public class ProductRequester {

	public ProductRequester() throws IOException {
		props = new Properties();
		props.load(new FileInputStream(PROPERTIES_FILE));

		if (getURL().equals("")) {
			throw new IOException("Could not load valid Volusion Site URL");
		}
	}

	public synchronized Product getProduct(String productCode) throws IOException,
			ProductNotFoundException {
		if (isCached(productCode)) {
			Product product = getCached(productCode);

			if (product == null) {
				throw new ProductNotFoundException(productCode + " was not found");
			}
			return product.clone();
		}

		String fullURL;

		try {
			fullURL = getURL().replace("%productcode%", URLEncoder.encode(
					productCode, "UTF-8"));
		} catch (UnsupportedEncodingException uee) {
			Logger.getLogger(this.getClass().getName()).
					log(Level.SEVERE, "Could not encode URL", uee);
			return null;
		}

		URL url;

		try {
			url = new URL(fullURL);
		} catch (MalformedURLException murle) {
			Logger.getLogger(this.getClass().getName()).
					log(Level.SEVERE, "Malformed URL", murle);
			return null;
		}

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();

		Document document;

		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
			document = domBuilder.parse(connection.getInputStream());
		} catch (ParserConfigurationException pce) {
			Logger.getLogger(this.getClass().getName()).
					log(Level.SEVERE, "DOM Parser Config Error", pce);
			return null;
		} catch (SAXException saxe) {
			Logger.getLogger(this.getClass().getName()).
					log(Level.SEVERE, "SAX Parser Exception", saxe);
			return null;
		}

		Product product = getProductFrom(document);

		cache.put(productCode, product);

		if (product != null) {
			return product.clone();
		} else {
			throw new ProductNotFoundException(productCode + " was not found");
		}
	}

	public Product getProductFrom(Document d) {
		Map<String, String> data = getProductData(d);

		Product product = new Product(data.get("productcode"));

		product.setName(data.get("productname"));
		if (product.getName().trim().equals("")) {
			return null;
		}

		product.setDescription(data.get("productdescription"));
		product.setAuthor(data.get("customfield2"));
		product.setMedia(data.get("customfield1"));

		if (data.containsKey("productprice") && !data.get("productprice").trim().isEmpty()) {
			product.setPrice(Double.parseDouble(data.get("productprice")));
		}

		try {
			if (data.containsKey("saleprice") && !data.get("saleprice").trim().isEmpty()) {
				product.setSalePrice(Double.parseDouble(data.get("saleprice")));
			}
		} catch (NumberFormatException nfe) {
			// That's fine - it doesn't have a sale price
		}

		product.setOtherData(data);

		return product;
	}

	public String getURL() {
		String base = props.getProperty("urlbase");

		String url = base.replace("%site%", props.getProperty("site"));
		url = url.replace("%password%", props.getProperty("password"));
		url = url.replace("%login%", props.getProperty("login"));

		return url;
	}

	public Map<String, String> getProductData(Document document) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("productname", "");

		Node rootElement = document.getDocumentElement().getElementsByTagName("Products").item(0);
		if (rootElement == null) {
			return data;
		}
		NodeList nodes = rootElement.getChildNodes();

		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;

				data.put(e.getTagName().toLowerCase(), e.getTextContent());
			}
		}

		return data;
	}

	public void printDocument(Document d) {
		printElement(d.getDocumentElement());
	}

	public void printElement(Element e) {
		printElement(e, 0);
	}

	public void printElement(Element e, int indent) {
		print("[" + e.getNodeName(), indent);

		NodeList nl = e.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				printElement((Element) n, indent + 1);
			} else if (n.getNodeType() == Node.TEXT_NODE) {
				String text = ((Text) n).getWholeText().trim();
				if (!text.equals("")) {
					print(text, indent + 1);
				}
			}
		}
		print("/" + e.getNodeName() + "]", indent);
	}

	public void print(String str) {
		print(str, 0);
	}

	public void print(String str, int indent) {
		System.out.println(stringRepeat(" ", indent) + str);
	}

	public String stringRepeat(String repeat, int times) {
		return new String(new char[times]).replace("\0", repeat);
	}

	public static boolean isCached(String productCode) {
		return cache.containsKey(productCode);
	}

	public static Product getCached(String productCode) {
		return cache.get(productCode);
	}

	private final String PROPERTIES_FILE = "volusion.properties";
	private Properties props;
	private static Map<String, Product> cache = new HashMap<String, Product>();
}
