package models;

import java.util.ArrayList;

import org.json.simple.JSONArray;


public class CoinCandles {
	// Variables
	ArrayList<CoinCandleTick> candles;
	// ------------------------------------------------------------------------------------------

	
	// Constructors
	public CoinCandles(JSONArray data) {
		candles = new ArrayList<CoinCandleTick>();
		
		for(int i = 0; i < data.size(); i++) {
			JSONArray tick = (JSONArray) data.get(i);
			candles.add(new CoinCandleTick(tick));
		}
	}

	public CoinCandles() {

	}
	// ------------------------------------------------------------------------------------------
}
