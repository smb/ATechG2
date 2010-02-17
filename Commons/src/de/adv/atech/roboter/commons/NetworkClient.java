package de.adv.atech.roboter.commons;

import java.rmi.Naming;

import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.CommandManager;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

public class NetworkClient implements Client {

	protected String address;

	protected String identifier;

	protected int port;

	protected String RMIURL;

	protected CommandManager commandManager;

	protected ServerInterface clientRMI;

	public NetworkClient(String identifier, String address, int port) {
		this.identifier = identifier;
		this.address = address;
		this.port = port;

		createRMIURL();

		commandManager = new ClientCommandManager(this);
	}

	public void createRMIURL() {

		StringBuilder endpoint = new StringBuilder(40);
		endpoint.append("//");
		endpoint.append(this.address);
		endpoint.append(":");
		endpoint.append(this.port);
		endpoint.append("/");
		endpoint.append(this.identifier);

		this.RMIURL = endpoint.toString();
	}

	public String getRMIURL() {
		return this.RMIURL;
	}

	public String getInetAddress() {
		return this.address;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public CommandManager getCommandManager() {
		return this.commandManager;
	}

	protected void rmiLookup() throws Exception {
		ControllerManager.debug("URL: " + this.getRMIURL());
		this.clientRMI = (ServerInterface) Naming.lookup(this.getRMIURL());
	}

	public ServerInterface getRMI() throws Exception {
		if (clientRMI == null) {
			rmiLookup();
		}

		return this.clientRMI;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("[NetworkClient][");
		sb.append(this.identifier);
		sb.append("]: ");
		sb.append(this.RMIURL);
		return sb.toString();
	}
}