package de.adv.atech.roboter.gui.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.adv.atech.roboter.commons.ClientManager;
import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.IncidentInfo;
import de.adv.atech.roboter.commons.NetworkClient;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.network.NetworkMasterManager;
import de.adv.atech.roboter.gui.components.ExceptionDialog;
import de.adv.atech.roboter.gui.rmi.RMIServer;

public class CommController implements Runnable {

	int sleepTime = 10000;

	NetworkMasterManager nmm = null;

	boolean shutdown;

	ClientManager clientManager = null;

	public CommController() throws Exception {

		this.clientManager = GUIController.getInstance().getClientManager();

		this.shutdown = false;

		try {
			this.nmm = new NetworkMasterManager(new RMIServer());
			this.nmm.initServer();
		}
		catch (Exception e) {

			throw new Exception("Fehler beim Initialisieren des Servers: "
					+ e.getMessage(), e);
		}
	}

	public void run() {
		// Idle-Cycle
		while (!this.shutdown) {
			try {
				long currentTimestamp = System.currentTimeMillis();

				// Registrierte Clients holen
				Map<String, Client> clientMap = this.clientManager
						.getRegisteredClients();

				boolean clientActive = false;

				List<String> removeClients = new ArrayList<String>();

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
							clientActive = true;
							ControllerManager.debug(" Ping '"
									+ clientIdentifier + "': " + diff + "ms");
						}
						else {
							clientActive = false;

							ControllerManager
									.debug("FEHLER!! Client '"
											+ clientIdentifier
											+ "' nicht mehr erreichbar - wird entfernt");

							// this.clientManager.unregisterClient(client);
							removeClients.add(clientIdentifier);
							// it.remove();
						}
					}
					else {
						clientActive = true;
					}
				}

				if (removeClients.size() > 0) {
					this.clientManager.unregisterClients(removeClients);
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}
			try {
				/*
				 * ControllerManager.debug("Idle Cycle - sleeping " +
				 * this.sleepTime + "ms");
				 */
				Thread.sleep(this.sleepTime);
			}
			catch (InterruptedException e) {
				ControllerManager.message(Constant.MESSAGE_TYPE_INFO,
						"[CommController] Shutdown");
			}

		}
	}

	public void shutdown() {
		this.shutdown = true;

	}
}
