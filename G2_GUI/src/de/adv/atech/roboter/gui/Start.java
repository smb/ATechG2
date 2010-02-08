package de.adv.atech.roboter.gui;

import de.adv.atech.roboter.gui.components.SplashScreenMain;
import de.adv.atech.roboter.gui.core.GUIController;

public class Start {

	public static void main(String[] args) {

		GUIController controller = GUIController.getInstance();

		SplashScreenMain ss = new SplashScreenMain("splashscreen.png");

		controller.initGUI();

		controller.init();
	}

}
