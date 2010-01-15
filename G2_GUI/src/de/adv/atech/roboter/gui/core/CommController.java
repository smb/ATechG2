package de.adv.atech.roboter.gui.core;

import java.util.Iterator;
import java.util.Map;

import de.adv.atech.roboter.commons.ClientManager;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.NetworkClient;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.network.NetworkMasterManager;
import de.adv.atech.roboter.gui.rmi.RMIServer;

public class CommController implements Runnable {

	int sleepTime = 10000;

	NetworkMasterManager nmm = null;

	ClientManager clientManager = null;

	public CommController() {

		this.clientManager = GUIController.getInstance().getClientManager();

		try {
			this.nmm = new NetworkMasterManager(new RMIServer());
			this.nmm.initServer();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		// Idle-Cycle
		while (true) {
			try {
				long currentTimestamp = System.currentTimeMillis();

				// Registrierte Clients holen
				Map<String, Client> clientMap = this.clientManager
						.getRegisteredClients();

				for (Iterator<String> it = clientMap.keySet().iterator(); it
						.hasNext();) {

					String clientIdentifier = it.next();
					NetworkClient client = this.clientManager
							.getNetworkClient(clientIdentifier);

					if (client != null) { // Network Client gefunden
						long clientTimestamp = 0;

						try {
							clientTimestamp = client.getRMI().ping();
						}
						catch (Exception ex) {

						}

						long diff = clientTimestamp - currentTimestamp;

						if (clientTimestamp != 0) {
							ControllerManager.debug(" Ping '"
									+ clientIdentifier + "': " + diff + "ms");
						}
						else {
							ControllerManager
									.debug("FEHLER!! Client '"
											+ clientIdentifier
											+ "' nicht mehr erreichbar - wird entfernt");

							this.clientManager.unregisterClient(client);
						}
					}

				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			try {
				ControllerManager.debug("Idle Cycle - sleeping "
						+ this.sleepTime + "ms");
				Thread.sleep(this.sleepTime);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
