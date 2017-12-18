package monitors;

import java.io.IOException;
import java.net.MalformedURLException;
import models.AppData;
import models.Coin;
import models.Wallet;
import org.json.simple.parser.ParseException;
import supportive.BinanceApi;


// Monitora subidas e descidas de preco de uma moeda
public class CoinMonitor implements Runnable {

	AppData appData;
	BinanceApi binance;
	Wallet wallet;
	Coin coin;
	
	String monitoredCoin;
	boolean running = false;


	public CoinMonitor(AppData data) {
	}


	public CoinMonitor(AppData data, String coinSymbol) {
		monitoredCoin = coinSymbol;
		this.appData = data;
		binance = appData.getBinance();
		wallet = appData.getWallet();
	}


	public void monitor() {
		running = true;
		/*if (wallet.getCurrencies().containsKey(monitoredCoin)) {
			coin = wallet.getCurrencies().get(monitoredCoin);
		} else {
			coin = new Coin();
		}*/
		
		coin = new Coin();
		wallet.getCurrencies().put(monitoredCoin, coin);
	
		while (running) {
			try {
				coin.updatePrices(binance.getPriceUpdate(monitoredCoin));
				coin.updateOneMinuteCandle(binance.getCoinCandle(monitoredCoin, "1m", "120"));
				System.out.println("Prices updated!");
				
				
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
			}
		}
	}


	@Override
	public void run() {
		monitor();
	}

}
