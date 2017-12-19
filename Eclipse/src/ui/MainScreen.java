package ui;

import javax.swing.JPanel;
import models.AppData;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;


public class MainScreen extends JPanel {

	private AppData appData;


	public MainScreen(AppData appData) {
		this.appData = appData;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblMain = new JLabel("Main");
		GridBagConstraints gbc_lblMain = new GridBagConstraints();
		gbc_lblMain.gridx = 6;
		gbc_lblMain.gridy = 4;
		add(lblMain, gbc_lblMain);

	}
	
	public void init() {
		
	}
}
