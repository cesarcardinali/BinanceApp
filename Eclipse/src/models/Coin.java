package models;

import java.util.ArrayList;
import org.json.simple.JSONObject;


public class Coin {

	// Variables ----------------------------------------------------------------------------------------------------------------------------
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
	String trend3m;


	// Constructor ---------------------------------------------------------------------------------------------------------------------------
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
		if (name.equals("BTC")) {
			name = name + "USDT";
		} else {
			name = name + "BTC";
		}
	}


	// Main methods --------------------------------------------------------------------------------------------------------------------------
	public void updatePrices(CoinPriceInformation priceUpdate) {
		if (priceHistory.size() >= 50) {
			priceHistory.remove(0);
		}

		priceHistory.add(priceUpdate);
	}


	public void updateOneMinuteCandle(CoinCandles candle) {
		candlesPerOneMinute = candle;
		price = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 1).getClosePrice();

		updateTrend3m();
	}


	public String updateTrend3m() {
		String candleResult1 = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 2).getTickResult();
		String candleResult2 = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 3).getTickResult();
		String candleResult3 = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 4).getTickResult();
		float candleClose1 = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 2).getClosePrice();
		float candleClose2 = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 3).getClosePrice();
		float candleClose3 = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 4).getClosePrice();

		if (candleResult1.equals("G") && candleClose1 > candleClose2) {
			if (candleResult2.equals("G") && candleClose2 > candleClose3) {
				if (!candleResult3.equals("G")) {
					trend3m = "Rising 2/3";
				} else {
					trend3m = "Rising 3/3";
				}
			}
		} else if (candleResult1.equals("R")) {
			if (candleResult2.equals("R")) {
				if (candleResult3.equals("R")) {
					trend3m = "Droping 3/3";
				} else {
					trend3m = "Droping 2/3";
				}
			}
		} else {
			trend3m = "Neutral";
		}

		return trend3m;
	}
	
	public String[][] getLast6CandlesResolution() {
		if(candlesPerOneMinute == null) {
			return null;
		}
		
		String [][] data = new String[6][2];
		data[0][0] = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 2).getTickResult();
		data[1][0] = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 3).getTickResult();
		data[2][0] = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 4).getTickResult();
		data[3][0] = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 5).getTickResult();
		data[4][0] = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 6).getTickResult();
		data[5][0] = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 7).getTickResult();
		
		for (int i = 2; i < 8; i++) {
			float p1 = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - i).getClosePrice();
			float p2 = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - i-1).getClosePrice();
			if (p1 > p2)
				data[i-2][1] = "H";
			else
				data[i-2][1] = "L";
		}
		
		
		return data;
	}

	
	public float getHighest24hPrice() {
		return priceHistory.get(priceHistory.size()-1).getHighPrice();
	}
	

	public float getLastClosePrice() {
		float price = -1;
		if (candlesPerOneMinute != null) {
			price = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 2).getClosePrice();
		}

		return price;
	}


	public float getLastOpenPrice() {
		float price = -1;
		if (candlesPerOneMinute != null) {
			price = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 2).getOpenPrice();
		}

		return price;
	}


	public float getActualClosePrice() {
		price = -1;
		if (candlesPerOneMinute != null) {
			price = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 1).getClosePrice();
		}

		return price;
	}


	public long getLastMinuteTrades() {
		return candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 1).getTrades();
	}
	
	
	public float getAveragePriceInLastXMinutes(int minutes) {
		float avg = 0;
		for (int i = 1; i <= minutes; i++) {
			avg += candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() -  i).getClosePrice();
		}
		 
		return avg / minutes;
	}
	


	// Getters and Setters ------------------------------------------------------------------------------------------------------------------------
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


	public String getTrend3m() {
		return trend3m;
	}


	public void setTrend3m(String trend3m) {
		this.trend3m = trend3m;
	}
}
