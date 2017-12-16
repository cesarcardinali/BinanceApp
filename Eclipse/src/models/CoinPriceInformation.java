package models;

public class CoinPriceInformation {
	/*{
	  "priceChange": "-94.99999800",
	  "priceChangePercent": "-95.960",
	  "weightedAvgPrice": "0.29628482",
	  "prevClosePrice": "0.10002000",
	  "lastPrice": "4.00000200",
	  "bidPrice": "4.00000000",
	  "askPrice": "4.00000200",
	  "openPrice": "99.00000000",
	  "highPrice": "100.00000000",
	  "lowPrice": "0.10000000",
	  "volume": "8913.30000000",
	  "openTime": 1499783499040,
	  "closeTime": 1499869899040,
	  "fristId": 28385,   // First tradeId
	  "lastId": 28460,    // Last tradeId
	  "count": 76         // Trade count
	}*/
	
	// Variables -----------------------------------------------------------------------------------------
	float priceChange;
	float priceChangePercent;
	float weightedAvgPrice;
	float prevClosePrice;
	float lastPrice;
	float bidPrice;
	float askPrice;
	float openPrice;
	float highPrice;
	float lowPrice;
	float volume;
	long openTime;
	long closeTime;
	long firstId;
	long lastId;
	long count;

	
	// Main Methods --------------------------------------------------------------------------------------
	
	
	
	// Getters and Setters -------------------------------------------------------------------------------
	public float getPriceChange() {
		return priceChange;
	}
	public void setPriceChange(float priceChange) {
		this.priceChange = priceChange;
	}
	public float getPriceChangePercent() {
		return priceChangePercent;
	}
	public void setPriceChangePercent(float priceChangePercent) {
		this.priceChangePercent = priceChangePercent;
	}
	public float getWeightedAvgPrice() {
		return weightedAvgPrice;
	}
	public void setWeightedAvgPrice(float weightedAvgPrice) {
		this.weightedAvgPrice = weightedAvgPrice;
	}
	public float getPrevClosePrice() {
		return prevClosePrice;
	}
	public void setPrevClosePrice(float prevClosePrice) {
		this.prevClosePrice = prevClosePrice;
	}
	public float getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(float lastPrice) {
		this.lastPrice = lastPrice;
	}
	public float getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(float bidPrice) {
		this.bidPrice = bidPrice;
	}
	public float getAskPrice() {
		return askPrice;
	}
	public void setAskPrice(float askPrice) {
		this.askPrice = askPrice;
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
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
	public long getOpenTime() {
		return openTime;
	}
	public void setOpenTime(long openTime) {
		this.openTime = openTime;
	}
	public long getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(long closeTime) {
		this.closeTime = closeTime;
	}
	public long getFirstId() {
		return firstId;
	}
	public void setFirstId(long firstId) {
		this.firstId = firstId;
	}
	public long getLastId() {
		return lastId;
	}
	public void setLastId(long lastId) {
		this.lastId = lastId;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
}
