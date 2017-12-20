package models;

import java.util.ArrayList;
import monitors.CoinMonitor;
import supportive.AccountStorage;
import supportive.BinanceApi;
import supportive.AccountStorage.SavedAccount;
import ui.MainWindow;
import actuators.TrailingOrder;


public class AppData {

	Wallet wallet;
	BinanceApi binance;
	MainWindow ui;
	AccountStorage accs;
	
	ArrayList<TrailingOrder> activeTrailings;
	ArrayList<CoinMonitor> coinMonitors;


	public AppData(MainWindow mainWindow) {
		this.ui = mainWindow;
		wallet = new Wallet();
		
		activeTrailings = new ArrayList<>();
		coinMonitors = new ArrayList<>();
		accs = new AccountStorage();
	}


	// Main methods ----------------------------------------------------------------------------------------------------------------
	public boolean logIn(String apiKey, String apiSecret) {
		binance = new BinanceApi(apiKey, apiSecret);
		if (binance.isConnectionGood()) {
			//wallet = new Wallet();
			wallet = binance.getWallet();
			
			ui.goToMainScreen();
			ui.revalidate();
			
			System.out.println("loggedin");
			
			return true;
		} else {
			System.out.println("log in FAILED!");
		}
		
		return false;
	}
	
	public boolean logIn(String account) {
		SavedAccount acc = accs.getAcc(account);
		binance = new BinanceApi(acc.getKey(), acc.getSecret());
		if (binance.isConnectionGood()) {
			// Create wallet based on your Binance Acc
			wallet = binance.getWallet();
			
			// Start monitors for the coins in your wallet
			for (String c : wallet.getCurrencies().keySet()) {
				if(c.equals("BCXBTC") || c.equals("SBTCBTC"))
					continue;
				CoinMonitor m = new CoinMonitor(this, c);
				m.start();
				coinMonitors.add(m);
			}
			
			ui.goToMainScreen();
			ui.revalidate();
			
			System.out.println("loggedin");
			
			return true;
		} else {
			System.out.println("log in FAILED!");
		}
		
		return true;
	}
	
	public void addTrailing(TrailingOrder t) {
		activeTrailings.add(t);
	}
	
	public void addMonitor(CoinMonitor m) {
		coinMonitors.add(m);
	}
	
	public void stopAllTrailings() {
		for (TrailingOrder t : activeTrailings) {
			t.cancel();
			activeTrailings.remove(t);
		}
		
	}
	
	public void stopAllMonitors() {
		for (CoinMonitor m : coinMonitors) {
			m.stopMonitor();
			coinMonitors.remove(m);
		}
		
	}
	
	public boolean hasMonitor(String symbol) {
		for (CoinMonitor m : coinMonitors) {
			if (m.getMonitoredCoin().equals(symbol) && m.isRunning()) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasAccount(String acc) {
		if(accs.getAcc(acc) != null) {
			return true;
		}
		
		return false;
	}
	
	public void addAccount(String acc, String k, String sec) {
		accs.addAcc(acc, k, sec);
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


	
	public AccountStorage getAccs() {
		return accs;
	}

	
	public void setAccs(AccountStorage accs) {
		this.accs = accs;
	}


	
	public ArrayList<TrailingOrder> getActiveTrailings() {
		return activeTrailings;
	}


	
	public void setActiveTrailings(ArrayList<TrailingOrder> activeTrailings) {
		this.activeTrailings = activeTrailings;
	}


	
	public ArrayList<CoinMonitor> getCoinMonitors() {
		return coinMonitors;
	}


	
	public void setCoinMonitors(ArrayList<CoinMonitor> coinMonitors) {
		this.coinMonitors = coinMonitors;
	}
	// -----------------------------------------------------------------------------------------------------------------------------
}
