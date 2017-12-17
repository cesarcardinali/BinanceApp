package models;

import java.util.HashMap;

public class Wallet {
	
	HashMap<String, Float> currencies; // Coin name and quantity
	float totalBtc;
	float totalDollar;
	
	public Wallet() {}
	
	public Wallet(float dolars, float btcs) {
		totalDollar = dolars;
		totalBtc = btcs;
		currencies = new HashMap<String, Float>();
	}
	
}
