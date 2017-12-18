package models;

import java.util.HashMap;

public class Wallet {
	
	HashMap<String, Coin> currencies; // Coin name and quantity
	float totalBtc;
	float totalDollar;
	
	public Wallet() {}
	
	public Wallet(float dolars, float btcs) {
		totalDollar = dolars;
		totalBtc = btcs;
		currencies = new HashMap<String, Coin>();
	}

	
	public HashMap<String, Coin> getCurrencies() {
		return currencies;
	}

	
	public void setCurrencies(HashMap<String, Coin> currencies) {
		this.currencies = currencies;
	}

	
	public float getTotalBtc() {
		return totalBtc;
	}

	
	public void setTotalBtc(float totalBtc) {
		this.totalBtc = totalBtc;
	}

	
	public float getTotalDollar() {
		return totalDollar;
	}

	
	public void setTotalDollar(float totalDollar) {
		this.totalDollar = totalDollar;
	}
	
	
	
}
