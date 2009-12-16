package de.adv.atech.roboter.commons;

/**
 * Beispielcode - bitte in eigenen Controller uebernehmen
 * 
 * @author sb
 * 
 */
public class Controller {

	private ClientManager clientManager;

	/**
   * 
   */
	private static Controller _instance;

	protected Controller() {
		this.clientManager = new ClientManager();
	}

	/**
	 * 
	 * @return
	 */
	public static Controller getInstance() {
		if (Controller._instance == null) {
			Controller._instance = new Controller();
		}

		return Controller._instance;
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
