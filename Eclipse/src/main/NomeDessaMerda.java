package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;
import models.AppData;
import ui.MainWindow;


public class NomeDessaMerda extends JFrame {

	// Launch app ------------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					javax.swing.UIManager.setLookAndFeel( javax.swing.UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				
				MainWindow appWindow = new MainWindow();
				AppData appData = new AppData(appWindow);
				appWindow.start(appData);
			}
		});
	}
	// -----------------------------------------------------------------------------------------------------------------------------
}
