package ui;


import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import models.AppData;
import monitors.CoinMonitor;
import actuators.TrailingOrder;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;


public class OrdersScreen extends JPanel {

	private AppData appData;
	
	private JTextField txtTrailingBuyValue;
	private JTextField txtTrailingDropLimit;
	private JTextField txtStopGainLimit;
	private JTextField txtStopLowLimit;
	private JTextField txtStopBuyLimit;
	private JTextField txtCoinName;
	private JTextField txtCoinAmount;
	private JButton btnAddStopLimit;
	private JButton btnAddTrailingStop;
	private JButton btnStop;
	private JTextField textField;
	private JTextField textField_1;


	public OrdersScreen(AppData appData) {
		this.appData = appData;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 200, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel label_5 = new JLabel("Create Custom Orders");
		label_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.gridwidth = 3;
		gbc_label_5.anchor = GridBagConstraints.WEST;
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 0;
		gbc_label_5.gridy = 0;
		add(label_5, gbc_label_5);

		JLabel label_6 = new JLabel("Coin Name");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.anchor = GridBagConstraints.EAST;
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 0;
		gbc_label_6.gridy = 1;
		add(label_6, gbc_label_6);

		txtCoinName = new JTextField();
		txtCoinName.setText("IOTABTC");
		txtCoinName.setColumns(10);
		GridBagConstraints gbc_txtCoinName = new GridBagConstraints();
		gbc_txtCoinName.insets = new Insets(0, 0, 5, 5);
		gbc_txtCoinName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCoinName.gridx = 1;
		gbc_txtCoinName.gridy = 1;
		add(txtCoinName, gbc_txtCoinName);

		JLabel label_7 = new JLabel("Amount");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.anchor = GridBagConstraints.EAST;
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 0;
		gbc_label_7.gridy = 2;
		add(label_7, gbc_label_7);

		txtCoinAmount = new JTextField();
		txtCoinAmount.setText("10");
		txtCoinAmount.setColumns(10);
		GridBagConstraints gbc_txtCoinAmount = new GridBagConstraints();
		gbc_txtCoinAmount.insets = new Insets(0, 0, 5, 5);
		gbc_txtCoinAmount.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCoinAmount.gridx = 1;
		gbc_txtCoinAmount.gridy = 2;
		add(txtCoinAmount, gbc_txtCoinAmount);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
				JLabel lblTrailingStop = new JLabel("Trailing Stop");
				GridBagConstraints gbc_lblTrailingStop = new GridBagConstraints();
				gbc_lblTrailingStop.anchor = GridBagConstraints.WEST;
				gbc_lblTrailingStop.gridwidth = 3;
				gbc_lblTrailingStop.insets = new Insets(0, 0, 5, 5);
				gbc_lblTrailingStop.gridx = 0;
				gbc_lblTrailingStop.gridy = 0;
				panel.add(lblTrailingStop, gbc_lblTrailingStop);
				lblTrailingStop.setFont(new Font("Tahoma", Font.BOLD, 12));
				
				JLabel label_8 = new JLabel("Buy at");
				GridBagConstraints gbc_label_8 = new GridBagConstraints();
				gbc_label_8.insets = new Insets(0, 0, 5, 5);
				gbc_label_8.gridx = 0;
				gbc_label_8.gridy = 1;
				panel.add(label_8, gbc_label_8);
				
						JLabel lblStart = new JLabel("Start at");
						GridBagConstraints gbc_lblStart = new GridBagConstraints();
						gbc_lblStart.insets = new Insets(0, 0, 5, 5);
						gbc_lblStart.gridx = 1;
						gbc_lblStart.gridy = 1;
						panel.add(lblStart, gbc_lblStart);
						
								JLabel lblDropLimit = new JLabel("Drop Pace Limit");
								GridBagConstraints gbc_lblDropLimit = new GridBagConstraints();
								gbc_lblDropLimit.insets = new Insets(0, 0, 5, 5);
								gbc_lblDropLimit.gridx = 2;
								gbc_lblDropLimit.gridy = 1;
								panel.add(lblDropLimit, gbc_lblDropLimit);
								
								JLabel label_4 = new JLabel("Goal");
								GridBagConstraints gbc_label_4 = new GridBagConstraints();
								gbc_label_4.insets = new Insets(0, 0, 5, 5);
								gbc_label_4.gridx = 3;
								gbc_label_4.gridy = 1;
								panel.add(label_4, gbc_label_4);
								
								textField_1 = new JTextField();
								GridBagConstraints gbc_textField_1 = new GridBagConstraints();
								gbc_textField_1.insets = new Insets(0, 0, 5, 5);
								gbc_textField_1.gridx = 0;
								gbc_textField_1.gridy = 2;
								panel.add(textField_1, gbc_textField_1);
								textField_1.setText("0.00021");
								textField_1.setColumns(10);
								
										txtTrailingBuyValue = new JTextField();
										GridBagConstraints gbc_txtTrailingBuyValue = new GridBagConstraints();
										gbc_txtTrailingBuyValue.insets = new Insets(0, 0, 5, 5);
										gbc_txtTrailingBuyValue.gridx = 1;
										gbc_txtTrailingBuyValue.gridy = 2;
										panel.add(txtTrailingBuyValue, gbc_txtTrailingBuyValue);
										txtTrailingBuyValue.setToolTipText("Not necessary if using the buy at");
										txtTrailingBuyValue.setText("0.00021");
										txtTrailingBuyValue.setColumns(10);
										
												txtTrailingDropLimit = new JTextField();
												GridBagConstraints gbc_txtTrailingDropLimit = new GridBagConstraints();
												gbc_txtTrailingDropLimit.insets = new Insets(0, 0, 5, 5);
												gbc_txtTrailingDropLimit.gridx = 2;
												gbc_txtTrailingDropLimit.gridy = 2;
												panel.add(txtTrailingDropLimit, gbc_txtTrailingDropLimit);
												txtTrailingDropLimit.setText("0.0000015");
												txtTrailingDropLimit.setColumns(10);
												
												textField = new JTextField();
												GridBagConstraints gbc_textField = new GridBagConstraints();
												gbc_textField.insets = new Insets(0, 0, 5, 5);
												gbc_textField.gridx = 3;
												gbc_textField.gridy = 2;
												panel.add(textField, gbc_textField);
												textField.setText("0.0000015");
												textField.setColumns(10);
												
														btnAddTrailingStop = new JButton("Add");
														GridBagConstraints gbc_btnAddTrailingStop = new GridBagConstraints();
														gbc_btnAddTrailingStop.insets = new Insets(0, 0, 5, 5);
														gbc_btnAddTrailingStop.gridx = 4;
														gbc_btnAddTrailingStop.gridy = 2;
														panel.add(btnAddTrailingStop, gbc_btnAddTrailingStop);
														
														btnStop = new JButton("Stop");
														GridBagConstraints gbc_btnStop = new GridBagConstraints();
														gbc_btnStop.insets = new Insets(0, 0, 5, 0);
														gbc_btnStop.gridx = 5;
														gbc_btnStop.gridy = 2;
														panel.add(btnStop, gbc_btnStop);
														
																JLabel label = new JLabel("Stop Limit Ratio");
																GridBagConstraints gbc_label = new GridBagConstraints();
																gbc_label.insets = new Insets(0, 0, 5, 5);
																gbc_label.gridx = 0;
																gbc_label.gridy = 4;
																panel.add(label, gbc_label);
																label.setFont(new Font("Tahoma", Font.BOLD, 12));
																
																		JLabel label_1 = new JLabel("Buy at");
																		GridBagConstraints gbc_label_1 = new GridBagConstraints();
																		gbc_label_1.insets = new Insets(0, 0, 5, 5);
																		gbc_label_1.gridx = 0;
																		gbc_label_1.gridy = 5;
																		panel.add(label_1, gbc_label_1);
																						
																						JLabel label_2 = new JLabel("Drop Limit");
																						GridBagConstraints gbc_label_2 = new GridBagConstraints();
																						gbc_label_2.insets = new Insets(0, 0, 5, 5);
																						gbc_label_2.gridx = 1;
																						gbc_label_2.gridy = 5;
																						panel.add(label_2, gbc_label_2);
																				
																						JLabel label_3 = new JLabel("Gain Limit");
																						GridBagConstraints gbc_label_3 = new GridBagConstraints();
																						gbc_label_3.insets = new Insets(0, 0, 5, 5);
																						gbc_label_3.gridx = 2;
																						gbc_label_3.gridy = 5;
																						panel.add(label_3, gbc_label_3);
																		
																				txtStopBuyLimit = new JTextField();
																				GridBagConstraints gbc_txtStopBuyLimit = new GridBagConstraints();
																				gbc_txtStopBuyLimit.insets = new Insets(0, 0, 0, 5);
																				gbc_txtStopBuyLimit.gridx = 0;
																				gbc_txtStopBuyLimit.gridy = 6;
																				panel.add(txtStopBuyLimit, gbc_txtStopBuyLimit);
																				txtStopBuyLimit.setColumns(10);
																								
																										txtStopLowLimit = new JTextField();
																										GridBagConstraints gbc_txtStopLowLimit = new GridBagConstraints();
																										gbc_txtStopLowLimit.insets = new Insets(0, 0, 0, 5);
																										gbc_txtStopLowLimit.gridx = 1;
																										gbc_txtStopLowLimit.gridy = 6;
																										panel.add(txtStopLowLimit, gbc_txtStopLowLimit);
																										txtStopLowLimit.setColumns(10);
																						
																								txtStopGainLimit = new JTextField();
																								GridBagConstraints gbc_txtStopGainLimit = new GridBagConstraints();
																								gbc_txtStopGainLimit.insets = new Insets(0, 0, 0, 5);
																								gbc_txtStopGainLimit.gridx = 2;
																								gbc_txtStopGainLimit.gridy = 6;
																								panel.add(txtStopGainLimit, gbc_txtStopGainLimit);
																								txtStopGainLimit.setColumns(10);
																						
																								btnAddStopLimit = new JButton("Add");
																								GridBagConstraints gbc_btnAddStopLimit = new GridBagConstraints();
																								gbc_btnAddStopLimit.insets = new Insets(0, 0, 0, 5);
																								gbc_btnAddStopLimit.gridx = 4;
																								gbc_btnAddStopLimit.gridy = 6;
																								panel.add(btnAddStopLimit, gbc_btnAddStopLimit);
																						
																								JSeparator separator = new JSeparator();
																								GridBagConstraints gbc_separator = new GridBagConstraints();
																								gbc_separator.fill = GridBagConstraints.BOTH;
																								gbc_separator.gridwidth = 6;
																								gbc_separator.insets = new Insets(8, 0, 8, 0);
																								gbc_separator.gridx = 0;
																								gbc_separator.gridy = 4;
																								add(separator, gbc_separator);
																								
																								JScrollPane scrollPane_1 = new JScrollPane();
																								GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
																								gbc_scrollPane_1.gridwidth = 6;
																								gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
																								gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
																								gbc_scrollPane_1.gridx = 0;
																								gbc_scrollPane_1.gridy = 5;
																								add(scrollPane_1, gbc_scrollPane_1);
																								
																								JPanel panel_1 = new JPanel();
																								scrollPane_1.setViewportView(panel_1);
																								GridBagLayout gbl_panel_1 = new GridBagLayout();
																								gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
																								gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
																								gbl_panel_1.columnWeights = new double[]{3.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0, Double.MIN_VALUE};
																								gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
																								panel_1.setLayout(gbl_panel_1);
																								
																								JLabel label_9 = new JLabel("Your orders and coin status");
																								label_9.setFont(new Font("Tahoma", Font.BOLD, 12));
																								GridBagConstraints gbc_label_9 = new GridBagConstraints();
																								gbc_label_9.anchor = GridBagConstraints.WEST;
																								gbc_label_9.gridwidth = 5;
																								gbc_label_9.insets = new Insets(0, 0, 5, 5);
																								gbc_label_9.gridx = 0;
																								gbc_label_9.gridy = 0;
																								panel_1.add(label_9, gbc_label_9);
																								
																								JLabel lblNewLabel = new JLabel("Coin");
																								GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
																								gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
																								gbc_lblNewLabel.gridx = 0;
																								gbc_lblNewLabel.gridy = 1;
																								panel_1.add(lblNewLabel, gbc_lblNewLabel);
																								
																								JLabel lblBought = new JLabel("Buy/Sell");
																								GridBagConstraints gbc_lblBought = new GridBagConstraints();
																								gbc_lblBought.insets = new Insets(0, 0, 5, 5);
																								gbc_lblBought.gridx = 1;
																								gbc_lblBought.gridy = 1;
																								panel_1.add(lblBought, gbc_lblBought);
																								
																								JLabel lblPrice = new JLabel("Buy/Sell Price");
																								GridBagConstraints gbc_lblPrice = new GridBagConstraints();
																								gbc_lblPrice.insets = new Insets(0, 0, 5, 5);
																								gbc_lblPrice.gridx = 2;
																								gbc_lblPrice.gridy = 1;
																								panel_1.add(lblPrice, gbc_lblPrice);
																								
																								JLabel lblAmout = new JLabel("Amout");
																								GridBagConstraints gbc_lblAmout = new GridBagConstraints();
																								gbc_lblAmout.insets = new Insets(0, 0, 5, 5);
																								gbc_lblAmout.gridx = 3;
																								gbc_lblAmout.gridy = 1;
																								panel_1.add(lblAmout, gbc_lblAmout);
																								
																								JLabel label_10 = new JLabel("Price Now");
																								GridBagConstraints gbc_label_10 = new GridBagConstraints();
																								gbc_label_10.insets = new Insets(0, 0, 5, 5);
																								gbc_label_10.gridx = 4;
																								gbc_label_10.gridy = 1;
																								panel_1.add(label_10, gbc_label_10);
																								
																								JLabel label_11 = new JLabel("Last Price");
																								GridBagConstraints gbc_label_11 = new GridBagConstraints();
																								gbc_label_11.insets = new Insets(0, 0, 5, 5);
																								gbc_label_11.gridx = 5;
																								gbc_label_11.gridy = 1;
																								panel_1.add(label_11, gbc_label_11);
																								
																								JLabel lblNewLabel_1 = new JLabel("Trades");
																								GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
																								gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
																								gbc_lblNewLabel_1.gridx = 6;
																								gbc_lblNewLabel_1.gridy = 1;
																								panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
																								
																								JPanel panel_2 = new JPanel();
																								GridBagConstraints gbc_panel_2 = new GridBagConstraints();
																								gbc_panel_2.insets = new Insets(0, 0, 0, 5);
																								gbc_panel_2.fill = GridBagConstraints.BOTH;
																								gbc_panel_2.gridx = 0;
																								gbc_panel_2.gridy = 2;
																								panel_1.add(panel_2, gbc_panel_2);
																								panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
																								
																								JPanel panel_3 = new JPanel();
																								GridBagConstraints gbc_panel_3 = new GridBagConstraints();
																								gbc_panel_3.insets = new Insets(0, 0, 0, 5);
																								gbc_panel_3.fill = GridBagConstraints.BOTH;
																								gbc_panel_3.gridx = 1;
																								gbc_panel_3.gridy = 2;
																								panel_1.add(panel_3, gbc_panel_3);
																								panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));
																								
																								JPanel panel_5 = new JPanel();
																								GridBagConstraints gbc_panel_5 = new GridBagConstraints();
																								gbc_panel_5.insets = new Insets(0, 0, 0, 5);
																								gbc_panel_5.fill = GridBagConstraints.BOTH;
																								gbc_panel_5.gridx = 2;
																								gbc_panel_5.gridy = 2;
																								panel_1.add(panel_5, gbc_panel_5);
																								panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
																								
																								JPanel panel_4 = new JPanel();
																								GridBagConstraints gbc_panel_4 = new GridBagConstraints();
																								gbc_panel_4.insets = new Insets(0, 0, 0, 5);
																								gbc_panel_4.fill = GridBagConstraints.BOTH;
																								gbc_panel_4.gridx = 3;
																								gbc_panel_4.gridy = 2;
																								panel_1.add(panel_4, gbc_panel_4);
																								panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
																								
																								JPanel panel_6 = new JPanel();
																								GridBagConstraints gbc_panel_6 = new GridBagConstraints();
																								gbc_panel_6.insets = new Insets(0, 0, 0, 5);
																								gbc_panel_6.fill = GridBagConstraints.BOTH;
																								gbc_panel_6.gridx = 4;
																								gbc_panel_6.gridy = 2;
																								panel_1.add(panel_6, gbc_panel_6);
																								panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));
																								
																								JPanel panel_7 = new JPanel();
																								GridBagConstraints gbc_panel_7 = new GridBagConstraints();
																								gbc_panel_7.insets = new Insets(0, 0, 0, 5);
																								gbc_panel_7.fill = GridBagConstraints.BOTH;
																								gbc_panel_7.gridx = 5;
																								gbc_panel_7.gridy = 2;
																								panel_1.add(panel_7, gbc_panel_7);
																								panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));
																								
																								JPanel panel_8 = new JPanel();
																								GridBagConstraints gbc_panel_8 = new GridBagConstraints();
																								gbc_panel_8.fill = GridBagConstraints.BOTH;
																								gbc_panel_8.gridx = 6;
																								gbc_panel_8.gridy = 2;
																								panel_1.add(panel_8, gbc_panel_8);
																								panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));

		configureBtns();
	}


	private void configureBtns() {
		btnAddTrailingStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String coin = txtCoinName.getText();
				String quantity = txtCoinAmount.getText();
				String start = txtTrailingBuyValue.getText();
				String drop = txtTrailingDropLimit.getText();
				
				// TODO remove it (teste)
				Thread iotaMonitor = new Thread(new CoinMonitor(appData, coin));
				iotaMonitor.start();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				TrailingOrder iotaTrail = new TrailingOrder(appData, coin, start, drop, quantity);
				Thread iotaTrailing = new Thread(new TrailingOrder(appData, coin, start, drop, quantity));
				iotaTrailing.start();
				
				appData.addTrailing(iotaTrail);
				// END --------
			}
		});

		btnAddStopLimit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				appData.stopAllTrailings();
			}
		});
	}
}
