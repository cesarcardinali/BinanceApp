package models;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import monitors.CoinMonitor;
import supportive.AccountStorage;
import supportive.AccountStorage.SavedAccount;
import supportive.BinanceApi;
import ui.MainWindow;
import actuators.ComboTrade;
import actuators.TrailingOrder;


public class AppData {

	Wallet wallet;
	BinanceApi binance;
	MainWindow ui;
	AccountStorage accs;
	public long totalMoneyStart = 0;
	public long totalMoneyCurrent = 0;
	
	ArrayList<TrailingOrder> activeTrailings;
	ArrayList<ComboTrade> activeComboTrades;
	ArrayList<CoinMonitor> coinMonitors;
	
	private Semaphore s;


	public AppData(MainWindow mainWindow) {
		s = new Semaphore(1);
		this.ui = mainWindow;
		wallet = new Wallet();
		
		activeTrailings = new ArrayList<>();
		activeComboTrades = new ArrayList<>();
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
			
			// Start monitors
			CoinMonitor cm = new CoinMonitor(this, "");
			coinMonitors.add(cm);
			cm.start();
			
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
	
	public void addComboTrade(ComboTrade t) {
		activeComboTrades.add(t);
	}
	
	public void addMonitor(CoinMonitor m) {
		coinMonitors.add(m);
	}
	
	public void stopAllTrailings() {
		try {
			s.acquire();
			for (TrailingOrder t : activeTrailings) {
				t.cancelIt();
				activeTrailings.remove(t);
			}
			s.release();
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
	}
	
	public void stopAllComboTrades() {
		try {
			s.acquire();
			
			for (ComboTrade m : activeComboTrades) {
				m.cancel();
				activeComboTrades.remove(m);
			}
			
			s.release();
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
	}
	
	public void stopAllMonitors() {
		try {
			s.acquire();
			
			for (CoinMonitor m : coinMonitors) {
				m.stopMonitor();
				coinMonitors.remove(m);
			}
				
			s.release();
		} catch (InterruptedException e) { 
			e.printStackTrace();
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
