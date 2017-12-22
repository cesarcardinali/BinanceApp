package monitors;

import java.io.IOException;
import java.net.MalformedURLException;
import models.AppData;
import models.Coin;
import models.Wallet;
import org.json.simple.parser.ParseException;
import supportive.BinanceApi;


// Monitora subidas e descidas de preco de uma moeda
public class CoinMonitor extends Thread implements Runnable {

	AppData appData;
	BinanceApi binance;
	Wallet wallet;
	Coin coin;

	String monitoredCoin;
	boolean running = false;


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
			System.out.println("new coin created into wallet");
		}*/
		System.out.println("Monitor started");
		
		while (running) {
			wallet.semaphoreAcq();
			String curr[] = wallet.getCurrencies().keySet().toArray(new String[wallet.getCurrencies().keySet().size()]);
			for (String c : curr) {
				coin = wallet.getCurrencies().get(c);
				try {
					monitoredCoin = c;
					coin.updatePrices(binance.getPriceUpdate(monitoredCoin));
					coin.updateOneMinuteCandle(binance.getCoinCandle(monitoredCoin, "1m", "120"));
					coin.updateTrend3m();
					//System.out.println("Prices updated!");


				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}


				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
				}
			}
			wallet.semaphoreRel();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		
		System.out.println(monitoredCoin + " monitor stopped");
	}
	
	
	public void stopMonitor() {
		running = false;
	}


	@Override
	public void run() {
		monitor();
	}


	
	public String getMonitoredCoin() {
		return monitoredCoin;
	}


	
	public void setMonitoredCoin(String monitoredCoin) {
		this.monitoredCoin = monitoredCoin;
	}


	
	public boolean isRunning() {
		return running;
	}


	
	public void setRunning(boolean running) {
		this.running = running;
	}

	
	
}
