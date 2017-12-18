package main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import models.AppData;
import ui.MainWindow;


public class NomeDessaMerda extends JFrame {

	// Launch app ------------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				MainWindow appWindow = new MainWindow();
				AppData appData = new AppData(appWindow);
				appWindow.start(appData);
			}
		});
	}
	// -----------------------------------------------------------------------------------------------------------------------------
}
