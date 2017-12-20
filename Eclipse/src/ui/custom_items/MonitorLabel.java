package ui.custom_items;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;


public class MonitorLabel extends JLabel {

	public MonitorLabel(String text) {
		if(text == null)
			text = "Unknown";
		
		setText(text);
		setFont(new Font("Consolas", Font.PLAIN, 11));
		setForeground(Color.DARK_GRAY);
		
		if(text.contains("Rising")) {
			setForeground(Color.green);
		} else if (text.contains("Drop")) {
			setForeground(Color.red);
		}
	}
}
