package actuators;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import models.AppData;
import models.Coin;
import models.Wallet;
import supportive.BinanceApi;
import ui.TrailingPane;


public class TrailingOrder implements Runnable {

	AppData appData;
	Wallet wallet;
	BinanceApi binance;

	TrailingPane statusPane;

	boolean done = false;
	String symbol;
	float startPrice;
	float minimumPrice;
	float goalPrice;
	float goalPricePercentage = -1;
	float trailPrice;
	float dropLimit;
	float dropLimitPercentage = -1;
	float quantity;

	float actualPrice;
	float lastPrice;
	boolean percentage = false;
	boolean percentageGoal = true;
	boolean alert = false;
	boolean holdForBought = false;
	boolean loopIt = false;
	boolean SOAGD = false;
	String holdForGoal = "";
	long startTime;

	public static final String GOAL_HARD = "hard";
	public static final String GOAL_SOFT = "soft";
	public static final String GOAL_NONE = "none";


	public TrailingOrder(AppData appData, String symbol, String start, String dropLimit, String goal, String quantity) {
		this.appData = appData;
		wallet = appData.getWallet();
		binance = appData.getBinance();

		this.symbol = symbol;
		holdForGoal = "";

		start = start.replace(",", ".");
		dropLimit = dropLimit.replace(",", ".");
		goal = goal.replace(",", ".");

		if (goal.contains("%")) {
			percentageGoal = true;
			goal = goal.replace("%", "");
			goalPricePercentage = Float.parseFloat(goal) / 100;
			goalPrice = -1;
		} else {
			percentageGoal = false;
			goalPrice = Float.parseFloat(goal);
		}

		if (dropLimit.contains("%")) {
			percentage = true;
			dropLimit = dropLimit.replace("%", "");
			dropLimitPercentage = Float.parseFloat(dropLimit) / 100;
			this.dropLimit = -1;
		} else {
			percentage = false;
			this.dropLimit = Float.parseFloat(dropLimit);
		}

		startPrice = Float.parseFloat(start);
		trailPrice = Float.parseFloat(start);
		minimumPrice = 99999; // to be calculate later
		this.quantity = Float.parseFloat(quantity);
	}


	public TrailingOrder(AppData appData, String symbol, String start, String dropLimit, String quantity) {
		this.appData = appData;
		wallet = appData.getWallet();
		binance = appData.getBinance();

		this.symbol = symbol;
		holdForGoal = "";

		if (dropLimit.contains("%")) {
			percentage = true;
			dropLimit = dropLimit.replace("%", "");
		} else {
			percentage = false;
		}
		dropLimit.replace("%", "");
		trailPrice = Float.parseFloat(start);
		startPrice = Float.parseFloat(start);
		minimumPrice = 0;
		goalPrice = -1;
		this.dropLimit = Float.parseFloat(dropLimit);
		this.quantity = Float.parseFloat(quantity);
	}


	public TrailingOrder(AppData appData) {
		this.appData = appData;
		this.symbol = null;
		this.trailPrice = -1;
		this.dropLimit = -1;
		this.quantity = -1;
		holdForGoal = "";
	}


