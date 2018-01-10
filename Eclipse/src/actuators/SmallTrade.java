package actuators;

import java.text.DecimalFormat;
import models.AppData;
import models.Coin;
import models.Wallet;
import supportive.BinanceApi;


public class SmallTrade implements Runnable {

	AppData appData;
	Wallet wallet;
	BinanceApi binance;
	String symbol;
	float startPrice;
	float closePrice;
	float dropLimit;
	float quantity;
	float startMoney;
	float stopPrice;
	float buyPrice;
	float maxPrice;

	float total;
	float initialMoney;
	float initialTransactionMoney;
	float bitcoinPrice = 17000;

	float actualPrice;
	float lastPrice;

	boolean done = false;
	boolean alert = false;


	public SmallTrade(AppData appData, String symbol, String dropLimit, String quantity) {
		this.appData = appData;
		wallet = appData.getWallet();
		binance = appData.getBinance();
		total = 0;

		this.symbol = symbol;

		dropLimit.replace("%", "");
		stopPrice = 0;
		this.dropLimit = Float.parseFloat(dropLimit);
		if (quantity.contains("m")) {
			this.quantity = Float.parseFloat("0");
			this.startMoney = Float.parseFloat(quantity.replace("m", "")) / bitcoinPrice;
		} else {
			this.quantity = Float.parseFloat(quantity);
			this.startMoney = Float.parseFloat("0");
		}

	}


	@Override
	public void run() {
		Coin coin = wallet.getCurrencies().get(symbol);
		System.out.println("Started monitoring " + symbol);

		DecimalFormat df = new DecimalFormat("#.########");
		DecimalFormat pf = new DecimalFormat("###.##");
		float newDropLimit;

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

		if (coin != null) {
			startPrice = coin.getLastOpenPrice();
			closePrice = coin.getLastClosePrice();
		} else {
			System.out.println("!!!!!! - " + symbol);
		}
		while (done != true) {
			actualPrice = coin.getActualClosePrice();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			if (coin.getLastOpenPrice() <= startPrice) {
				startPrice = coin.getLastOpenPrice();
			} else if (coin.getLastClosePrice() <= closePrice) {
				closePrice = coin.getLastClosePrice();
			}
			if (actualPrice >= startPrice * 1.02 || actualPrice >= closePrice * 1.02) {
				//Buy
				newDropLimit = dropLimit;
				buyPrice = actualPrice;
				if (total == 0) {
					if (startMoney == 0) {
						total = (float) ((quantity * buyPrice) * 0.999);
						initialMoney = (float) (quantity * buyPrice);
					} else {
						total = (float) (startMoney * 0.999);
						initialMoney = startMoney;
					}
				}
				quantity = total / buyPrice;
				initialTransactionMoney = total;
				System.out.println("### " + symbol + " ###");
				System.out.println("Bought at: " + df.format(buyPrice));
				System.out.println("Bought: " + df.format(quantity));
				System.out.println("");
				maxPrice = buyPrice;
				//Start trailing
				while (true) {
					actualPrice = coin.getActualClosePrice();
					if (actualPrice > maxPrice) {
						maxPrice = actualPrice;
						if (maxPrice >= buyPrice * 1.01 && newDropLimit == dropLimit) {
							newDropLimit = newDropLimit / 2;
							System.out.println("### " + symbol + " ###");
							System.out.println("Being Safe -> Decrease Drop Limit: " + dropLimit + " -> " + newDropLimit);
							System.out.println("");
						}
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
					if ((actualPrice <= buyPrice * (1 - 0.02) || actualPrice <= maxPrice * (1 - newDropLimit)) && !alert) {
						alert = true;
					} else if (alert) {
						if (actualPrice <= buyPrice * (1 - 0.02)) {
							System.out.println("### " + symbol + " ###");
							System.out.println("Coin dropped BELOW buy price. Sold at " + df.format(actualPrice));
							total = (float) ((quantity * actualPrice) * 0.999);
							System.out.println("LOSS: " + pf.format(((total / initialTransactionMoney) - 1) * 100) + "%");
							System.out.println("Initial: " + initialMoney + "\tInitialTransaction: " + initialTransactionMoney + "\tTotal: " + total);
							System.out.println("");
							startPrice = coin.getLastOpenPrice();
							closePrice = coin.getLastClosePrice();
							break;
						} else if (actualPrice <= maxPrice * (1 - newDropLimit)) {
							System.out.println("### " + symbol + " ###");
							System.out.println("Coin dropped below drop limit. Sold at " + df.format(actualPrice));
							System.out.println("Buy Price: " + df.format(buyPrice));
							System.out.println("Sell Price: " + df.format(actualPrice));
							total = (float) ((quantity * actualPrice) * 0.999);
							if (actualPrice < buyPrice) {
								System.out.println("LOSS (after binance taxes): " + pf.format(((total / initialTransactionMoney) - 1) * 100) + "%");
							} else {
								System.out.println("Profit (after binance taxes): " + pf.format(((total / initialTransactionMoney) - 1) * 100) + "%");
							}
							System.out.println("Initial: " + initialMoney + "\tInitialTransaction: " + initialTransactionMoney + "\tTotal: " + total);
							System.out.println("");
							startPrice = coin.getLastOpenPrice();
							closePrice = coin.getLastClosePrice();
							break;
						} else {
							alert = false;
						}
					}
				}
			}
		}
		appData.totalMoneyCurrent += total * bitcoinPrice;
		appData.totalMoneyStart += initialMoney * bitcoinPrice;
		System.out.println("=========================");
		System.out.println("### " + symbol + " ###");
		System.out.println("Start Money: " + appData.totalMoneyStart);
		System.out.println("End Money: " + appData.totalMoneyCurrent);
		System.out.println("=========================\n\n\n");
	}


	public void cancel() {
		done = true;
		System.out.println("Stopping thread for " + symbol);
		System.out.println("If coins are bought, they will be sold before stopping");
	}

}
