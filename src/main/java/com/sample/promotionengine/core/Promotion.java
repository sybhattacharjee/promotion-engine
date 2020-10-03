package com.sample.promotionengine.core;

import java.util.Map;

import lombok.Data;

/**
 * Instantiates a new promotion.
 *
 * @param itemCombination the item combination
 * @param promotionalPrice the promotional price
 */
@Data
public class Promotion {

	/** The item combination. */
	private final Map<Item, Integer> itemCombination;
	

	/** The promotional price. */
	private final float promotionalPrice;

	
}
