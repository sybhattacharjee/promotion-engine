/**
* This class tests the the total value of the cart based on various promotions.
* 
*
* @author  Sayan Bhattacharjee
* @version 1.0.
* @since   2020-10-03
*/
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

	/**
	 * Compute.
	 *
	 * @param cartItems  the cart items
	 * @param promotions the promotions
	 * @return the float
	 */
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

	/**
	 * Compute with promotion.
	 *
	 * @param cartItems  the cart items
	 * @param promotions the promotions
	 * @return the float
	 */
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

	/**
	 * Compute items with combined promotion.
	 *
	 * @param cartItems the cart items
	 * @param total     the total
	 * @param promotion the promotion
	 * @return the float
	 */
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
			
			int noOfUnitsEligibleForPromotion = 0;
			
			for (Map.Entry<CartItem, Integer> cartItemForCombinedPromotion : cartItemsForCombinedPromotion.entrySet()) {
				
				noOfUnitsEligibleForPromotion = cartItemForCombinedPromotion.getKey().getQuantity()/cartItemForCombinedPromotion.getValue();
				total += (cartItemForCombinedPromotion.getKey().getQuantity()%cartItemForCombinedPromotion.getValue())
						* cartItemForCombinedPromotion.getKey().getItem().getPrice();
				cartItems.remove(cartItemForCombinedPromotion.getKey());
			}
			
			total += promotionalPrice * noOfUnitsEligibleForPromotion;

			log.debug("Intermediate total after calculating items [{}] with promotion: {}",
					cartItemsForCombinedPromotion.keySet().stream().map(ci -> ci.getItem().name())
							.collect(Collectors.joining(",")),
					total);
		}

		return total;
	}

	/**
	 * Compute items with single promotion.
	 *
	 * @param cartItems the cart items
	 * @param total     the total
	 * @param promotion the promotion
	 * @return the float
	 */
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

	/**
	 * Compute items without promotion.
	 *
	 * @param cartItems the cart items
	 * @param total     the total
	 * @return the float
	 */
	private float computeItemsWithoutPromotion(List<CartItem> cartItems, final float total) {
		float subtotal = 0;
		for (CartItem cartItem : cartItems) {
			subtotal += cartItem.getQuantity() * cartItem.getItem().getPrice();
			log.debug("Sub-total of item [{}] without applying promotion: {}", cartItem.getItem(), subtotal);
		}
		return subtotal;
	}

}
