package models;

import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Wallet {
	
	HashMap<String, Coin> currencies; // Coin name and quantity
	float totalBtc;
	float totalDollar;
	
	public Wallet() {
		totalDollar = 0;
		totalBtc = 0;
		currencies = new HashMap<String, Coin>();
	}
	
	public Wallet(float dolars, float btcs) {
		totalDollar = dolars;
		totalBtc = btcs;
		currencies = new HashMap<String, Coin>();
	}

	
	public Wallet(JSONObject json) {
		currencies = new HashMap<String, Coin>();
		
		JSONArray coins = (JSONArray) json.get("balances");
		for(int i = 0; i < coins.size(); i++) {
			JSONObject coinData = (JSONObject) coins.get(i);
			Coin tmp = new Coin(coinData);
			if (tmp.getQuantityFree() + tmp.getQuantityLocked() > 0 && !tmp.getName().contains("BCX") && !tmp.getName().contains("SBTC")) {
				currencies.put(tmp.getName(), tmp);
				System.out.println(tmp.getName() + ": " + (tmp.getQuantityFree() + tmp.getQuantityLocked()));
			}
		}
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
