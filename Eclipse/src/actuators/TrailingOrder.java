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
	float trailPrice;
	float dropLimit;
	float quantity;

	float actualPrice;
	float lastPrice;
	boolean percentage = false;
	boolean percentageGoal = true;
	boolean alert = false;
	boolean holdForBought = false;
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
		} else {
			percentageGoal = false;
		}
		goal.replace("%", "");

		trailPrice = Float.parseFloat(start);
		startPrice = Float.parseFloat(start);
		minimumPrice = 0;
		goalPrice = Float.parseFloat(goal);
		this.dropLimit = Float.parseFloat(dropLimit);
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
		int lastTradesCount = -1;
		showPanel();

		while (done != true) {
			coin = wallet.getCurrencies().get(symbol);
			lastPrice = coin.getLastClosePrice();
			actualPrice = coin.getActualClosePrice();
			status = "";

			if (lastPrice == -1 || actualPrice == -1) {
				System.out.println("Prices unkown");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				continue;
			}

			if (percentage) {
				dropLimit = trailPrice * dropLimit / 100;
			}
			if (percentageGoal) {
				goalPrice = startPrice + startPrice * dropLimit / 100;
			}

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
			if (minimumPrice > 0) {
				System.out.println("Stop min Activated: " + df.format(minimumPrice));
				status += "Stop min Activated: " + df.format(minimumPrice) + "\n";
			}
			System.out.println("Trades: " + coin.getLastMinuteTrades());
			status += "Trades: " + coin.getLastMinuteTrades() + "\n";

			
			if (coin.getLastMinuteTrades() == lastTradesCount) {
				System.out.println("Coin still the same, waiting for a change ...");
				status += "Coin still the same, waiting for a change ..." + "\n";
			}

			
			else {
				if (actualPrice > trailPrice) {
					trailPrice = actualPrice;
					if (percentage) {
						dropLimit = trailPrice * dropLimit / 100;
					}
					System.out.println("Updating price to " + df.format(trailPrice));
					status += "Updating price to " + df.format(trailPrice) + "\n";

					if (goalPrice > 0 && goalPrice < actualPrice) {
						if (actualPrice > goalPrice) {
							goalPrice = actualPrice;
							goalAchieved = true;
							System.out.println("Goal achieved! Updating price and selling after drop!");
							status += "Goal achieved! Updating price and selling after drop!" + "\n";
						} else if (goalAchieved) {
							System.out.println("Selling based on goal for " + df.format(actualPrice) + " (" + dfPct.format((actualPrice / startPrice  - 1) * 100) + "%)");
							status += "Selling based on goal for " + df.format(actualPrice) + " (" + dfPct.format((actualPrice / startPrice  - 1) * 100) + "%)" + "\n";
							//binance.placeSellOrder(symbol, quantity, df.format(actualPrice), "vend" + symbol);
							done = true;
						}
					}

				} else {
					if (actualPrice < trailPrice - dropLimit || (minimumPrice > 0 && actualPrice < minimumPrice)) {
						if (actualPrice > startPrice && actualPrice > minimumPrice) {
							if (alert == false) {
								alert = true;
								System.out.println("Alert for possible selling at " + df.format(actualPrice));
								status += "Alert for possible selling at " + df.format(actualPrice) + "\n";
							} else {
								System.out.println("Selling for " + df.format(actualPrice) + " (" + dfPct.format((actualPrice / startPrice  - 1) * 100) + "%)");
								status += "Selling for " + df.format(actualPrice) + " (" + dfPct.format((actualPrice / startPrice  - 1) * 100) + "%)" + "\n";
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
				}


				if (System.currentTimeMillis() - startTime > 5 * 60 * 1000) {
					if (dropLimit < startPrice && minimumPrice == 0) {
						minimumPrice = (float) (startPrice * 1.003);
						System.out.println("Stop price activated " + df.format(minimumPrice));
						status += "Stop price activated " + df.format(minimumPrice) + "\n";
					}
					
					if (goalAchieved) {
						minimumPrice = goalPrice;
					}
				}
			}


			statusPane.setStatus(status);
			System.out.println("\n");


			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
			}
		}

		System.out.println(symbol + " trailing stopped");
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

	public String isHoldForGoal() {
		return holdForGoal;
	}
	
	public void setHoldForGoal(String holdForGoal) {
		this.holdForGoal = holdForGoal;
	}
}
