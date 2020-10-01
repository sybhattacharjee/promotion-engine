package com.sample.promotionengine.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.sample.promotionengine.core.CartItem;

public class PromotionComputeEngine {

	public float compute(List<CartItem> cartItems) {

		float total = 0;

		if (CollectionUtils.isEmpty(cartItems))
			return 0;
		
		for(CartItem cartItem : cartItems) {
			total+= cartItem.getQuantity() * cartItem.getItem().getPrice();
		}

		return total;
	}

}
