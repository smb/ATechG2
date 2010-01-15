/*
 * Created on 11.12.2009
 */
package de.adv.atech.roboter.gui.core;

import de.adv.atech.roboter.commons.ClientManager;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.LocalClient;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.Controller;
import de.adv.atech.roboter.gui.DebugPanel;

/**
 * 
 * @author sbu
 * 
 */
public class GUIController implements Controller {

	private ClientManager clientManager;

	private DebugPanel debugPanel;

	/**
   * 
   */
	private static GUIController _instance;

	GUIController() {

		ControllerManager.getInstance().setController(this);

		this.clientManager = new ClientManager();

		Client localClient = new LocalClient();

		this.clientManager.registerClient(localClient);
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

	/**
	 * @param debugPanel
	 *            the debugPanel to set
	 */
	public void setDebugPanel(DebugPanel debugPanel) {
		this.debugPanel = debugPanel;
	}

	/**
	 * @return the debugPanel
	 */
	public DebugPanel getDebugPanel() {
		return this.debugPanel;
	}

	public void debug(String text) {
		if (this.debugPanel != null) {
			this.debugPanel.addText("[GUIController]" + text);
		}
	}

}
