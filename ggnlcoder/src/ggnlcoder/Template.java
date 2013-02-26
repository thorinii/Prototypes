/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ggnlcoder;

import java.util.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lachlan
 */
public class Template {

	private String productImageTemplate;
	private String productTemplate;
	private String categoryGroupTemplate;
	private String newsletterTemplate;
	// Product Template Keys
	private final String KEY_PRODUCT_CODE = "product-code";
	private final String KEY_PRODUCT_NAME = "product-name";
	private final String KEY_PRODUCT_PRICE = "product-price";
	private final String KEY_PRODUCT_MEDIA = "product-media";
	private final String KEY_PRODUCT_DESCRIPTION = "product-description";
	// Category Group Template Keys
	private final String KEY_CATEGORY_NAME = "category-name";
	private final String KEY_CATEGORY_IMAGES = "category-images";
	private final String KEY_CATEGORY_PRODUCTS = "category-products";
	// Newsletter Template Keys
	private final String KEY_CATEGORYGROUPS = "category-groups";
	public Template() {
		this(".");
	}

	public Template(String location) {
		productImageTemplate = loadTemplateFile("product-image", location);
		productTemplate = loadTemplateFile("product", location);
		categoryGroupTemplate = loadTemplateFile("category-group", location);
		newsletterTemplate = loadTemplateFile("newsletter", location);
	}

	private String loadTemplateFile(String templateName, String location) {
		String templateFile = templateName + ".tpl.html";
		String file = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(location
					+ File.separator + templateFile));

			String line = "";
			while ((line = br.readLine()) != null) {
				file += line + "\n";
			}

			return file;
		} catch (IOException ioe) {
			Logger.getLogger(this.getClass().getName()).
					log(Level.SEVERE, "Error Loading Template File", ioe);
			return null;
		}
	}

	private String templateProductImage(Product product) {
		Map<String, String> keys = new HashMap<String, String>();
		keys.put(KEY_PRODUCT_CODE, product.getCode());

		return applyTemplate(productImageTemplate, keys);
	}

	public String templateProduct(Product product) {
		Map<String, String> keys = new HashMap<String, String>();
		keys.putAll(product.getOtherData());

		keys.put(KEY_PRODUCT_CODE, product.getCode());
		keys.put(KEY_PRODUCT_NAME, product.getName());

		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		keys.put(KEY_PRODUCT_PRICE, currencyFormat.format(product.getPrice()));
		keys.put("saleprice", currencyFormat.format(product.getSalePrice()));

		if (product.getMedia().startsWith("BOOK") && product.getAuthor() != null
				&& !product.getAuthor().equals("")) {
			keys.put(KEY_PRODUCT_MEDIA, product.getAuthor());
		} else if (product.getMedia() == null || product.getMedia().equals("")) {
			keys.put(KEY_PRODUCT_MEDIA, "");
		} else {
			keys.put(KEY_PRODUCT_MEDIA, product.getMedia());
		}

		keys.put(KEY_PRODUCT_DESCRIPTION, product.getDescription());

		return applyTemplate(productTemplate, keys);
	}

	public String templateCategoryGroup(String categoryName,
			List<Product> products) {
		String templatedImages = "";
		String templatedProducts = "";

		for (Product product : products) {
			templatedImages += templateProductImage(product) + "\n";
			templatedProducts += templateProduct(product) + "\n";
		}

		Map<String, String> keys = new HashMap<String, String>();
		keys.put(KEY_CATEGORY_NAME, (categoryName == null) ? "&nbsp;"
				: categoryName);
		keys.put(KEY_CATEGORY_IMAGES, templatedImages);
		keys.put(KEY_CATEGORY_PRODUCTS, templatedProducts);

		return applyTemplate(categoryGroupTemplate, keys);
	}

	public String templateNewsletter(List<String> categoryGroups) {
		String allGroups = "";

		for (String group : categoryGroups) {
			allGroups += group + "\n";
		}

		Map<String, String> keys = new HashMap<String, String>();
		keys.put(KEY_CATEGORYGROUPS, allGroups);

		return applyTemplate(newsletterTemplate, keys);
	}

	private String applyTemplate(String template, Map<String, String> keys) {
		String result = template;

		for (String key : keys.keySet()) {
			if (keys.get(key) == null) {
				result.replace("%%" + key + "%%", "");
			} else {
				result = result.replace("%%" + key + "%%", keys.get(key));
			}
		}

		return result;
	}

}
