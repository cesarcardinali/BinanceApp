package models;

import supportive.BinanceApi;
import ui.MainScreen;
import ui.MainWindow;


public class AppData {

	Wallet wallet;
	BinanceApi binance;
	MainWindow ui;


	public AppData(MainWindow mainWindow) {
		this.ui = mainWindow;
		wallet = new Wallet();
	}


	// Main methods ----------------------------------------------------------------------------------------------------------------
	public void logIn(String apiKey, String apiSecret) {
		binance = new BinanceApi(apiKey, apiSecret);
		wallet = new Wallet();

		ui.goToMainScreen();
		ui.revalidate();
	}
	// -----------------------------------------------------------------------------------------------------------------------------


	// Supportive methods ----------------------------------------------------------------------------------------------------------
	private void initVariables() {
	}
	// -----------------------------------------------------------------------------------------------------------------------------


	// Getters and Setters ---------------------------------------------------------------------------------------------------------
	public BinanceApi getBinance() {
		return binance;
	}


	public void setBinance(BinanceApi binance) {
		this.binance = binance;
	}


	public Wallet getAppData() {
		return wallet;
	}


	public void setAppData(Wallet appData) {
		this.wallet = appData;
	}
	// -----------------------------------------------------------------------------------------------------------------------------
}
