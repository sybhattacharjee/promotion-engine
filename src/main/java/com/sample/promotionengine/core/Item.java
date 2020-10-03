package com.sample.promotionengine.core;

/**
 * The Enum Item.
 */
public enum Item {

	/** The a. */
	A(50), /** The b. */
 B(30), /** The c. */
 C(20), /** The d. */
 D(15);

	/** The price. */
	private int price;

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public int getPrice() {
		return this.price;
	}

	/**
	 * Instantiates a new item.
	 *
	 * @param price the price
	 */
	private Item(int price) {
		this.price = price;
	}
	
	

}
