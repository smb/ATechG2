/*
 * Created on 11.12.2009
 */
package de.adv.atech.roboter.gui.core;

import de.adv.atech.roboter.commons.ClientManager;

/**
 * 
 * @author sbu
 * 
 */
public class GUIController {
	private ClientManager clientManager;

	/**
   * 
   */
	private static GUIController _instance;

	GUIController() {
		this.clientManager = new ClientManager();
	}

	/**
	 * 
	 * @return
	 */
	public static GUIController getInstance() {
		if (GUIController._instance == null) {
			GUIController._instance = new GUIController();
		}

		return GUIController._instance;
	}

	/**
	 * @param clientManager
	 *            the clientManager to set
	 */
	public void setClientManager(ClientManager clientManager) {
		this.clientManager = clientManager;
	}

	/**
	 * @return the clientManager
	 */
	public ClientManager getClientManager() {
		return this.clientManager;
	}

}
