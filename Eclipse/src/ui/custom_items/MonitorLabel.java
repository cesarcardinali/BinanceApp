package ui.custom_items;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class MonitorLabel extends JLabel {

	
	public MonitorLabel() {
		setBorder(null);
		setText("");
		setFont(new Font("Consolas", Font.PLAIN, 11));
		setForeground(Color.DARK_GRAY);
		setMaximumSize(new Dimension(32767, 13));
		setSize(new Dimension(0, 13));
		setPreferredSize(new Dimension(10, 13));
	}
	
	public MonitorLabel(String text) {
		if(text == null)
			text = "Unknown";
		
		setText(text);
		setFont(new Font("Consolas", Font.PLAIN, 11));
		setForeground(Color.DARK_GRAY);
		setMaximumSize(new Dimension(32767, 13));
		setSize(new Dimension(0, 13));
		setPreferredSize(new Dimension(10, 13));
		
		if(text.contains("Rising")) {
			setForeground(Color.green);
		} else if (text.contains("Drop")) {
			setForeground(Color.red);
		}
	}
	
	public MonitorLabel(String text, Color color) {
		if(text == null)
			text = "";
		
		setVerticalAlignment(SwingConstants.TOP);
		setHorizontalAlignment(SwingConstants.CENTER);
		setText("<html><div style='text-align: center;'>" + text + "</div></html>");
		setMaximumSize(new Dimension(32767, 13));
		setSize(new Dimension(0, 13));
		setPreferredSize(new Dimension(10, 13));
		setFont(new Font("Consolas", Font.BOLD, 11));
		setOpaque(true);
		setForeground(Color.DARK_GRAY);
		setBackground(color);
	}
	
	public void setColor(Color color) {
		setForeground(color);
		//setBackground(color);
		
	}
}
