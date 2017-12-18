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


public class OrdersScreen extends JPanel {

	private AppData appData;
	
	private JTextField startValue;
	private JTextField dropLimit;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField txtCoinName;
	private JTextField txtCoinAmount;
	private JButton btnAddStopLimit;
	private JButton btnAddTrailingStop;


	public OrdersScreen(AppData appData) {
		this.appData = appData;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel label_5 = new JLabel("Custom Orders");
		label_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
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
		txtCoinAmount.setColumns(10);
		GridBagConstraints gbc_txtCoinAmount = new GridBagConstraints();
		gbc_txtCoinAmount.insets = new Insets(0, 0, 5, 5);
		gbc_txtCoinAmount.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCoinAmount.gridx = 1;
		gbc_txtCoinAmount.gridy = 2;
		add(txtCoinAmount, gbc_txtCoinAmount);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridwidth = 3;
		gbc_separator.insets = new Insets(15, 0, 15, 5);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 3;
		add(separator, gbc_separator);

		JLabel lblTrailingStop = new JLabel("Trailing Stop");
		lblTrailingStop.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblTrailingStop = new GridBagConstraints();
		gbc_lblTrailingStop.insets = new Insets(0, 0, 5, 5);
		gbc_lblTrailingStop.gridx = 0;
		gbc_lblTrailingStop.gridy = 4;
		add(lblTrailingStop, gbc_lblTrailingStop);

		JLabel lblStart = new JLabel("Start at");
		GridBagConstraints gbc_lblStart = new GridBagConstraints();
		gbc_lblStart.insets = new Insets(0, 0, 5, 5);
		gbc_lblStart.gridx = 0;
		gbc_lblStart.gridy = 5;
		add(lblStart, gbc_lblStart);

		JLabel lblDropLimit = new JLabel("Drop Limit");
		GridBagConstraints gbc_lblDropLimit = new GridBagConstraints();
		gbc_lblDropLimit.insets = new Insets(0, 0, 5, 5);
		gbc_lblDropLimit.gridx = 1;
		gbc_lblDropLimit.gridy = 5;
		add(lblDropLimit, gbc_lblDropLimit);

		startValue = new JTextField();
		GridBagConstraints gbc_startValue = new GridBagConstraints();
		gbc_startValue.insets = new Insets(0, 0, 5, 5);
		gbc_startValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_startValue.gridx = 0;
		gbc_startValue.gridy = 6;
		add(startValue, gbc_startValue);
		startValue.setColumns(10);

		dropLimit = new JTextField();
		dropLimit.setColumns(10);
		GridBagConstraints gbc_dropLimit = new GridBagConstraints();
		gbc_dropLimit.insets = new Insets(0, 0, 5, 5);
		gbc_dropLimit.fill = GridBagConstraints.HORIZONTAL;
		gbc_dropLimit.gridx = 1;
		gbc_dropLimit.gridy = 6;
		add(dropLimit, gbc_dropLimit);

		btnAddTrailingStop = new JButton("Add");
		GridBagConstraints gbc_btnAddTrailingStop = new GridBagConstraints();
		gbc_btnAddTrailingStop.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddTrailingStop.gridx = 3;
		gbc_btnAddTrailingStop.gridy = 6;
		add(btnAddTrailingStop, gbc_btnAddTrailingStop);

		JLabel label = new JLabel("Stop Limit Ratio");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 8;
		add(label, gbc_label);

		JLabel label_1 = new JLabel("Buy at");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 9;
		add(label_1, gbc_label_1);

		JLabel label_2 = new JLabel("Drop Limit");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 9;
		add(label_2, gbc_label_2);

		JLabel label_3 = new JLabel("Gain Limit");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 9;
		add(label_3, gbc_label_3);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 0, 5);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 0;
		gbc_textField_6.gridy = 10;
		add(textField_6, gbc_textField_6);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 0, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 1;
		gbc_textField_5.gridy = 10;
		add(textField_5, gbc_textField_5);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 0, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 2;
		gbc_textField_4.gridy = 10;
		add(textField_4, gbc_textField_4);

		btnAddStopLimit = new JButton("Add");
		GridBagConstraints gbc_btnAddStopLimit = new GridBagConstraints();
		gbc_btnAddStopLimit.gridx = 3;
		gbc_btnAddStopLimit.gridy = 10;
		add(btnAddStopLimit, gbc_btnAddStopLimit);

		configureBtns();
	}


	private void configureBtns() {
		btnAddTrailingStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		btnAddStopLimit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
	}
}
