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
import javax.swing.JCheckBox;
import javax.swing.JTextField;


public class LoginPane extends JPanel {

	private JPasswordField key;
	private JPasswordField secret;
	private JButton btnDone;
	private MainWindow ui;
	private AppData appData;
	private JTextField txtOptional;
	private JTextField txtAccName;
	private JCheckBox chckbxSaveAccount;
	private JButton btnLogIn;


	public LoginPane(AppData data) {
		// ui = screen;
		appData = data;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 20, 0, 0, 20, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		
		JLabel label = new JLabel("Account Name");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 4;
		add(label, gbc_label);
		
		txtOptional = new JTextField();
		txtOptional.setText("Required if you want to save your account");
		GridBagConstraints gbc_txtOptional = new GridBagConstraints();
		gbc_txtOptional.insets = new Insets(0, 0, 5, 5);
		gbc_txtOptional.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOptional.gridx = 2;
		gbc_txtOptional.gridy = 4;
		add(txtOptional, gbc_txtOptional);
		txtOptional.setColumns(10);
		
		chckbxSaveAccount = new JCheckBox("Save Account?");
		GridBagConstraints gbc_chckbxSaveAccount = new GridBagConstraints();
		gbc_chckbxSaveAccount.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSaveAccount.gridx = 2;
		gbc_chckbxSaveAccount.gridy = 5;
		add(chckbxSaveAccount, gbc_chckbxSaveAccount);

		btnDone = new JButton("Done!");
		GridBagConstraints gbc_btnDone = new GridBagConstraints();
		gbc_btnDone.insets = new Insets(10, 0, 5, 5);
		gbc_btnDone.gridx = 2;
		gbc_btnDone.gridy = 6;
		add(btnDone, gbc_btnDone);
		
		JLabel lblLogInUsing = new JLabel("Log in using acc name");
		GridBagConstraints gbc_lblLogInUsing = new GridBagConstraints();
		gbc_lblLogInUsing.anchor = GridBagConstraints.EAST;
		gbc_lblLogInUsing.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogInUsing.gridx = 1;
		gbc_lblLogInUsing.gridy = 8;
		add(lblLogInUsing, gbc_lblLogInUsing);
		
		txtAccName = new JTextField();
		txtAccName.setText("Acc Name");
		GridBagConstraints gbc_txtAccName = new GridBagConstraints();
		gbc_txtAccName.insets = new Insets(0, 0, 5, 5);
		gbc_txtAccName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAccName.gridx = 2;
		gbc_txtAccName.gridy = 8;
		add(txtAccName, gbc_txtAccName);
		txtAccName.setColumns(10);
		
		btnLogIn = new JButton("Log in");
		GridBagConstraints gbc_btnLogIn = new GridBagConstraints();
		gbc_btnLogIn.insets = new Insets(0, 0, 0, 5);
		gbc_btnLogIn.gridx = 2;
		gbc_btnLogIn.gridy = 9;
		add(btnLogIn, gbc_btnLogIn);

		configureBtns();
	}


	private void configureBtns() {
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String apiKey;
				String apiSecret;
				String accName;
				if (key.getPassword().length < 1 || secret.getPassword().length < 1) {
					System.out.println("key or secret too short");
				} else {
					apiKey = new String(key.getPassword());
					apiSecret = new String(secret.getPassword());
					accName = txtOptional.getText();
					
					if (chckbxSaveAccount.isSelected()) {
						appData.addAccount(accName, apiKey, apiSecret);
					}
					
					appData.logIn(apiKey, apiSecret);
				}
			}
		});
		
		
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String accName = txtAccName.getText();
				if (appData.hasAccount(accName)) {
					appData.logIn(accName);
				} else {
					txtAccName.setText("This acc doesnt exists");
				}
			}
		});
	}

}
