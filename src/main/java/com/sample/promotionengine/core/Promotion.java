package com.sample.promotionengine.core;

import java.util.Map;

import lombok.Data;

@Data
public class Promotion {

	private final Map<Item, Integer> itemCombination;
	

	private final float promotionalPrice;

	
}
