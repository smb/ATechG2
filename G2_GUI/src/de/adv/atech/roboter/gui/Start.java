package de.adv.atech.roboter.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.adv.atech.roboter.gui.components.SplashScreenMain;
import de.adv.atech.roboter.gui.core.GUIController;

public class Start {

	public static void main(String[] args) {

		SplashScreenMain ss = new SplashScreenMain("splashscreen.png");

		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				}
				catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				catch (InstantiationException e) {
					e.printStackTrace();
				}
				catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				GUIController controller = GUIController.getInstance();

				controller.initGUI();

				controller.init();
			}
		});

	}
}
