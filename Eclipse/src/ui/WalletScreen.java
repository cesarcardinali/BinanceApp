package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import models.AppData;


public class WalletScreen extends JPanel {

	private AppData appData;


	public WalletScreen(AppData appData) {
		setBorder(new EmptyBorder(5, 10, 3, 10));
		this.appData = appData;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblMain = new JLabel("Wallet");
		GridBagConstraints gbc_lblMain = new GridBagConstraints();
		gbc_lblMain.gridx = 6;
		gbc_lblMain.gridy = 4;
		add(lblMain, gbc_lblMain);

	}
}
