package actuators;

import java.text.DecimalFormat;
import models.AppData;
import models.Coin;
import models.Wallet;
import monitors.CoinMonitor;
import supportive.BinanceApi;


public class TrailingOrder implements Runnable {

	AppData appData;
	Wallet wallet;
	BinanceApi binance;
	boolean done = false;
	String symbol;
	float startPrice;
	float stopPrice;
	float trailPrice;
	float dropLimit;
	float quantity;

	float actualPrice;
	float lastPrice;
	boolean percentage = false;
	boolean alert = false;
	long startTime;


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
		DecimalFormat df = new DecimalFormat("#.########");
		startTime = System.currentTimeMillis();
		
		if (!appData.hasMonitor(symbol)) {
			CoinMonitor mon = new CoinMonitor(appData, symbol);
			Thread monitor = new Thread(mon);
			monitor.start();
			appData.addMonitor(mon);
		}

		while (done != true) {
			if (wallet.getCurrencies() != null && wallet.getCurrencies().containsKey(symbol)) {
				coin = wallet.getCurrencies().get(symbol);
			} else {
				System.out.println("Coin unkown");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				continue;
			}

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

			System.out.println("My price: " + df.format(trailPrice));
			System.out.println("Coin price: " + df.format(actualPrice));
			System.out.println("Limit: " + df.format(trailPrice - dropLimit));
			System.out.println("Trades: " + coin.getLastMinuteTrades());

			if (actualPrice > trailPrice) {
				trailPrice = actualPrice;
				if (percentage) {
					dropLimit = trailPrice * dropLimit / 100;
				}
				System.out.println("Updating price to " + trailPrice);
			} else {
				if (actualPrice < trailPrice - dropLimit || actualPrice < stopPrice) {
					if (alert == false) {
						alert = true;
						System.out.println("Alert for possible selling at " + df.format(actualPrice));
					} else {
						System.out.println("Selling for " + df.format(actualPrice));
						done = true;
					}
				} else {
					System.out.println("Still better than limit\nPrice: " + df.format(actualPrice) + "   -  Limit: " + df.format(trailPrice - dropLimit));
					alert = false;
					if (stopPrice > 0) {
						System.out.println("Stop is activated to " + df.format(stopPrice) + ". May be selled before reaches low limit");
					}
				}
			}


			if (System.currentTimeMillis() - startTime > 2.5 * 60 * 1000) {
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
