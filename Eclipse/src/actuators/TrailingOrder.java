package actuators;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import models.AppData;
import models.Coin;
import models.Wallet;
import supportive.BinanceApi;


public class TrailingOrder implements Runnable {

	AppData appData;
	Wallet wallet;
	BinanceApi binance;
	
	boolean done = false;
	String symbol;
	float startPrice;
	float stopPrice;
	float goalPrice;
	float trailPrice;
	float dropLimit;
	float quantity;

	float actualPrice;
	float lastPrice;
	boolean percentage = false;
	boolean percentageGoal = true;
	boolean alert = false;
	long startTime;


	public TrailingOrder(AppData appData, String symbol, String start, String dropLimit, String goal, String quantity) {
		this.appData = appData;
		wallet = appData.getWallet();
		binance = appData.getBinance();

		this.symbol = symbol;
		
		start = start.replace(",", ".");
		dropLimit = dropLimit.replace(",", ".");
		goal = goal.replace(",", ".");
		
		if (goal.contains("%")) {
			percentageGoal = true;
			goal = goal.replace("%", "");
		} else {
			percentageGoal = false;
		}
		goal.replace("%", "");
		
		this.trailPrice = Float.parseFloat(start);
		startPrice = trailPrice;
		stopPrice = 0;
		goalPrice = Float.parseFloat(goal);;
		this.dropLimit = Float.parseFloat(dropLimit);
		this.quantity = Float.parseFloat(quantity);
	}
	
	public TrailingOrder(AppData appData, String symbol, String start, String dropLimit, String quantity) {
		this.appData = appData;
		wallet = appData.getWallet();
		binance = appData.getBinance();

		this.symbol = symbol;

		if (dropLimit.contains("%")) {
			percentage = true;
			dropLimit = dropLimit.replace("%", "");
		} else {
			percentage = false;
		}
		dropLimit.replace("%", "");
		this.trailPrice = Float.parseFloat(start);
		startPrice = trailPrice;
		stopPrice = 0;
		goalPrice = -1;
		this.dropLimit = Float.parseFloat(dropLimit);
		this.quantity = Float.parseFloat(quantity);
	}


	public TrailingOrder(AppData appData) {
		this.appData = appData;
		this.symbol = null;
		this.trailPrice = -1;
		this.dropLimit = -1;
		this.quantity = -1;
	}
	
	
	@Override
	public void run() {
		Coin coin = null;
		DecimalFormat df = new DecimalFormat("#.#########");
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
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
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				continue;
			}

			if (percentage) {
				dropLimit = trailPrice * dropLimit / 100;
			}
			if (percentageGoal) {
				goalPrice = startPrice + startPrice * dropLimit / 100;
			}
			
			System.out.println(symbol);
			System.out.println("My price  : " + df.format(trailPrice));
			System.out.println("Coin price: " + df.format(actualPrice));
			System.out.println("Drop Limit: " + df.format(trailPrice - dropLimit));
			System.out.println("Goal      : " + df.format(goalPrice));
			if(stopPrice > 0)
				System.out.println("Stop Activated: " + df.format(stopPrice));
			System.out.println("Trades: " + coin.getLastMinuteTrades());

			if (actualPrice > trailPrice) {
				trailPrice = actualPrice;
				if (percentage) {
					dropLimit = trailPrice * dropLimit / 100;
				}
				System.out.println("Updating price to " + df.format(trailPrice));
				
				if (goalPrice > 0 && goalPrice < actualPrice) {
					if (actualPrice > goalPrice) {
						System.out.println("Selling for " + df.format(actualPrice) + "(" + df.format(actualPrice/startPrice) + ")");
						binance.placeSellOrder(symbol, quantity, df.format(actualPrice), "vend" + symbol);
					}
				}
				
			} else {
				if (actualPrice < trailPrice - dropLimit || actualPrice < stopPrice) {
					if (actualPrice > startPrice) {
						if (alert == false) {
							alert = true;
							System.out.println("Alert for possible selling at " + df.format(actualPrice));
						} else {
							System.out.println("Selling for " + df.format(actualPrice) + "(" + df.format(actualPrice/startPrice) + ")");
							//(String coinSymbol, float quantity, float price, String orderId) 
							binance.placeSellOrder(symbol, quantity, df.format(actualPrice), "vend" + symbol);
							done = true;
						}
					} else {
						System.out.println("HOOODL! Coin price is below your bought price ");
					}
				} else {
					System.out.println("Still better than limit\nPrice: " + df.format(actualPrice) + "   -  Limit: " + df.format(trailPrice - dropLimit));
					alert = false;
					if (stopPrice > 0) {
						System.out.println("Stop is activated to " + df.format(stopPrice) + ". May be selled before reaches low limit");
					}
				}
			}


			if (System.currentTimeMillis() - startTime > 5 * 60 * 1000) {
				if (dropLimit < startPrice && stopPrice == 0) {
					stopPrice = (float) (startPrice * 1.0021);
					System.out.println("Stop price activated");
				}
			}


			System.out.println("\n");


			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}
		}

		System.out.println(symbol + " trailing stopped");
	}


	public void cancel() {
		done = true;
	}
}
