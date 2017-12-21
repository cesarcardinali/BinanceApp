package ui.custom_items;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;


public class TrendPanel extends JPanel {

	private MonitorLabel c1;
	private MonitorLabel c2;
	private MonitorLabel c3;
	private MonitorLabel c4;
	private MonitorLabel c5;
	private MonitorLabel c6;
	private ArrayList<MonitorLabel> candles;


	public TrendPanel() {
		setBorder(null);
		setMaximumSize(new Dimension(32767, 15));
		setSize(new Dimension(0, 15));
		setPreferredSize(new Dimension(10, 15));
		setLayout(new GridLayout(1, 0, 2, 0));
		
		candles = new ArrayList<>();
	}
	
	
	public TrendPanel(String data[][]) {
		setLayout(new GridLayout(1, 0, 0, 0));
		
		candles = new ArrayList<>();
		for (int i=0; i < data.length; i++) {
			MonitorLabel m;
			if(data[i][0].equals("R")) {
				m = new MonitorLabel(data[i][1], new Color(220, 20, 20)); // Red
			} else {
				m = new MonitorLabel(data[i][1], new Color(20, 220, 20)); // Green
			}
			candles.add(m);
			add(m);
		}
		
		setMaximumSize(new Dimension(32767, 13));
		setSize(new Dimension(0, 13));
		setPreferredSize(new Dimension(10, 13));
	}
	
	
	public void setCandleColor(int candle, Color color) {
		candles.get(candle).setBackground(color);
	}
	
	public void setQ1Color(Color color) {
		c1.setBackground(color);
	}
	public void setQ2Color(Color color) {
		c2.setBackground(color);
	}
	public void setQ3Color(Color color) {
		c3.setBackground(color);
	}
	public void setQ4Color(Color color) {
		c4.setBackground(color);
	}
	public void setQ5Color(Color color) {
		c5.setBackground(color);
	}
	public void setQ6Color(Color color) {
		c6.setBackground(color);
	}
}
