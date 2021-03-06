package models;

import java.util.HashMap;
import java.util.concurrent.Semaphore;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Wallet {
	
	HashMap<String, Coin> currencies; // Coin name and quantity
	float totalBtc;
	float totalDollar;
	
	private Semaphore s;
	
	
	public Wallet() {
		totalDollar = 0;
		totalBtc = 0;
		currencies = new HashMap<String, Coin>();
		
		s = new Semaphore(1);
	}
	
	public Wallet(float dolars, float btcs) {
		totalDollar = dolars;
		totalBtc = btcs;
		currencies = new HashMap<String, Coin>();
		
		s = new Semaphore(1);
	}

	
	public Wallet(JSONObject json) {
		s = new Semaphore(1);
		try {
			s.acquire();
			
			currencies = new HashMap<String, Coin>();
			
			JSONArray coins = (JSONArray) json.get("balances");
			for(int i = 0; i < coins.size(); i++) {
				JSONObject coinData = (JSONObject) coins.get(i);
				Coin tmp = new Coin(coinData);
				if (tmp.getName().startsWith("BTC") || tmp.getName().startsWith("ETH") || tmp.getName().startsWith("XMR") || tmp.getName().startsWith("NEO")) {
					currencies.put(tmp.getName(), tmp);
					System.out.println(tmp.getName() + ": " + (tmp.getQuantityFree() + tmp.getQuantityLocked()));
				} else if (tmp.getQuantityFree() + tmp.getQuantityLocked() > 1 && !tmp.getName().contains("BCX") && !tmp.getName().contains("SBTC") && !tmp.getName().contains("USDT")) {
					currencies.put(tmp.getName(), tmp);
					System.out.println(tmp.getName() + ": " + (tmp.getQuantityFree() + tmp.getQuantityLocked()));
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		s.release();
	}
	
	
	public void semaphoreAcq() {
		try {
			s.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void semaphoreRel() {
		s.release();
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
