/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ggnlcoder;

import java.util.Map;

/**
 *
 * @author Lachlan
 */
public class Product {

	private final String code;
	private String name, description, author, media;
	private double price, salePrice;
	private Map<String, String> otherData;
	public Product(String code) {
		this.code = code;

		name = "";
		description = "";
		author = "";
		media = "";
		price = 0;
		salePrice = 0;
	}

	public String getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setMedia(String media) {
		this.media = media;
		if(media == null)
			this.media = ";";
	}

	public String getMedia() {
		return media;
	}

	public void setOtherData(Map<String, String> otherData) {
		this.otherData = otherData;
	}

	public Map<String, String> getOtherData() {
		return otherData;
	}
	@Override
	protected Product clone() {
		Product p = new Product(code);

		p.name = name;
		p.description = description;
		p.author = author;
		p.media = media;
		
		p.price = price;
		p.salePrice = salePrice;
		
		p.otherData = otherData;
		
		return p;
	}

	@Override
	public String toString() {
		String toString = "[Product;";

		toString += "code=" + code;
		toString += "name=" + name;
		toString += "price" + price;
		toString += "salePrice" + salePrice;
		toString += "]";

		return toString;
	}

}
