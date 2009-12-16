package de.adv.atech.roboter.commons;

import de.adv.atech.roboter.commons.interfaces.Client;

/**
 * Beispielcode - bitte in eigenen Controller uebernehmen
 * 
 * @author sb
 * 
 */
public class GenericController {

	private ClientManager clientManager;

	/**
   * 
   */
	private static GenericController _instance;

	protected GenericController() {
		this.clientManager = new ClientManager();

		Client localClient = new LocalClient();

		this.clientManager.registerClient(localClient);
	}

	/**
	 * 
	 * @return
	 */
	public static GenericController getInstance() {
		if (GenericController._instance == null) {
			GenericController._instance = new GenericController();
		}

		return GenericController._instance;
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
