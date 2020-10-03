/**
* This is the main class which accepts input from the console (promotions and cart-items)
* and calculates the total price of the cart based on promotions
* 
*
* @author  Sayan Bhattacharjee
* @version 1.0.
* @since   2020-10-03
*/
package com.sample.promotionengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import com.sample.promotionengine.core.CartItem;
import com.sample.promotionengine.core.Item;
import com.sample.promotionengine.core.Promotion;
import com.sample.promotionengine.exception.BusinessException;
import com.sample.promotionengine.service.PromotionComputeEngine;

import lombok.extern.slf4j.Slf4j;

/** The Constant log. */
@Slf4j
public class Application {

	/** The Constant ITEMS. */
	private static final Set<String> ITEMS = Stream.of(Item.values()).map(Item::name).collect(Collectors.toSet());

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		try (Scanner scanner = new Scanner(System.in)) {

			List<Promotion> promotions = addPromotions(scanner);

			List<CartItem> cartItems = addCartItems(scanner);

			PromotionComputeEngine promotionComputeEngine = new PromotionComputeEngine();
			promotionComputeEngine.compute(new ArrayList<>(cartItems), ListUtils.unmodifiableList(promotions));
			log.info("Bye");
		} catch (BusinessException be) {
			throw be;
		} catch (Exception e) {
			throw new BusinessException("Unable to handle input from console", e);
		}
	}

	/**
	 * Adds the promotions.
	 *
	 * @param scanner the scanner
	 * @return the list
	 */
	private static List<Promotion> addPromotions(Scanner scanner) {
		List<Promotion> promotions = new ArrayList<>();

		int i = 1;

		// Add promotion
		while (true) {

			log.info("Enter details of Promotion [" + i++ + "]");
			log.info("Enter the number of items associated with this promotion:");
			int noOfItems = scanner.nextInt();

			Map<Item, Integer> itemQuantities = new HashMap<>(noOfItems);

			// Add item and quantity in a promotion
			for (int iteration = 0; iteration < noOfItems; iteration++) {
				log.info("Item:");
				String itemStr = scanner.next();
				if (!ITEMS.contains(itemStr))
					throw new BusinessException("No item found with ID: " + itemStr);

				Item item = Item.valueOf(itemStr);

				if (itemQuantities.keySet().stream().anyMatch(it -> it == item))
					throw new BusinessException("Item [" + itemStr + "] already added for promotion in this iteration");

				if (promotions.stream().map(promotion -> promotion.getItemCombination().keySet())
						.flatMap(items -> items.stream()).anyMatch(it -> it == item))
					throw new BusinessException(
							"Promotion already added for item [" + itemStr + "] in another iteration");

				log.info("Quantity:");
				int quantity = scanner.nextInt();
				itemQuantities.put(item, quantity);
			}

			// Add promotional price
			log.info("Promotional Price:");
			float price = scanner.nextFloat();

			Promotion promotion = new Promotion(itemQuantities, price);

			promotions.add(promotion);

			log.info("Enter 'exit' to skip adding promotions, else enter any value to continue adding promotion...");
			if (StringUtils.equalsIgnoreCase("exit", scanner.next()))
				break;
		}
		return promotions;
	}

	/**
	 * Adds the cart items.
	 *
	 * @param scanner the scanner
	 * @return the list
	 */
	private static List<CartItem> addCartItems(Scanner scanner) {
		List<CartItem> cartItems = new ArrayList<>();
		log.info("Enter details of Cart");

		// Add items and quantities in cart
		while (true) {
			log.info("Item:");
			String itemStr = scanner.next();
			if (!ITEMS.contains(itemStr))
				throw new BusinessException("No item found with ID: " + itemStr);
			Item item = Item.valueOf(itemStr);
			log.info("Quantity:");
			int quantity = scanner.nextInt();
			cartItems.add(new CartItem(item, quantity));
			log.info(
					"Enter 'exit' to calculate total price of cart, else enter any value to continue adding items to cart...");
			if (StringUtils.equalsIgnoreCase("exit", scanner.next()))
				break;
		}
		return cartItems;
	}

}
