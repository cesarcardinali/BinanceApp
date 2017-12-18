package models;

import java.util.ArrayList;


public class Coin {

	ArrayList<CoinPriceInformation> priceHistory;
	int historyLimit = 50;

	CoinCandles candlesPerOneMinute;
	CoinCandles candlesPerThreeMinutes;
	CoinCandles candlesPerFiveMinutes;
	CoinCandles candlesPerFifteenMinutes;
	CoinCandles candlesPerThirtyMinutes;
	CoinCandles candlesPerSixtyMinutes;

	float price;
	float quantity;


	public Coin() {
		priceHistory = new ArrayList<CoinPriceInformation>();
	}


	public void updatePrices(CoinPriceInformation priceUpdate) {
		if (priceHistory.size() >= 50) {
			priceHistory.remove(0);
		}

		priceHistory.add(priceUpdate);
	}


	public float getLastClosePrice() {
		float price = -1;
		price = priceHistory.get(priceHistory.size() - 2).getPrevClosePrice();

		return price;
	}
	
	public float getActualClosePrice() {
		float price = -1;
		price = priceHistory.get(priceHistory.size() - 1).getPrevClosePrice();

		return price;
	}

}
