package ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;


public class TrailingPane extends JFrame {

	private JPanel contentPane;
	private JTextPane txtStatus;
	

	/**
	 * Create the frame.
	 */
	public TrailingPane() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 504, 248);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		txtStatus = new JTextPane();
		contentPane.add(txtStatus, BorderLayout.CENTER);
	}
	
	
	public void setStatus(String txt) {
		txtStatus.setText(txt);
	}

}
