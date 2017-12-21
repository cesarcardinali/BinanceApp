package models;

import org.json.simple.JSONArray;


public class CoinCandleTick {

	/*
	 * [ 1499040000000, // Open time "0.01634790", // Open "0.80000000", // High "0.01575800", // Low "0.01577100", // Close "148976.11427815", // Volume 1499644799999, // Close time "2434.19055334", // Quote asset volume 308, // Number of trades "1756.87402397", // Taker buy base asset volume "28.46694368", // Taker buy quote asset volume "17928899.62484339" // Can be ignored ]
	 */
	long openTime;
	float openPrice;
	float highPrice;
	float lowPrice;
	float closePrice;
	long closeTime;
	float volume;
	long trades;
	float takerBaseVolume;
	float takerQuoteVolume;
	String tickResult;


	public CoinCandleTick() {
	}


	public CoinCandleTick(JSONArray data) {
		openTime = (long) data.get(0);
		openPrice = Float.parseFloat((String) data.get(1));
		highPrice = Float.parseFloat((String) data.get(2));
		lowPrice = Float.parseFloat((String) data.get(3));
		closePrice = Float.parseFloat((String) data.get(4));
		volume = Float.parseFloat((String) data.get(5));
		closeTime = (long) data.get(6);
		trades = (long) data.get(8);
		takerBaseVolume = Float.parseFloat((String) data.get(9));
		takerQuoteVolume = Float.parseFloat((String) data.get(10));
		if(openPrice < closePrice) {
			tickResult = "G";
		} else if(openPrice > closePrice) {
			tickResult = "R";
		} else {
			tickResult = "N";
		}
	}


	public long getOpenTime() {
		return openTime;
	}


	public void setOpenTime(long openTime) {
		this.openTime = openTime;
	}


	public float getOpenPrice() {
		return openPrice;
	}


	public void setOpenPrice(float openPrice) {
		this.openPrice = openPrice;
	}


	public float getHighPrice() {
		return highPrice;
	}


	public void setHighPrice(float highPrice) {
		this.highPrice = highPrice;
	}


	public float getLowPrice() {
		return lowPrice;
	}


	public void setLowPrice(float lowPrice) {
		this.lowPrice = lowPrice;
	}


	public float getClosePrice() {
		return closePrice;
	}


	public void setClosePrice(float closePrice) {
		this.closePrice = closePrice;
	}


	public long getCloseTime() {
		return closeTime;
	}


	public void setCloseTime(long closeTime) {
		this.closeTime = closeTime;
	}


	public float getVolume() {
		return volume;
	}


	public void setVolume(float volume) {
		this.volume = volume;
	}


	public long getTrades() {
		return trades;
	}


	public void setTrades(long trades) {
		this.trades = trades;
	}


	public float getTakerBaseVolume() {
		return takerBaseVolume;
	}


	public void setTakerBaseVolume(float takerBaseVolume) {
		this.takerBaseVolume = takerBaseVolume;
	}


	public float getTakerQuoteVolume() {
		return takerQuoteVolume;
	}


	public void setTakerQuoteVolume(float takerQuoteVolume) {
		this.takerQuoteVolume = takerQuoteVolume;
	}

	
	public String getTickResult() {
		return tickResult;
	}


	
	public void setTickResult(String result) {
		this.tickResult = result;
	}

	
}
