package com.sample.promotionengine.core;

public enum Item {

	A(50), B(30), C(20), D(10);

	private int price;

	public int getPrice() {
		return this.price;
	}

	private Item(int price) {
		this.price = price;
	}
	
	

}
