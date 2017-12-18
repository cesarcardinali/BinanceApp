package monitors;

import models.AppData;


// Monitor subidas e descidas de preco de uma moeda
public class CoinMonitor {

	String monitoredCoin;


	public CoinMonitor(AppData data) {
	}


	public CoinMonitor(AppData data, String coinSymbol) {
		monitoredCoin = coinSymbol;
	}

}
