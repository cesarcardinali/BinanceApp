package models;

import java.util.ArrayList;
import org.json.simple.JSONObject;


public class Coin {

	ArrayList<CoinPriceInformation> priceHistory;
	
	CoinCandles candlesPerOneMinute;
	CoinCandles candlesPerThreeMinutes;
	CoinCandles candlesPerFiveMinutes;
	CoinCandles candlesPerFifteenMinutes;
	CoinCandles candlesPerThirtyMinutes;
	CoinCandles candlesPerSixtyMinutes;

	float price;
	float quantityFree;
	float quantityLocked;
	String name;


	public Coin() {
		priceHistory = new ArrayList<CoinPriceInformation>();
		price = -1;
	}
	
	public Coin(JSONObject data) {
		priceHistory = new ArrayList<CoinPriceInformation>();
		price = -1;
		quantityFree = Float.parseFloat((String) data.get("free"));
		quantityLocked = Float.parseFloat((String) data.get("locked"));
		name = (String) data.get("asset");
		if(name.equals("BTC")) {
			name = name + "USDT";
		} else {
			name = name + "BTC";
		}
	}


	public void updatePrices(CoinPriceInformation priceUpdate) {
		if (priceHistory.size() >= 50) {
			priceHistory.remove(0);
		}

		priceHistory.add(priceUpdate);
	}


	public void updateOneMinuteCandle(CoinCandles candle) {
		if (priceHistory.size() >= 50) {
			priceHistory.remove(0);
		}

		candlesPerOneMinute = candle;
		price = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 1).getClosePrice();
	}


	public float getLastClosePrice() {
		float price = -1;
		if(candlesPerOneMinute != null) {
			System.out.println("candles " + candlesPerOneMinute.getCandles().size());
			price = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 2).getClosePrice();
		}

		return price;
	}


	public float getActualClosePrice() {
		price = -1;
		if(candlesPerOneMinute != null) {
			System.out.println("candles " + candlesPerOneMinute.getCandles().size());
			price = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 1).getClosePrice();
		}	

		return price;
	}


	public long getLastMinuteTrades() {
		return candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 1).getTrades();
	}

	
	public ArrayList<CoinPriceInformation> getPriceHistory() {
		return priceHistory;
	}

	
	public void setPriceHistory(ArrayList<CoinPriceInformation> priceHistory) {
		this.priceHistory = priceHistory;
	}

	
	public CoinCandles getCandlesPerOneMinute() {
		return candlesPerOneMinute;
	}

	
	public void setCandlesPerOneMinute(CoinCandles candlesPerOneMinute) {
		this.candlesPerOneMinute = candlesPerOneMinute;
	}

	
	public CoinCandles getCandlesPerThreeMinutes() {
		return candlesPerThreeMinutes;
	}

	
	public void setCandlesPerThreeMinutes(CoinCandles candlesPerThreeMinutes) {
		this.candlesPerThreeMinutes = candlesPerThreeMinutes;
	}

	
	public CoinCandles getCandlesPerFiveMinutes() {
		return candlesPerFiveMinutes;
	}

	
	public void setCandlesPerFiveMinutes(CoinCandles candlesPerFiveMinutes) {
		this.candlesPerFiveMinutes = candlesPerFiveMinutes;
	}

	
	public CoinCandles getCandlesPerFifteenMinutes() {
		return candlesPerFifteenMinutes;
	}

	
	public void setCandlesPerFifteenMinutes(CoinCandles candlesPerFifteenMinutes) {
		this.candlesPerFifteenMinutes = candlesPerFifteenMinutes;
	}

	
	public CoinCandles getCandlesPerThirtyMinutes() {
		return candlesPerThirtyMinutes;
	}

	
	public void setCandlesPerThirtyMinutes(CoinCandles candlesPerThirtyMinutes) {
		this.candlesPerThirtyMinutes = candlesPerThirtyMinutes;
	}

	
	public CoinCandles getCandlesPerSixtyMinutes() {
		return candlesPerSixtyMinutes;
	}

	
	public void setCandlesPerSixtyMinutes(CoinCandles candlesPerSixtyMinutes) {
		this.candlesPerSixtyMinutes = candlesPerSixtyMinutes;
	}

	
	public float getPrice() {
		return price;
	}

	
	public void setPrice(float price) {
		this.price = price;
	}

	
	public float getQuantityFree() {
		return quantityFree;
	}

	
	public void setQuantityFree(float quantityFree) {
		this.quantityFree = quantityFree;
	}

	
	public float getQuantityLocked() {
		return quantityLocked;
	}

	
	public void setQuantityLocked(float quantityLocked) {
		this.quantityLocked = quantityLocked;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}
}
