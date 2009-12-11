package de.adv.atech.roboter.commons;

import java.util.List;
import java.util.Vector;


public class ClientManager {

	List<Schlumpf> clientList;

	public ClientManager() {
		this.clientList = new Vector<Schlumpf>();
	}

	public boolean registerClient(Schlumpf schlumpf) {
		return this.clientList.add(schlumpf);
	}

	public boolean unregisterClient(Schlumpf schlumpf) {
		return this.clientList.remove(schlumpf);
	}

	public List<Schlumpf> getRegisteredClients() {
		return this.clientList;
	}

	public boolean isRegisteredClient(Schlumpf schlumpf) {
		return this.clientList.contains(schlumpf);
	}

}
