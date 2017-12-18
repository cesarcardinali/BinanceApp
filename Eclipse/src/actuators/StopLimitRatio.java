package actuators;

import models.AppData;
import models.Coin;
import models.Wallet;
import supportive.BinanceApi;


public class StopLimitRatio {

	AppData appData;
	Wallet wallet;
	BinanceApi binance;
	boolean done = false;
	String symbol;
	String start;
	String dropLimit;
	String topLimit;


	public StopLimitRatio(AppData appData, String symbol, String start, String dropLimit, String topLimit) {
		this.appData = appData;
		wallet = appData.getWallet();
		binance = appData.getBinance();

		this.symbol = symbol;
		this.start = start;
		this.dropLimit = dropLimit;
		this.topLimit = topLimit;
	}


	public void run() {
		Coin coin = null;
		if (wallet.getCurrencies().containsKey(symbol)) {
			coin = wallet.getCurrencies().get(symbol);
		}

		while (done != true) {

		}
	}
}
