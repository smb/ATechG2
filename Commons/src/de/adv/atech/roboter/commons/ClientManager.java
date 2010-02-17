package de.adv.atech.roboter.commons;

import java.util.LinkedHashMap;
import java.util.Map;

import de.adv.atech.roboter.commons.interfaces.Client;

public class ClientManager {

	protected Map<String, Client> clientMap;

	protected Client activeClient;

	public ClientManager() {
		this.clientMap = new LinkedHashMap<String, Client>();

	}

	public boolean registerClient(Client client) {
		ControllerManager.debug("[ClientManager] registered Client: "
				+ client.getIdentifier());

		this.clientMap.put(client.getIdentifier(), client);

		return true;
	}

	public boolean unregisterClient(Client client) {
		if (client != null) {

			this.clientMap.remove(client);

			// Client war aktiver Client - entfernen
			if (this.activeClient == client) {
				this.activeClient = null;
			}
		}

		return true;
	}

	public boolean unregisterClient(String identifier) {
		return this.unregisterClient(getClient(identifier));
	}

	public Map<String, Client> getRegisteredClients() {
		return this.clientMap;
	}

	public boolean isRegisteredClient(Client client) {
		return this.clientMap.containsValue(client);
	}

	public boolean setActiveClient(String identifier) {
		boolean returnValue = false;

		if (identifier != null) {
			if (this.clientMap.containsKey(identifier)) {
				this.activeClient = this.clientMap.get(identifier);
				returnValue = true;

				ControllerManager.message(Constant.MESSAGE_TYPE_INFO,
						"Aktiver Client gesetzt: " + this.activeClient);
			}

			/*
			 * for (Iterator<Client> it = this.clientMap.iterator();
			 * it.hasNext();) { Client tmpClient = it.next(); String
			 * tmpIdentifier = tmpClient.getIdentifier(); if (tmpIdentifier !=
			 * null) { if (tmpIdentifier.equals(identifier)) { resolvedClient =
			 * tmpClient; returnValue = true; } } else { returnValue = false; }
			 * }
			 */
		} else {
		}

		return returnValue;
	}

	public void setActiveClient(Client client) {
		this.activeClient = client;
	}

	public Client getActiveClient() {
		return this.activeClient;
	}

	public NetworkClient getNetworkClient(String identifier) {
		NetworkClient networkClient = null;

		Client client = getClient(identifier);

		if (client instanceof NetworkClient) {
			networkClient = (NetworkClient) client;
		}

		return networkClient;
	}

	public Client getClient(String identifier) {
		Client client = null;

		if (this.clientMap.containsKey(identifier)) {
			client = this.clientMap.get(identifier);
		}

		return client;
	}
}
