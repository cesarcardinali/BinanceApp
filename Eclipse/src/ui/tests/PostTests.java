package ui.tests;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import models.AppData;
import supportive.BinanceApi;


public class PostTests extends JFrame {

	private BinanceApi binance;
	private JPanel contentPane;
	private JTextField txtPostUrlHere;
	private JLabel lblPostUrl;
	private JLabel lblPostReturn;
	private JTextPane txtpnResults;
	private JButton btnSend;


	/**
	 * Create the frame.
	 */
	public PostTests(AppData appData) {
		setTitle("Post tests");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 673, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblPostUrl = new JLabel("Post URL");
		GridBagConstraints gbc_lblPostUrl = new GridBagConstraints();
		gbc_lblPostUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblPostUrl.gridx = 0;
		gbc_lblPostUrl.gridy = 0;
		contentPane.add(lblPostUrl, gbc_lblPostUrl);
		
		txtPostUrlHere = new JTextField();
		txtPostUrlHere.setText("post url here");
		GridBagConstraints gbc_txtPostUrlHere = new GridBagConstraints();
		gbc_txtPostUrlHere.insets = new Insets(0, 0, 5, 5);
		gbc_txtPostUrlHere.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPostUrlHere.gridx = 0;
		gbc_txtPostUrlHere.gridy = 1;
		contentPane.add(txtPostUrlHere, gbc_txtPostUrlHere);
		txtPostUrlHere.setColumns(10);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtpnResults.setText(binance.testSignAndPostUrl(txtPostUrlHere.getText()));
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 5, 0);
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 1;
		contentPane.add(btnSend, gbc_btnSend);
		
		lblPostReturn = new JLabel("Post Result");
		GridBagConstraints gbc_lblPostReturn = new GridBagConstraints();
		gbc_lblPostReturn.insets = new Insets(0, 0, 5, 5);
		gbc_lblPostReturn.gridx = 0;
		gbc_lblPostReturn.gridy = 2;
		contentPane.add(lblPostReturn, gbc_lblPostReturn);
		
		txtpnResults = new JTextPane();
		txtpnResults.setBorder(new LineBorder(Color.LIGHT_GRAY));
		txtpnResults.setText("Results");
		GridBagConstraints gbc_txtpnResults = new GridBagConstraints();
		gbc_txtpnResults.gridwidth = 2;
		gbc_txtpnResults.fill = GridBagConstraints.BOTH;
		gbc_txtpnResults.gridx = 0;
		gbc_txtpnResults.gridy = 3;
		contentPane.add(txtpnResults, gbc_txtpnResults);
		

		binance = appData.getBinance();
	}

}
