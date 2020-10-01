package com.sample.promotionengine.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.sample.promotionengine.core.CartItem;
import com.sample.promotionengine.core.Item;
import com.sample.promotionengine.core.Promotion;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PromotionComputeEngineTest {

	private PromotionComputeEngine promotionComputeEngine;
	List<Promotion> promotions = null;

	@Before
	public void init() {
		promotionComputeEngine = new PromotionComputeEngine();
		
		// Promotion for Item A
		Map<Item, Integer> itemQuantities1 = new HashMap<>(1);
		itemQuantities1.put(Item.A, 3);
		Promotion promotion1 = new Promotion(itemQuantities1, 130);
		
		// Promotion for Item B
		Map<Item, Integer> itemQuantities2 = new HashMap<>(1);
		itemQuantities2.put(Item.B, 2);
		Promotion promotion2 = new Promotion(itemQuantities2, 45);
		
		// Promotion for Item C & D
		Map<Item, Integer> itemQuantities3 = new HashMap<>(2);
		itemQuantities3.put(Item.C, 1);
		itemQuantities3.put(Item.D, 1);
		Promotion promotion3 = new Promotion(itemQuantities3, 30);
		
		// Promotion list
		promotions = new ArrayList<>(3);
		promotions.add(promotion1);
		promotions.add(promotion2);
		promotions.add(promotion3);
	}

	@Test
	public void test01_compute() {
		
		log.info("Executing test case with empty cart items");
		
		List<CartItem> cartItems = Collections.emptyList();
		assertEquals(0, promotionComputeEngine.compute(cartItems), 0);
	}

}
