package com.sample.promotionengine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.sample.promotionengine.core.CartItem;
import com.sample.promotionengine.core.Item;
import com.sample.promotionengine.core.Promotion;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PromotionComputeEngine {

	public float compute(List<CartItem> cartItems, List<Promotion> promotions) {

		float total = 0;

		if (CollectionUtils.isEmpty(cartItems)) {
			log.debug("Total of all items in cart: {}", total);
			return total;
		}

		if (CollectionUtils.isEmpty(promotions)) {
			total += computeItemsWithoutPromotion(cartItems, total);
			log.debug("Total of all items in cart: {}", total);
			return total;
		}

		total = this.computeWithPromotion(cartItems, promotions);
		log.debug("Total of all items in cart: {}", total);
		return total;
	}

	private float computeWithPromotion(List<CartItem> cartItems, List<Promotion> promotions) {
		float total = 0;
		for (Promotion promotion : promotions) {
			if (promotion.getItemCombination().entrySet().size() == 1) {
				total = computeItemsWithSinglePromotion(cartItems, total, promotion);
			} else {
				total = computeItemsWithCombinedPromotion(cartItems, total, promotion);
			}
		}

		if (CollectionUtils.isNotEmpty(cartItems)) {
			total += this.computeItemsWithoutPromotion(cartItems, total);
			log.debug("Intermediate total after calculating items [{}] without promotion: {}",
					cartItems.stream().map(ci -> ci.getItem().name()).collect(Collectors.joining(",")), total);
		}

		return total;
	}

	private float computeItemsWithCombinedPromotion(List<CartItem> cartItems, float total, Promotion promotion) {
		float promotionalPrice = promotion.getPromotionalPrice();
		Map<CartItem, Integer> cartItemsForCombinedPromotion = new HashMap<>();
		boolean combinedItemsExists = false;
		for (Map.Entry<Item, Integer> entry : promotion.getItemCombination().entrySet()) {
			Optional<CartItem> oTemp = cartItems.stream().filter(c -> c.getItem() == entry.getKey()).findFirst();
			if (oTemp.isPresent()) {
				cartItemsForCombinedPromotion.put(oTemp.get(), entry.getValue());
				combinedItemsExists = true;
			} else {
				combinedItemsExists = false;
				break;
			}

		}

		if (combinedItemsExists) {
			total += promotionalPrice;
			for (Map.Entry<CartItem, Integer> cartItemForCombinedPromotion : cartItemsForCombinedPromotion.entrySet()) {
				cartItems.remove(cartItemForCombinedPromotion.getKey());
			}

			log.debug("Intermediate total after calculating items [{}] with promotion: {}",
					cartItemsForCombinedPromotion.keySet().stream().map(ci -> ci.getItem().name())
							.collect(Collectors.joining(",")),
					total);
		}

		return total;
	}

	private float computeItemsWithSinglePromotion(List<CartItem> cartItems, float total, Promotion promotion) {
		for (CartItem cartItem : cartItems) {
			Integer promotionalQuantityForItem = promotion.getItemCombination().get(cartItem.getItem());
			if (null != promotionalQuantityForItem) {
				float promotionalPrice = promotion.getPromotionalPrice();
				total += (cartItem.getQuantity() / promotionalQuantityForItem) * promotionalPrice
						+ (cartItem.getQuantity() % promotionalQuantityForItem) * cartItem.getItem().getPrice();
				log.debug("Intermediate total after calculating item [{}]: {}", cartItem.getItem(), total);
				cartItems.remove(cartItem);
				break;

			}
		}
		return total;
	}

	private float computeItemsWithoutPromotion(List<CartItem> cartItems, final float total) {
		float subtotal = 0;
		for (CartItem cartItem : cartItems) {
			subtotal += cartItem.getQuantity() * cartItem.getItem().getPrice();
			log.debug("Sub-total of item [{}] without applying promotion: {}", cartItem.getItem(), subtotal);
		}
		return subtotal;
	}

}
