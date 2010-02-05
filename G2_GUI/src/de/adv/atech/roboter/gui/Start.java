package de.adv.atech.roboter.gui;

import de.adv.atech.roboter.gui.core.GUIController;

public class Start {

	public static void main(String[] args) {

		GUIController controller = GUIController.getInstance();

		controller.initGUI();

		controller.init();
	}

}
