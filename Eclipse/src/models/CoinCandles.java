package models;

import java.util.ArrayList;

import org.json.simple.JSONArray;


public class CoinCandles {
	// Variables
	ArrayList<CoinCandleTick> candleTicks;
	// ------------------------------------------------------------------------------------------

	
	// Constructors -----------------------------------------------------------------------------
	public CoinCandles(JSONArray data) {
		candleTicks = new ArrayList<CoinCandleTick>();
		
		for(int i = 0; i < data.size(); i++) {
			JSONArray tick = (JSONArray) data.get(i);
			candleTicks.add(new CoinCandleTick(tick));
		}
	}

	public CoinCandles() {

	}
	// ------------------------------------------------------------------------------------------

	
	public ArrayList<CoinCandleTick> getCandles() {
		return candleTicks;
	}

	
	public void setCandles(ArrayList<CoinCandleTick> candles) {
		this.candleTicks = candles;
	}
}
