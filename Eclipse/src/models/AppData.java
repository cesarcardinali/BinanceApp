package models;

import java.util.ArrayList;
import actuators.TrailingOrder;
import supportive.BinanceApi;
import ui.MainWindow;


public class AppData {

	Wallet wallet;
	BinanceApi binance;
	MainWindow ui;
	
	ArrayList<TrailingOrder> activeTrailings;


	public AppData(MainWindow mainWindow) {
		this.ui = mainWindow;
		wallet = new Wallet();
		
		activeTrailings = new ArrayList<>();
	}


	// Main methods ----------------------------------------------------------------------------------------------------------------
	public boolean logIn(String apiKey, String apiSecret) {
		binance = new BinanceApi(apiKey, apiSecret);
		if (binance.isConnectionGood()) {
			wallet = new Wallet();
			
			ui.goToMainScreen();
			ui.revalidate();
			
			System.out.println("loggedin");
			
			return true;
		} else {
			System.out.println("log in FAILED!");
		}
		
		return false;
	}
	
	public void addTrailing(TrailingOrder t) {
		activeTrailings.add(t);
	}
	
	public void stopAllTrailings() {
		for (TrailingOrder t : activeTrailings) {
			t.cancel();
			activeTrailings.remove(t);
		}
		
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


	public Wallet getWallet() {
		return wallet;
	}


	public void setWallet(Wallet appData) {
		this.wallet = appData;
	}
	// -----------------------------------------------------------------------------------------------------------------------------
}
