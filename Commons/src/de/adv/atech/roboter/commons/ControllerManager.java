package de.adv.atech.roboter.commons;

import de.adv.atech.roboter.commons.interfaces.Controller;

public class ControllerManager {

	private Controller controller;

	private static ControllerManager _instance;

	private ControllerManager() {
	}

	/**
	 * 
	 * @return
	 */
	public static ControllerManager getInstance() {
		if (ControllerManager._instance == null) {
			ControllerManager._instance = new ControllerManager();
		}

		return ControllerManager._instance;
	}

	public static void message(int messageType, String text) {
		if (getInstance().getController() != null) {
			ControllerManager.getInstance().getController().message(
					messageType, text);
		}
	}

	public static void debug(String text) {
		if (getInstance().getController() != null) {
			ControllerManager.getInstance().getController().debug(text);
		}
	}

	/**
	 * @param controller
	 *            the controller to set
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * @return the controller
	 */
	public Controller getController() {
		return this.controller;
	}

}
