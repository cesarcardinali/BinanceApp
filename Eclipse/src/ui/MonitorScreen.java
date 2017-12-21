package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.AppData;
import models.Coin;
import ui.custom_items.MonitorLabel;
import ui.custom_items.TrendPanel;


public class MonitorScreen extends JFrame {

	private AppData appData;
	private HashMap<String, Coin> currencies;
	
	private JPanel panel;
	private JLabel lblCoin;
	private JLabel lblTrend;
	private JLabel lblPrice;
	private JLabel lblAvg;
	private JLabel lblAvg_1;
	private JPanel panelCoins;
	private JPanel panelTrend;
	private JPanel panelPrice;
	private JPanel panelAvg3;
	private JPanel panelAvg8;
	private JPanel panel24hHighest;

	private Thread uiThread;
	private boolean active = false;
	private JPanel panel_1;
	private JLabel label;


	public MonitorScreen(AppData data) {
		appData = data;
		setTitle("Coins Monitor");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 800, 500);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 2.0, 2.0, 2.0, 2.0, 2.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		lblCoin = new JLabel("Coin");
		lblCoin.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblCoin = new GridBagConstraints();
		gbc_lblCoin.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoin.gridx = 0;
		gbc_lblCoin.gridy = 0;
		panel.add(lblCoin, gbc_lblCoin);

		lblTrend = new JLabel("Trend");
		lblTrend.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblTrend = new GridBagConstraints();
		gbc_lblTrend.insets = new Insets(0, 0, 5, 5);
		gbc_lblTrend.gridx = 1;
		gbc_lblTrend.gridy = 0;
		panel.add(lblTrend, gbc_lblTrend);

		lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 2;
		gbc_lblPrice.gridy = 0;
		panel.add(lblPrice, gbc_lblPrice);

		lblAvg = new JLabel("Avg(5m)");
		lblAvg.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblAvg = new GridBagConstraints();
		gbc_lblAvg.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvg.gridx = 3;
		gbc_lblAvg.gridy = 0;
		panel.add(lblAvg, gbc_lblAvg);

		lblAvg_1 = new JLabel("Avg(30m)");
		lblAvg_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblAvg_1 = new GridBagConstraints();
		gbc_lblAvg_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvg_1.gridx = 4;
		gbc_lblAvg_1.gridy = 0;
		panel.add(lblAvg_1, gbc_lblAvg_1);
		
