package models;

import org.json.simple.JSONArray;

public class CoinCandleTick {
	/*
	  [
	    1499040000000,      // Open time
	    "0.01634790",       // Open
	    "0.80000000",       // High
	    "0.01575800",       // Low
	    "0.01577100",       // Close
	    "148976.11427815",  // Volume
	    1499644799999,      // Close time
	    "2434.19055334",    // Quote asset volume
	    308,                // Number of trades
	    "1756.87402397",    // Taker buy base asset volume
	    "28.46694368",      // Taker buy quote asset volume
	    "17928899.62484339" // Can be ignored
	  ]
	 */
	
	long openTime;
	float openPrice;
	float highPrice;
	float lowPrice;
	float closePrice;
	long closeTime;
	float volume;
	int trades;
	float takerBaseVolume;
	float takerQuoteVolume;
	
	
	public CoinCandleTick() {}
	
	
	public CoinCandleTick(JSONArray data) {
		openTime = Long.parseLong((String) data.get(0));
		openPrice = Float.parseFloat((String) data.get(1));
		highPrice = Float.parseFloat((String) data.get(2));
		lowPrice = Float.parseFloat((String) data.get(3));
		closePrice = Float.parseFloat((String) data.get(4));
		closeTime = Long.parseLong((String) data.get(5));
		volume = Float.parseFloat((String) data.get(6));
		trades = Integer.parseInt((String) data.get(7));
		takerBaseVolume = Float.parseFloat((String) data.get(8));
		takerQuoteVolume = Float.parseFloat((String) data.get(9));
	}
}
