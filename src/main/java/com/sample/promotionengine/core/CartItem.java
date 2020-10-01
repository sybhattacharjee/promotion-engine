package com.sample.promotionengine.core;

import lombok.Data;

@Data
public class CartItem {

	private final Item item;

	private final int quantity;


}
