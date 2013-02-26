/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ggnlcoder;

/**
 *
 * @author lachlan
 */
public class Processor {

	private double priceScale;
	private double priceShift;

	public Processor() {
		priceScale = 1.0;
		priceShift = 0.0;
	}

	public Processor(double priceScale, double priceShift) {
		this.priceScale = priceScale;
		this.priceShift = priceShift;
	}

	public void processProduct(Product p) {
		double price = p.getPrice();
		price *= priceScale;
		price += priceShift;
		p.setPrice(price);
		
		double salePrice = p.getSalePrice();
		salePrice *= priceScale;
		salePrice += priceShift;
		p.setSalePrice(salePrice);
	}

	public void setPriceScale(double priceScale) {
		this.priceScale = priceScale;
	}

	public double getPriceScale() {
		return priceScale;
	}

	public void setPriceShift(double priceShift) {
		this.priceShift = priceShift;
	}

	public double getPriceShift() {
		return priceShift;
	}
}
