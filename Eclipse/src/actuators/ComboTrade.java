package actuators;

import java.text.DecimalFormat;
import models.AppData;
import models.Coin;
import models.Wallet;
import supportive.BinanceApi;


public class ComboTrade implements Runnable {

	AppData appData;
	Wallet wallet;
	BinanceApi binance;

	boolean done = false;
	String symbol;
	float buyPrice;
	float buyLimit;
	float sellPrice;
	float sellLimit;
	float quantity;
	boolean bought = false;
	boolean sold = false;

	float actualPrice;
	float lastPrice;
	boolean alert = false;
	long startTime;


	public ComboTrade(AppData appData, String symbol, String quantity, String buy, String sell, String buyLimit, String sellLimit) {
		this.appData = appData;
		wallet = appData.getWallet();
		binance = appData.getBinance();

		this.symbol = symbol;
		this.quantity = Float.parseFloat(quantity);
		this.buyPrice = Float.parseFloat(buy);
		this.sellPrice = Float.parseFloat(sell);

		if (sellLimit.contains("%")) {
			sellLimit = sellLimit.replace("%", "");
			this.sellLimit = sellPrice + (sellPrice * Float.parseFloat(sellLimit) / 100);
		} else {
			this.sellLimit = Float.parseFloat(sellLimit);
		}

		if (buyLimit.contains("%")) {
			buyLimit = buyLimit.replace("%", "");
			this.buyLimit = buyPrice + (buyPrice * Float.parseFloat(buyLimit) / 100);
		} else {
			this.buyLimit = Float.parseFloat(buyLimit);
		}
	}


	@Override
	public void run() {
		Coin coin = null;
		DecimalFormat df = new DecimalFormat("#.########");
		startTime = System.currentTimeMillis();

		if (wallet.getCurrencies() != null && !wallet.getCurrencies().containsKey(symbol)) {
			wallet.semaphoreAcq();
			wallet.getCurrencies().put(symbol, new Coin());
			wallet.semaphoreRel();
		}

		while (done != true) {
			coin = wallet.getCurrencies().get(symbol);
			lastPrice = coin.getLastClosePrice();
			actualPrice = coin.getActualClosePrice();

			if (lastPrice == -1 || actualPrice == -1) {
				System.out.println("Prices unkown");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				continue;
			}

			System.out.println(symbol);
			System.out.println("Coin price: " + df.format(actualPrice));
			System.out.println("Coin Trades: " + coin.getLastMinuteTrades());
			System.out.println("Buy price: " + df.format(buyPrice));
			System.out.println("Buy limit: " + df.format(buyLimit));
			System.out.println("Sell price: " + df.format(sellPrice));
			System.out.println("Sell limit: " + df.format(sellLimit));

			if (bought == false) {
				System.out.println("Waiting price to buy");
				if (actualPrice <= buyPrice) {
					System.out.println("Buying for " + df.format(buyLimit));
					binance.placeStartLimitOrder(symbol, quantity, buyPrice, buyLimit, "bcdorder1");
					bought = true;
				}
			}

			else {
				if (actualPrice >= sellPrice) {
					System.out.println("Selling for " + df.format(sellLimit));
					binance.placeStopLimitOrder(symbol, (float) (quantity*0.999), sellPrice, sellLimit, "bcdorder2");
					sold = true;
					done = true;
				} else {
					System.out.println("Waiting for a good price to sell");
				}
			}


			System.out.println("\n");

			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
			}
		}

		System.out.println(symbol + " trailing stopped");
	}


	public void cancel() {
		done = true;
	}
}
