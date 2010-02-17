package de.adv.atech.roboter.gui.components;

import java.util.Iterator;
import java.util.Map;

import javax.swing.JLabel;

import de.adv.atech.roboter.commons.ClientManager;
import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.NetworkClient;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.gui.core.GUIController;
import de.adv.atech.roboter.gui.media.Media;

public class ClientStatusLabel extends JLabel implements Runnable {

	boolean shutdown;

	String currentLabel;

	public ClientStatusLabel() {
		currentLabel = "Kein Client ausgewaehlt";

		this.setIcon(Media.getIcon("stop"));

		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * 
	 */
	private void setClientLabel() {

		// Get selected Client
		ClientManager cm = GUIController.getInstance().getClientManager();

		StringBuffer connectedClients = new StringBuffer();

		if (cm != null) {
			Map<String, Client> clientMap = cm.getRegisteredClients();

			for (Iterator<String> it = clientMap.keySet().iterator(); it
					.hasNext();) {

				String clientIdentifier = it.next();
				NetworkClient client = cm.getNetworkClient(clientIdentifier);

				if (client != null) { // Network Client gefunden

					if (connectedClients.length() > 0) {
						connectedClients.append(" / ");
					}

					if (client == cm.getActiveClient()) {
						connectedClients.append("*" + clientIdentifier + "*");
					} else {
						connectedClients.append(clientIdentifier);
					}
				}
			}
		}
		if (connectedClients != null && connectedClients.length() > 0) {
			currentLabel = "Verbundene Clients: " + connectedClients.toString();
			this.setIcon(Media.getIcon("accept"));

		} else {
			currentLabel = "Keine Clients verbunden";
			this.setIcon(Media.getIcon("stop"));
		}

		this.setText(currentLabel);
	}

	public void run() {
		// Idle-Cycle
		while (!this.shutdown) {
			try {
				setClientLabel();

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {

				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				ControllerManager.message(Constant.MESSAGE_TYPE_INFO,
						"[DateTimeStatusLabel] Shutdown");
			}

		}
	}

	public void shutdown() {
		this.shutdown = true;

	}
}
