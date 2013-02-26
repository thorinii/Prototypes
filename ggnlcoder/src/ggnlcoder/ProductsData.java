/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ggnlcoder;

import java.util.*;

/**
 *
 * @author Lachlan
 */
public class ProductsData {

	public ProductsData() {
		LinkedHashMap<String, List<String>> productsList = new LinkedHashMap<String, List<String>>();
		productsListMap = new LinkedHashMap<String, List<String>>();
	}

	public boolean addCategory(String categoryName) {
		productsListMap.put(categoryName, new ArrayList<String>());

		return true;
	}

	public void removeCategory(String categoryName) {
		productsListMap.get(categoryName).clear();
		productsListMap.remove(categoryName);
	}

	public boolean addProduct(String categoryName, String productCode) {
		if (productsListMap.containsKey(categoryName)) {
			if (!productsListMap.get(categoryName).contains(productCode)) {
				productsListMap.get(categoryName).add(productCode);
				return true;
			} else {
				return false;
			}
		} else {
			throw new NoSuchElementException("No Product Category '"
					+ categoryName + "'");
		}
	}

	public void editProduct(String categoryName, String oldProductCode,
			String newProductCode) {
		if (productsListMap.containsKey(categoryName)) {
			if (!productsListMap.get(categoryName).contains(oldProductCode)) {
				throw new NoSuchElementException("No Product '"
						+ oldProductCode + "'");
			} else {
				int index = productsListMap.get(categoryName).indexOf(oldProductCode);
				productsListMap.get(categoryName).set(index, newProductCode);
			}
		} else {
			throw new NoSuchElementException("No Product Category '"
					+ categoryName + "'");
		}
	}

	public void removeProduct(String categoryName, String productCode) {
		if (productsListMap.containsKey(categoryName)) {
			if (productsListMap.get(categoryName).contains(productCode)) {
				productsListMap.get(categoryName).remove(productCode);
			} else {
				throw new NoSuchElementException("No Product '" + productCode
						+ "' in category '" + categoryName + "'");
			}
		} else {
			throw new NoSuchElementException("No Product Category '"
					+ categoryName + "'");
		}
	}

	public List<String> getCategories() {
		return new ArrayList<String>(productsListMap.keySet());
	}

	public Map<String, List<String>> getProducts() {
		return Collections.unmodifiableMap(productsListMap);
	}
	
	private Map<String, List<String>> productsListMap;
}
