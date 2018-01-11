package actuators;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import models.AppData;
import models.Coin;
import models.Wallet;
import supportive.BinanceApi;


public class ComboTrade implements Runnable {

	AppData appData;
	Wallet wallet;
	BinanceApi binance;
	
	String symbol;
	float buyPrice;
	float buyLimit;
	float sellPrice;
	float dropLimit;
	float quantity;
	boolean bought = false;
	boolean sold = false;
	TrailingOrder trail;

	float actualPrice;
	float lastPrice;
	boolean alert = false;
	long startTime;


	public ComboTrade(AppData appData, String symbol, String quantity, String buy, String sell, String buyLimit, String dropLimit) {
		this.appData = appData;
		wallet = appData.getWallet();
		binance = appData.getBinance();

		this.symbol = symbol;
		this.quantity = Float.parseFloat(quantity);
		this.buyPrice = Float.parseFloat(buy);
		this.sellPrice = Float.parseFloat(sell);

		if (dropLimit.contains("%")) {
			dropLimit = dropLimit.replace("%", "");
			this.dropLimit = sellPrice + (sellPrice * Float.parseFloat(dropLimit) / 100);
		} else {
			this.dropLimit = Float.parseFloat(dropLimit);
		}

		if (buyLimit.contains("%")) {
			buyLimit = buyLimit.replace("%", "");
			this.buyLimit = buyPrice + (buyPrice * Float.parseFloat(buyLimit) / 100);
		} else {
			this.buyLimit = Float.parseFloat(buyLimit);
		}
		
		
		// Config trailing seller
		// public TrailingOrder(AppData appData, String symbol, String start, String dropLimit, String quantity) {
		trail = new TrailingOrder(appData, symbol, sell, dropLimit, quantity);
	}


	@Override
	public void run() {
		Coin coin = null;
		DecimalFormat df = new DecimalFormat("#.########");
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		startTime = System.currentTimeMillis();

		if (wallet.getCurrencies() != null && !wallet.getCurrencies().containsKey(symbol)) {
			wallet.semaphoreAcq();
			wallet.getCurrencies().put(symbol, new Coin());
			wallet.semaphoreRel();
		}

		while (bought != true) {
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
			System.out.println("Sell limit: " + df.format(dropLimit));

			if (bought == false) {
				System.out.println("Waiting price to buy");
				if (actualPrice <= buyPrice) {
					System.out.println("Buying for " + df.format(buyLimit));
					binance.placeStartLimitOrder(symbol, quantity, df.format(buyPrice), df.format(buyLimit), "bcdorder1");
					bought = true;
				}
			}

			else {
				Thread trailing = new Thread(trail);
				trailing.start();
				appData.addTrailing(trail);
				
				/*
				if (actualPrice >= sellPrice) {
					System.out.println("Selling for " + df.format(sellLimit));
					binance.placeStopLimitOrder(symbol, (float) (quantity*0.999), sellPrice, sellLimit, "bcdorder2");
					sold = true;
					done = true;
				} else {
					System.out.println("Waiting for a good price to sell");
				}*/
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
		bought = true;
	}
}
