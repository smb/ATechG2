package de.adv.atech.roboter.commons;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class ClientManager {

	protected List<Client> clientList;

	protected Client activeClient;

	public ClientManager() {
		this.clientList = new Vector<Client>();
	}

	public boolean registerClient(Client client) {
		System.out.println("[ClientManager] registered Client: " + client);

		return this.clientList.add(client);
	}

	public boolean unregisterClient(Client client) {
		return this.clientList.remove(client);
	}

	public List<Client> getRegisteredClients() {
		return this.clientList;
	}

	public boolean isRegisteredClient(Client client) {
		return this.clientList.contains(client);
	}

	public boolean setActiveClient(String identifier) {
		Client resolvedClient = null;

		boolean returnValue = false;

		if (identifier != null) {
			for (Iterator<Client> it = this.clientList.iterator(); it.hasNext();) {
				Client tmpClient = it.next();
				String tmpIdentifier = tmpClient.getIdentifier();
				if (tmpIdentifier != null) {
					if (tmpIdentifier.equals(identifier)) {
						resolvedClient = tmpClient;
						returnValue = true;
					}
				} else {
					returnValue = false;
				}
			}
		} else {
			returnValue = false;
		}

		this.activeClient = resolvedClient;

		return returnValue;
	}

	public void setActiveClient(Client client) {
		this.activeClient = client;
	}

	public Client getActiveClient() {
		return this.activeClient;
	}
}
