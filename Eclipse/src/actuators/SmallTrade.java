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
	float dropLimit;
	float quantity;
	float startMoney;
	float stopPrice;
	float buyPrice;
	float maxPrice;
	
	float total;
	float initialMoney;
	float initialTransactionMoney;

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
		float bitcoinPrice = 17000;
		if (quantity.contains("m")){
			this.quantity = Float.parseFloat("0");
			this.startMoney = Float.parseFloat(quantity.replace("m", ""))/bitcoinPrice;
		}else{
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
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		
		if(coin != null){
			startPrice = coin.getActualClosePrice();
		}else{
			System.out.println("!!!!!! - " + symbol);
		}
		while (done != true) {
			actualPrice = coin.getActualClosePrice();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			if (actualPrice <= startPrice){
				startPrice = actualPrice;
			} else if (actualPrice >= startPrice*1.015){
				//Buy
				buyPrice = actualPrice;
				if (total == 0){
					if(startMoney == 0){
						total = (float) ((quantity * buyPrice)*0.999);
						initialMoney = (float) (quantity * buyPrice);
					}else{
						total = (float) (startMoney*0.999);
						initialMoney = startMoney;
					}
					appData.totalMoneyStart += initialMoney;
				}
				quantity = total/buyPrice;
				initialTransactionMoney = total;
				System.out.println("### " + symbol + " ###");
				System.out.println("Bought at: " + df.format(buyPrice));
				System.out.println("Bought: " + df.format(quantity));
				System.out.println("");
				maxPrice = buyPrice;
				//Start trailing
				while (true){
					actualPrice = coin.getActualClosePrice();
					if (actualPrice > maxPrice){
						maxPrice = actualPrice;
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
					if ((actualPrice <= buyPrice*(1-0.02) || actualPrice <= maxPrice*(1-dropLimit)) && !alert){
						alert = true;
					} else if (alert){
						if (actualPrice <= buyPrice*(1-0.02)){
							System.out.println("### " + symbol + " ###");
							System.out.println("Coin dropped BELOW buy price. Sold at " + df.format(actualPrice));
							total = (float) ((quantity * actualPrice)*0.999);
							System.out.println("LOST: " + pf.format((1-(total/initialTransactionMoney))*100) + "%");
							System.out.println("Initial: " + initialMoney + "\tInitialTransaction: " + initialTransactionMoney + "\tTotal: " + total);
							System.out.println("");
							startPrice = actualPrice;
							break;
						}else if (actualPrice <= maxPrice*(1-dropLimit)){
							System.out.println("### " + symbol + " ###");
							System.out.println("Coin dropped below drop limit. Sold at " + df.format(actualPrice));
							System.out.println("Buy Price: " + df.format(buyPrice));
							System.out.println("Sell Price: " + df.format(actualPrice));
							total = (float) ((quantity * actualPrice)*0.999);
							System.out.println("Profit (after binance taxes): " + pf.format((1-(total/initialTransactionMoney))*100) + "%");
							System.out.println("Initial: " + initialMoney + "\tInitialTransaction: " + initialTransactionMoney + "\tTotal: " + total);
							System.out.println("");
							startPrice = actualPrice;
							break;
						}else{
							alert = false;
						}
					}
				}
			}
		}
		appData.totalMoneyCurrent += total;
		System.out.println("\n\n\n=========================");
		System.out.println("Start Money: " + appData.totalMoneyStart);
		System.out.println("End Money: " + appData.totalMoneyCurrent);
		System.out.println("\n\n\n=========================");
	}
	
	public void cancel(){
		done = true;
		System.out.println("Stopping thread for " + symbol);
		System.out.println("If coins are bought, they will be sold before stopping");
	}

}
