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
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import models.AppData;


public class LoginPane extends JPanel {

	private JPasswordField key;
	private JPasswordField secret;
	private JButton btnDone;
	private MainWindow ui;
	private AppData appData;


	public LoginPane(AppData data) {
		// ui = screen;
		appData = data;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 20, 0, 0, 20, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblHeyToStart = new JLabel("Hey! To start using this shit, please, add your API information");
		lblHeyToStart.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblHeyToStart = new GridBagConstraints();
		gbc_lblHeyToStart.anchor = GridBagConstraints.WEST;
		gbc_lblHeyToStart.gridwidth = 2;
		gbc_lblHeyToStart.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeyToStart.gridx = 1;
		gbc_lblHeyToStart.gridy = 0;
		add(lblHeyToStart, gbc_lblHeyToStart);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 2;
		gbc_separator.insets = new Insets(0, 0, 20, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 1;
		add(separator, gbc_separator);

		JLabel lblApiKey = new JLabel("Api Key");
		GridBagConstraints gbc_lblApiKey = new GridBagConstraints();
		gbc_lblApiKey.anchor = GridBagConstraints.EAST;
		gbc_lblApiKey.insets = new Insets(0, 0, 5, 5);
		gbc_lblApiKey.gridx = 1;
		gbc_lblApiKey.gridy = 2;
		add(lblApiKey, gbc_lblApiKey);

		key = new JPasswordField();
		GridBagConstraints gbc_key = new GridBagConstraints();
		gbc_key.insets = new Insets(0, 0, 5, 5);
		gbc_key.fill = GridBagConstraints.HORIZONTAL;
		gbc_key.gridx = 2;
		gbc_key.gridy = 2;
		add(key, gbc_key);

		JLabel lblApiSecret = new JLabel("Api Secret");
		GridBagConstraints gbc_lblApiSecret = new GridBagConstraints();
		gbc_lblApiSecret.anchor = GridBagConstraints.EAST;
		gbc_lblApiSecret.insets = new Insets(0, 0, 5, 5);
		gbc_lblApiSecret.gridx = 1;
		gbc_lblApiSecret.gridy = 3;
		add(lblApiSecret, gbc_lblApiSecret);

		secret = new JPasswordField();
		GridBagConstraints gbc_secret = new GridBagConstraints();
		gbc_secret.insets = new Insets(0, 0, 5, 5);
		gbc_secret.fill = GridBagConstraints.HORIZONTAL;
		gbc_secret.gridx = 2;
		gbc_secret.gridy = 3;
		add(secret, gbc_secret);

		btnDone = new JButton("Done!");
		GridBagConstraints gbc_btnDone = new GridBagConstraints();
		gbc_btnDone.insets = new Insets(10, 0, 0, 5);
		gbc_btnDone.gridx = 2;
		gbc_btnDone.gridy = 4;
		add(btnDone, gbc_btnDone);

		configureBtns();
	}


	private void configureBtns() {
		btnDone.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String apiKey;
				String apiSecret;
				if (key.getPassword().length < 1 || secret.getPassword().length < 1) {
					System.out.println("key or secret too short");
				} else {
					apiKey = new String(key.getPassword());
					apiSecret = new String(secret.getPassword());
					appData.logIn(apiKey, apiSecret);
					System.out.println("loggedin");
					System.out.println("key: " + apiKey);
					System.out.println("sec: " + apiSecret);
				}
			}
		});
	}

}
