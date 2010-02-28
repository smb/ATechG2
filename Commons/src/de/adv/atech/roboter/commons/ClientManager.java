package de.adv.atech.roboter.commons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.ClientChangedListener;

public class ClientManager {

	protected Map<String, Client> clientMap;

	protected Client activeClient;

	public ClientManager() {
		this.clientMap = new LinkedHashMap<String, Client>();
		this.listenerMap = new HashMap<ClientChangedListener, Object>();
	}

	private Map<ClientChangedListener, Object> listenerMap;

	public void registerClientChangedListener(ClientChangedListener listener,
			Object data) {
		this.listenerMap.put(listener, data);
	}

	public void fireClientChangedEvent() {
		for (Iterator<Entry<ClientChangedListener, Object>> it = this.listenerMap
				.entrySet().iterator(); it.hasNext();) {
			Entry<ClientChangedListener, Object> entry = it.next();
			entry.getKey().clientChanged(this, entry.getValue());
		}
	}

	public boolean registerClient(Client client) {
		ControllerManager.debug("[ClientManager] registered Client: "
				+ client.getIdentifier());

		this.clientMap.put(client.getIdentifier(), client);

		fireClientChangedEvent();

		return true;
	}

	public boolean unregisterClient(Client client) {
		if (client != null) {

			for (Iterator<Client> it = this.clientMap.values().iterator(); it
					.hasNext();) {
				if (it.next().equals(client)) {
					it.remove();
				}
			}

			// Client war aktiver Client - entfernen
			if (this.activeClient == client) {
				this.activeClient = null;
			}

			fireClientChangedEvent();
		}

		return true;
	}

	public boolean unregisterClient(String identifier) {
		// return this.unregisterClient(getClient(identifier));
		if (identifier != null) {
			Client client = this.clientMap.remove(identifier);

			if (this.activeClient == null || this.activeClient.equals(client)) {
				this.activeClient = null;
			}

			fireClientChangedEvent();
		}

		return true;
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

	public int getRegisteredNetworkClientCount() {
		int registeredClients = 0;

		for (Iterator<Client> it = this.clientMap.values().iterator(); it
				.hasNext();) {
			if (it.next() instanceof NetworkClient) {
				registeredClients++;
			}
		}

		return registeredClients;
	}

	public Client getClient(String identifier) {
		Client client = null;

		if (this.clientMap.containsKey(identifier)) {
			client = this.clientMap.get(identifier);
		}

		return client;
	}

	public void unregisterClients(List<String> removeClients) {
		for (Iterator<String> it = removeClients.iterator(); it.hasNext();) {
			this.unregisterClient(it.next());
		}
	}
}
