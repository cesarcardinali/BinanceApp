package ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MonitorScreen extends JFrame {
	private JPanel panel;
	private JLabel lblCoin;
	private JLabel lblTrend;
	private JLabel lblPrice;
	private JLabel lblAvg;
	private JLabel lblAvg_1;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	
	private Thread uiThread; 

	public MonitorScreen() {
		setType(Type.POPUP);
		setTitle("Coins Monitor");
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblCoin = new JLabel("Coin");
		GridBagConstraints gbc_lblCoin = new GridBagConstraints();
		gbc_lblCoin.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoin.gridx = 0;
		gbc_lblCoin.gridy = 0;
		panel.add(lblCoin, gbc_lblCoin);
		
		lblTrend = new JLabel("Trend");
		GridBagConstraints gbc_lblTrend = new GridBagConstraints();
		gbc_lblTrend.insets = new Insets(0, 0, 5, 5);
		gbc_lblTrend.gridx = 1;
		gbc_lblTrend.gridy = 0;
		panel.add(lblTrend, gbc_lblTrend);
		
		lblPrice = new JLabel("Price");
		GridBagConstraints gbc_lblPrice = new GridBagConstraints();
		gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrice.gridx = 2;
		gbc_lblPrice.gridy = 0;
		panel.add(lblPrice, gbc_lblPrice);
		
		lblAvg = new JLabel("Avg(3m)");
		GridBagConstraints gbc_lblAvg = new GridBagConstraints();
		gbc_lblAvg.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvg.gridx = 3;
		gbc_lblAvg.gridy = 0;
		panel.add(lblAvg, gbc_lblAvg);
		
		lblAvg_1 = new JLabel("Avg(8m)");
		GridBagConstraints gbc_lblAvg_1 = new GridBagConstraints();
		gbc_lblAvg_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblAvg_1.gridx = 4;
		gbc_lblAvg_1.gridy = 0;
		panel.add(lblAvg_1, gbc_lblAvg_1);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 0, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 2;
		gbc_panel_3.gridy = 1;
		panel.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 0, 5);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 3;
		gbc_panel_4.gridy = 1;
		panel.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 4;
		gbc_panel_5.gridy = 1;
		panel.add(panel_5, gbc_panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));

	}

}
