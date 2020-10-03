/**
* This is a test class used to test the PromotionComputationEngine class.
* 
*
* @author  Sayan Bhattacharjee
* @version 1.0.
* @since   2020-10-03
*/
package com.sample.promotionengine.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ListUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.sample.promotionengine.core.CartItem;
import com.sample.promotionengine.core.Item;
import com.sample.promotionengine.core.Promotion;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PromotionComputeEngineTest {

	/** The promotion compute engine. */
	private PromotionComputeEngine promotionComputeEngine;

	/** The promotions. */
	List<Promotion> promotions = null;

	/**
	 * Initializes the attributes before a test case execution.
	 */
	@Before
	public void init() {
		promotionComputeEngine = new PromotionComputeEngine();

		// Promotion for Item A
		Map<Item, Integer> itemCombinations1 = new HashMap<>(1);
		itemCombinations1.put(Item.A, 3);
		Promotion promotion1 = new Promotion(itemCombinations1, 130);

		// Promotion for Item B
		Map<Item, Integer> itemCombinations2 = new HashMap<>(1);
		itemCombinations2.put(Item.B, 2);
		Promotion promotion2 = new Promotion(itemCombinations2, 45);

		// Combined Promotion for Item C & D
		Map<Item, Integer> itemCombinations3 = new HashMap<>(2);
		itemCombinations3.put(Item.C, 1);
		itemCombinations3.put(Item.D, 1);
		Promotion promotion3 = new Promotion(itemCombinations3, 30);

		// Promotion list
		promotions = new ArrayList<>(3);
		promotions.add(promotion1);
		promotions.add(promotion2);
		promotions.add(promotion3);
	}

	/**
	 * Cart items for scenario 1.
	 *
	 * @return the list
	 */
	private List<CartItem> cartItemsForScenario1() {

		CartItem cartItem1 = new CartItem(Item.A, 1);
		CartItem cartItem2 = new CartItem(Item.B, 1);
		CartItem cartItem3 = new CartItem(Item.C, 1);

		List<CartItem> cartItems = new ArrayList<CartItem>(3);
		cartItems.add(cartItem1);
		cartItems.add(cartItem2);
		cartItems.add(cartItem3);
		return cartItems;

	}

	/**
	 * Cart items for scenario 2.
	 *
	 * @return the list
	 */
	private List<CartItem> cartItemsForScenario2() {

		CartItem cartItem1 = new CartItem(Item.A, 5);
		CartItem cartItem2 = new CartItem(Item.B, 5);
		CartItem cartItem3 = new CartItem(Item.C, 1);

		List<CartItem> cartItems = new ArrayList<CartItem>(3);
		cartItems.add(cartItem1);
		cartItems.add(cartItem2);
		cartItems.add(cartItem3);
		return cartItems;

	}

	/**
	 * Cart items for scenario 3.
	 *
	 * @return the list
	 */
	private List<CartItem> cartItemsForScenario3() {

		CartItem cartItem1 = new CartItem(Item.A, 3);
		CartItem cartItem2 = new CartItem(Item.B, 5);
		CartItem cartItem3 = new CartItem(Item.C, 1);
		CartItem cartItem4 = new CartItem(Item.D, 1);

		List<CartItem> cartItems = new ArrayList<CartItem>(4);
		cartItems.add(cartItem1);
		cartItems.add(cartItem2);
		cartItems.add(cartItem3);
		cartItems.add(cartItem4);
		return cartItems;

	}

	/**
	 * Test 01 compute with no items in cart.
	 */
	@Test
	public void test01_compute() {

		log.info("TC01 - Test case with empty cart items");

		List<CartItem> cartItems = Collections.emptyList();
		assertEquals(0, promotionComputeEngine.compute(new ArrayList<>(cartItems), null), 0);
	}

	/**
	 * Test 02 compute with no promotion.
	 */
	@Test
	public void test02_compute() {

		log.info("TC02 - Test cases with cart items and no promotion");

		log.info("TC02 - Executing test case for Scenario 1");
		assertEquals(100, promotionComputeEngine.compute(new ArrayList<>(this.cartItemsForScenario1()), null), 0);

		log.info("TC02 - Executing test case for Scenario 2");
		assertEquals(420, promotionComputeEngine.compute(new ArrayList<>(this.cartItemsForScenario2()), null), 0);

		log.info("TC02 - Executing test case for Scenario 3");
		assertEquals(335, promotionComputeEngine.compute(new ArrayList<>(this.cartItemsForScenario3()), null), 0);
	}

	/**
	 * Test 03 compute with empty promotion list.
	 */
	@Test
	public void test03_compute() {

		log.info("TC03 - Test cases with cart items and empty promotion list");

		log.info("TC03 - Executing test case for Scenario 1");
		assertEquals(100,
				promotionComputeEngine.compute(new ArrayList<>(this.cartItemsForScenario1()), Collections.emptyList()),
				0);

		log.info("TC03 - Executing test case for Scenario 2");
		assertEquals(420,
				promotionComputeEngine.compute(new ArrayList<>(this.cartItemsForScenario2()), Collections.emptyList()),
				0);

		log.info("TC03 - Executing test case for Scenario 3");
		assertEquals(335,
				promotionComputeEngine.compute(new ArrayList<>(this.cartItemsForScenario3()), Collections.emptyList()),
				0);
	}

	/**
	 * Test 04 compute with promotions.
	 */
	@Test
	public void test04_compute() {

		log.info("TC04 - Test case with cart items and promotion list");

		log.info("TC04 - Executing test case for Scenario 1");
		assertEquals(100, promotionComputeEngine.compute(new ArrayList<>(this.cartItemsForScenario1()),
				ListUtils.unmodifiableList(promotions)), 0);

		log.info("TC04 - Executing test case for Scenario 2");
		assertEquals(370, promotionComputeEngine.compute(new ArrayList<>(this.cartItemsForScenario2()),
				ListUtils.unmodifiableList(promotions)), 0);

		log.info("TC04 - Executing test case for Scenario 3");
		assertEquals(280, promotionComputeEngine.compute(new ArrayList<>(this.cartItemsForScenario3()),
				ListUtils.unmodifiableList(promotions)), 0);
	}

}