	@Override
	public void run() {
		// Config basic stuff
		statusPane = new TrailingPane();
		statusPane.setStatus("Waiting for status ...");
		String status = "";
		Coin coin = null;
		DecimalFormat df = new DecimalFormat("#.#########");
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		DecimalFormat dfPct = new DecimalFormat("#.###");
		df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
		startTime = System.currentTimeMillis();

		if (wallet.getCurrencies() != null && !wallet.getCurrencies().containsKey(symbol)) {
			wallet.semaphoreAcq();
			wallet.getCurrencies().put(symbol, new Coin());
			wallet.semaphoreRel();
		}
		boolean goalAchieved = false;
		showPanel();

		// Start trailing
		while (done != true) {
			// Get coin data
			coin = wallet.getCurrencies().get(symbol);
			lastPrice = coin.getLastClosePrice();
			actualPrice = coin.getActualClosePrice();
			status = "";

			// Check if coin data is ready
			if (lastPrice == -1 || actualPrice == -1) {
				System.out.println("Waiting for coin data ...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
				continue;
			}

			// Calculate variables values
			if (percentage) {
				dropLimit = trailPrice * dropLimitPercentage;
			}
			if (percentageGoal && goalPrice == -1) {
				goalPrice = startPrice + startPrice * goalPricePercentage;
			}

			// Show the coin information
			System.out.println(symbol);
			status += symbol + "\n\n";
			System.out.println("My price  : " + df.format(startPrice));
			status += "My price  : " + df.format(startPrice) + "\n";
			System.out.println("Trail     : " + df.format(trailPrice));
			status += "Trail Top : " + df.format(trailPrice) + "\n";
			System.out.println("Coin price: " + df.format(actualPrice) + " (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)");
			status += "Coin price: " + df.format(actualPrice) + " (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)" + "\n";
			System.out.println("Drop Limit: " + df.format(trailPrice - dropLimit) + " (" + dfPct.format(((trailPrice - dropLimit) / startPrice - 1) * 100) + "%)");
			status += "Drop Limit: " + df.format(trailPrice - dropLimit) + " (" + dfPct.format(((trailPrice - dropLimit) / startPrice - 1) * 100) + "%)" + "\n";
			System.out.println("Goal      : " + df.format(goalPrice) + "  -  " + goalAchieved);
			status += "Goal      : " + df.format(goalPrice) + "  -  " + goalAchieved + "\n";

			// Check if stop loss is activated
			if (minimumPrice > 0 && minimumPrice != 99999) {
				System.out.println("Stop loss Activated: " + df.format(minimumPrice));
				status += "Stop loss Activated: " + df.format(minimumPrice) + "\n";
			}
			System.out.println("Trades: " + coin.getLastMinuteTrades());
			status += "Trades: " + coin.getLastMinuteTrades() + "\n";


			// Check if price is higher now
			if (actualPrice > trailPrice) {
				// Update the top price
				trailPrice = actualPrice;
				System.out.println("Updating price to " + df.format(trailPrice));
				status += "Updating price to " + df.format(trailPrice) + "\n";

				// Check if Goal is reached
				if (goalPrice > 0 && actualPrice >= goalPrice) {
					goalPrice = actualPrice;
					goalAchieved = true;
					System.out.println("Goal achieved! Updating Goal price to " + df.format(goalPrice));
					status += "Goal achieved! Updating Goal price to " + df.format(goalPrice) + "\n";
				}
			}

			// Goal was reached before and user wants to sell as soon as it drops before highest value?
			else if (goalAchieved && SOAGD) {
				System.out.println("Selling out! Dropped from the top and higher than first goal (SOAGD) - (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)");
				status += "Selling based on goal for " + df.format(actualPrice) + " (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)" + "\n";
				binance.placeSellOrder(symbol, quantity, df.format(actualPrice), "vend" + symbol);
				done = true;
			}
			
			// Get known that price dropped!
			else if (alert == false) {
				alert = true;
				
				System.out.println("Price is dropped!! Alert is now ON!  If it stiill low, coin will be sold");
				status += "Price is dropped!! Alert is now ON!  If it stiill low, coin will be sold" + "\n";
			}
			
			// If it is alerted 
			else if (actualPrice < trailPrice - dropLimit) {
				// Se nao atingiu o GOAL e o modo eh HARD, nao vende
				if (holdForGoal.equals(GOAL_HARD)) {
					if (goalAchieved) {
						System.out.println("Selling! Goal HARD achieved!! (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)");
						status += "Selling! Goal HARD achieved!! (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)" + "\n";
						binance.placeSellOrder(symbol, quantity, df.format(actualPrice), "vend" + symbol);
						done = true;
					}
					
					else {
						System.out.println("Waiting for Goal (HARD) price ignoring any other value below it");
						status += "Waiting for Goal (HARD) price ignoring any other value below it" + "\n";
					}
				} 
				
				// Se nao for HARD e ja caiu do limite, vende! (pois se for soft ou preco minimo e entrou aqui, quer dizer q ja eh menor q o minimo)
				else {
					System.out.println("Selling out to avoid loss! (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)");
					status += "Selling out to avoid loss! (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)" + "\n";
					binance.placeSellOrder(symbol, quantity, df.format(actualPrice), "vend" + symbol);
					done = true;
				}
			} 
			
			// Se nao bateu o limite de drop mas bateu o minimo, vende!
			else if ((holdForGoal.equals(GOAL_SOFT) || holdForBought) && actualPrice <= minimumPrice && minimumPrice != 99999) {
				System.out.println("Selling out to avoid loss! Dropped from minimum price (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)");
				status += "Selling out to avoid loss! Dropped from minimum price (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)" + "\n";
				binance.placeSellOrder(symbol, quantity, df.format(actualPrice), "vend" + symbol);
				done = true;
			}
			
			else {
				System.out.println("Doing nothing");
				status += "Doing nothing" + "\n";
			}

			
			
			

			/*
			if (actualPrice > trailPrice) {
				// Update the top price
				trailPrice = actualPrice;
			
				// If needed, recalculate the drop limit
				if (percentage) {
					dropLimit = trailPrice * dropLimit / 100;
				}
				System.out.println("Updating price to " + df.format(trailPrice));
				status += "Updating price to " + df.format(trailPrice) + "\n";
			
				// Check if Goal is reached
				if (goalPrice > 0 && actualPrice >= goalPrice) {
					goalPrice = actualPrice;
					goalAchieved = true;
					System.out.println("Goal achieved! Updating Goal price to " + df.format(goalPrice));
					status += "Goal achieved! Updating Goal price to " + df.format(goalPrice) + "\n";
			
					// If it is beyond the Goal and the top trail price had a drop, sell it!
				} else if (goalAchieved && SOAGD) {
					System.out.println("Selling out! Dropped from the top and higher than first goal (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)");
					status += "Selling based on goal for " + df.format(actualPrice) + " (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)" + "\n";
					//binance.placeSellOrder(symbol, quantity, df.format(actualPrice), "vend" + symbol);
					done = true;
				}
			
			} else {
				if (actualPrice < trailPrice - dropLimit || (minimumPrice > 0 && actualPrice < minimumPrice)) {
					if (actualPrice > startPrice && actualPrice > minimumPrice) {
						if (alert == false) {
							alert = true;
							System.out.println("Alert for possible selling at " + df.format(actualPrice));
							status += "Alert for possible selling at " + df.format(actualPrice) + "\n";
						} else {
							System.out.println("Selling for " + df.format(actualPrice) + " (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)");
							status += "Selling for " + df.format(actualPrice) + " (" + dfPct.format((actualPrice / startPrice - 1) * 100) + "%)" + "\n";
							//binance.placeSellOrder(symbol, quantity, df.format(actualPrice), "vend" + symbol);
							done = true;
						}
					} else {
						System.out.println("HOOODL! Coin price is below your bought price ");
						status += "HOOODL! Coin price is below your bought price " + "\n";
					}
			
				} else {
					System.out.println("Still better than limit\nPrice: " + df.format(actualPrice) + "   -  Limit: " + df.format(trailPrice - dropLimit));
					status += "Still better than limit\nPrice: " + df.format(actualPrice) + "   -  Limit: " + df.format(trailPrice - dropLimit) + "\n";
					alert = false;
				}
			}*/


			
			
			// Activate stop loss
			if (System.currentTimeMillis() - startTime > 1 * 60 * 1000) {
				if (dropLimit < startPrice && minimumPrice == 0) {
					minimumPrice = (float) (startPrice * 1.003);
					System.out.println("Stop price activated " + df.format(minimumPrice));
					status += "Stop price activated " + df.format(minimumPrice) + "\n";
				}
			}


			// Print data
			statusPane.setStatus(status);
			System.out.println("\n");


			// Wait for next cycle
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
			}
		}

		// Tell user that it is finished!
		System.out.println(symbol + " trailing stopped");
		statusPane.setStatus(status + "\n\n --- Trailing stopped ---");
		statusPane.setVisible(true);
		statusPane.setAlwaysOnTop(true);
		statusPane.requestFocus();
		appData.getActiveTrailings().remove(this);
	}


	public void cancelIt() {
		done = true;
	}


	public void showPanel() {
		statusPane.setVisible(true);
	}


	// Getters and Setters --------------------------------------------------------------------------------
	public boolean isHoldForBought() {
		return holdForBought;
	}

	public void setHoldForBought(boolean holdForBought) {
		this.holdForBought = holdForBought;
	}

	public String getHoldForGoal() {
		return holdForGoal;
	}

	public void setHoldForGoal(String holdForGoal) {
		this.holdForGoal = holdForGoal;
	}

	public boolean isSOAGD() {
		return SOAGD;
	}

	public void setSOAGD(boolean sOAGD) {
		SOAGD = sOAGD;
	}

	public boolean isLoopIt() {
		return loopIt;
	}

	public void setLoopIt(boolean loopIt) {
		this.loopIt = loopIt;
	}
}
