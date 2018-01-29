package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import models.AppData;


public class MainWindow extends JFrame {

	// Variables- ------------------------------------------------------------------------------------------------------------------
	private AppData appData;
	private JPanel topMenu;

	private LoginPane LoginScreen;
	private MainScreen mainScreen;
	private OrdersScreen ordersScreen;
	private WalletScreen walletScreen;
	private MonitorScreen monitorScreen;

	private JToggleButton tglbtnMain;
	private JToggleButton tglbtnOrders;
	private JToggleButton tglbtnWallet;
	// -----------------------------------------------------------------------------------------------------------------------------


	// Constructor -----------------------------------------------------------------------------------------------------------------
	public MainWindow() {
		setTitle("BinanceFreakShow");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);

		topMenu = new JPanel();
		topMenu.setBackground(Color.GRAY);
		getContentPane().add(topMenu, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 10, 0, 0, 0, 20, 0 };
		gbl_panel.rowHeights = new int[] { 42, 0 };
		gbl_panel.columnWeights = new double[] { 3.0, 1.0, 1.0, 1.0, 3.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		topMenu.setLayout(gbl_panel);

		ButtonGroup btnGroup = new ButtonGroup();

		tglbtnMain = new JToggleButton("Main");
		tglbtnMain.setSelected(true);
		GridBagConstraints gbc_tglbtnMain = new GridBagConstraints();
		gbc_tglbtnMain.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnMain.gridx = 1;
		gbc_tglbtnMain.gridy = 0;
		topMenu.add(tglbtnMain, gbc_tglbtnMain);

		tglbtnOrders = new JToggleButton("Orders");
		GridBagConstraints gbc_tglbtnOrders = new GridBagConstraints();
		gbc_tglbtnOrders.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnOrders.gridx = 2;
		gbc_tglbtnOrders.gridy = 0;
		topMenu.add(tglbtnOrders, gbc_tglbtnOrders);

		tglbtnWallet = new JToggleButton("Wallet");
		GridBagConstraints gbc_tglbtnWallet = new GridBagConstraints();
		gbc_tglbtnWallet.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnWallet.gridx = 3;
		gbc_tglbtnWallet.gridy = 0;
		topMenu.add(tglbtnWallet, gbc_tglbtnWallet);

		btnGroup.add(tglbtnMain);
		btnGroup.add(tglbtnOrders);
		btnGroup.add(tglbtnWallet);

		configureBtns();
	}
	// -----------------------------------------------------------------------------------------------------------------------------


	// Main methods ----------------------------------------------------------------------------------------------------------------
	public void start(AppData appData) {
		this.appData = appData;
		topMenu.setVisible(false);
		LoginScreen = new LoginPane(appData);
		getContentPane().add(LoginScreen, BorderLayout.CENTER);
		this.setVisible(true);
	}


	public void goToMainScreen() {
		if (mainScreen == null)
			mainScreen = new MainScreen(appData);

		if (topMenu.isVisible() == false)
			topMenu.setVisible(true);

		getContentPane().remove(1);
		getContentPane().add(mainScreen, BorderLayout.CENTER);

		repaint();
		revalidate();
	}


	public void goToOrdersScreen() {
		if (ordersScreen == null)
			ordersScreen = new OrdersScreen(appData);

		if (topMenu.isVisible() == false)
			topMenu.setVisible(true);

		getContentPane().remove(1);
		getContentPane().add(ordersScreen, BorderLayout.CENTER);

		repaint();
		revalidate();
	}


	public void goToWalletScreen() {
		/*if (walletScreen == null)
			walletScreen = new WalletScreen(appData);

		if (topMenu.isVisible() == false)
			topMenu.setVisible(true);

		getContentPane().remove(1);
		getContentPane().add(walletScreen, BorderLayout.CENTER);

		repaint();
		revalidate();*/
		
		if (monitorScreen == null) {
			monitorScreen = new MonitorScreen(appData);
			monitorScreen.setVisible(true);
		} else {
			monitorScreen.setVisible(true);
		}

		if (topMenu.isVisible() == false)
			topMenu.setVisible(true);
	}
	// -----------------------------------------------------------------------------------------------------------------------------


	// Supportive methods ----------------------------------------------------------------------------------------------------------
	private void configureBtns() {
		tglbtnMain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tglbtnMain.isSelected()) {
					goToMainScreen();
				}
				System.out.println("main " + e.getActionCommand());
			}
		});

		tglbtnOrders.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tglbtnOrders.isSelected()) {
					goToOrdersScreen();
				}

				System.out.println("orders " + tglbtnOrders.isSelected() + "\n");
			}
		});

		tglbtnWallet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tglbtnWallet.isSelected()) {
					goToWalletScreen();
				}
			}
		});
	}
	// -----------------------------------------------------------------------------------------------------------------------------
}
