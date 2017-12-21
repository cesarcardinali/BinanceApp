package ui.custom_items;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TrendPanel extends JPanel {

	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;


	public TrendPanel() {
		setMaximumSize(new Dimension(32767, 15));
		setSize(new Dimension(0, 20));
		setPreferredSize(new Dimension(10, 20));
		setLayout(new GridLayout(1, 0, 0, 0));

		lblNewLabel = new JLabel();
		add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel();
		add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel();
		add(lblNewLabel_2);

		lblNewLabel_4 = new JLabel();
		add(lblNewLabel_4);

		lblNewLabel_3 = new JLabel();
		add(lblNewLabel_3);

		lblNewLabel_5 = new JLabel();
		add(lblNewLabel_5);
	}
	
	
	public TrendPanel(String data[]) {
		setMaximumSize(new Dimension(32767, 15));
		setSize(new Dimension(0, 20));
		setPreferredSize(new Dimension(10, 20));
		setLayout(new GridLayout(1, 0, 0, 0));

		lblNewLabel = new JLabel();
		add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel();
		add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel();
		add(lblNewLabel_2);

		lblNewLabel_4 = new JLabel();
		add(lblNewLabel_4);

		lblNewLabel_3 = new JLabel();
		add(lblNewLabel_3);

		lblNewLabel_5 = new JLabel();
		add(lblNewLabel_5);
	}
	
	
	
	public void setQ1Color(Color color) {
		lblNewLabel.setBackground(color);
	}
}
