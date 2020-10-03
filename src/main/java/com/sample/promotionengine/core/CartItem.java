package com.sample.promotionengine.core;

import lombok.Data;

/**
 * Instantiates a new cart item.
 *
 * @param item the item
 * @param quantity the quantity
 */
@Data
public class CartItem {

	/** The item. */
	private final Item item;

	/** The quantity. */
	private final int quantity;


}