		label = new JLabel("24h Highest");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 5;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);

		panelCoins = new JPanel();
		GridBagConstraints gbc_panelCoins = new GridBagConstraints();
		gbc_panelCoins.insets = new Insets(0, 0, 0, 5);
		gbc_panelCoins.fill = GridBagConstraints.BOTH;
		gbc_panelCoins.gridx = 0;
		gbc_panelCoins.gridy = 1;
		panel.add(panelCoins, gbc_panelCoins);
		panelCoins.setLayout(new BoxLayout(panelCoins, BoxLayout.Y_AXIS));

		panelTrend = new JPanel();
		GridBagConstraints gbc_panelTrend = new GridBagConstraints();
		gbc_panelTrend.insets = new Insets(0, 0, 0, 5);
		gbc_panelTrend.fill = GridBagConstraints.BOTH;
		gbc_panelTrend.gridx = 1;
		gbc_panelTrend.gridy = 1;
		panel.add(panelTrend, gbc_panelTrend);
		panelTrend.setLayout(new BoxLayout(panelTrend, BoxLayout.Y_AXIS));
		
		panelPrice = new JPanel();
		GridBagConstraints gbc_panelPrice = new GridBagConstraints();
		gbc_panelPrice.insets = new Insets(0, 0, 0, 5);
		gbc_panelPrice.fill = GridBagConstraints.BOTH;
		gbc_panelPrice.gridx = 2;
		gbc_panelPrice.gridy = 1;
		panel.add(panelPrice, gbc_panelPrice);
		panelPrice.setLayout(new BoxLayout(panelPrice, BoxLayout.Y_AXIS));

		panelAvg3 = new JPanel();
		GridBagConstraints gbc_panelAvg3 = new GridBagConstraints();
		gbc_panelAvg3.insets = new Insets(0, 0, 0, 5);
		gbc_panelAvg3.fill = GridBagConstraints.BOTH;
		gbc_panelAvg3.gridx = 3;
		gbc_panelAvg3.gridy = 1;
		panel.add(panelAvg3, gbc_panelAvg3);
		panelAvg3.setLayout(new BoxLayout(panelAvg3, BoxLayout.Y_AXIS));

		panelAvg8 = new JPanel();
		GridBagConstraints gbc_panelAvg8 = new GridBagConstraints();
		gbc_panelAvg8.insets = new Insets(0, 0, 0, 5);
		gbc_panelAvg8.fill = GridBagConstraints.BOTH;
		gbc_panelAvg8.gridx = 4;
		gbc_panelAvg8.gridy = 1;
		panel.add(panelAvg8, gbc_panelAvg8);
		panelAvg8.setLayout(new BoxLayout(panelAvg8, BoxLayout.Y_AXIS));
		
		panel24hHighest = new JPanel();
		GridBagConstraints gbc_panel1 = new GridBagConstraints();
		gbc_panel1.insets = new Insets(0, 0, 0, 5);
		gbc_panel1.fill = GridBagConstraints.BOTH;
		gbc_panel1.gridx = 5;
		gbc_panel1.gridy = 1;
		panel.add(panel24hHighest, gbc_panel1);
		panel24hHighest.setLayout(new BoxLayout(panel24hHighest, BoxLayout.Y_AXIS));

		startUiThread();
	}


	private void startUiThread() {
		active = true;
		uiThread = new Thread(new Runnable() {

			@Override
			public void run() {
				DecimalFormat df = new DecimalFormat("#.########");
				while (active) {
					currencies = appData.getWallet().getCurrencies();

					panelCoins.removeAll();
					panelPrice.removeAll();
					panelAvg3.removeAll();
					panelAvg8.removeAll();
					panelTrend.removeAll();
					panel24hHighest.removeAll();
					
					for (String c: currencies.keySet()){
						panelCoins.add(new MonitorLabel(c));
						Coin temp = currencies.get(c);
						panelPrice.add(new MonitorLabel("" + df.format(temp.getPrice())));
						
						if (temp.getLast6CandlesResolution() != null) {
							panelTrend.add(new TrendPanel(temp.getLast6CandlesResolution()));
							
							MonitorLabel m = new MonitorLabel(df.format(temp.getAveragePriceInLastXMinutes(5)));
							if(temp.getPrice() < temp.getAveragePriceInLastXMinutes(5))
								m.setColor(new Color(10, 160, 10));
							else
								m.setColor(new Color(180, 10, 10));
							panelAvg3.add(m);
							
							m = new MonitorLabel(df.format(temp.getAveragePriceInLastXMinutes(30)));
							if(temp.getPrice() < temp.getAveragePriceInLastXMinutes(30))
								m.setColor(new Color(10, 160, 10));
							else
								m.setColor(new Color(180, 10, 10));
							panelAvg8.add(m);
							
							m = new MonitorLabel(df.format(temp.getHighest24hPrice()));
							if(temp.getPrice() < temp.getHighest24hPrice())
								m.setColor(new Color(10, 160, 10));
							else
								m.setColor(new Color(180, 10, 10));
							panel24hHighest.add(m);
						}
					}
					
					repaint();
					revalidate();
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		uiThread.start();
	}
	
	
	public Rectangle getPanel_1Bounds() {
		return panel_1.getBounds();
	}
	public void setPanel_1Bounds(Rectangle bounds) {
		panel_1.setBounds(bounds);
	}
}
