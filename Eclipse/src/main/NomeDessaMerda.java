package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import models.Wallet;
import supportive.BinanceApi;
import ui.LoginPane;
import ui.MainScreen;


public class NomeDessaMerda extends JFrame {
	// Variables ------------------------------------------------------------------------------------------------------------------
	private JPanel contentPane;
	
	private Wallet appData;
	private BinanceApi binance;
	// -----------------------------------------------------------------------------------------------------------------------------

	
	// Startup ---------------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NomeDessaMerda frame = new NomeDessaMerda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	// -----------------------------------------------------------------------------------------------------------------------------
	

	// Constructor -----------------------------------------------------------------------------------------------------------------
	public NomeDessaMerda() {
		setTitle("BinanceFreakShow");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 350);
		
		contentPane = new LoginPane(this);
		contentPane.setBorder(new EmptyBorder(8, 10, 5, 10));
		setContentPane(contentPane);
		
		initVariables();
	}
	// -----------------------------------------------------------------------------------------------------------------------------
	
	
	// Main methods ----------------------------------------------------------------------------------------------------------------
	public void logIn(String apiKey, String apiSecret) {
		setBinance(new BinanceApi(apiKey, apiSecret));
		contentPane = new MainScreen();
		setContentPane(contentPane);
		revalidate();
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
		return appData;
	}


	public void setAppData(Wallet appData) {
		this.appData = appData;
	}
	// -----------------------------------------------------------------------------------------------------------------------------
}
