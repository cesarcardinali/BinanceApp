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


	public void updateOneMinuteCandle(CoinCandles candle) {
		if (priceHistory.size() >= 50) {
			priceHistory.remove(0);
		}

		candlesPerOneMinute = candle;

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
		float price = -1;
		if(candlesPerOneMinute != null) {
			System.out.println("candles " + candlesPerOneMinute.getCandles().size());
			price = candlesPerOneMinute.getCandles().get(candlesPerOneMinute.getCandles().size() - 1).getClosePrice();
		}	

		return price;
	}

}
