package de.adv.atech.roboter.commons;

public class Client {

	protected String address;

	protected String identifier;

	protected String RMIURL;

	public Client(String identifier, String address) {
		this.identifier = identifier;
		this.address = address;
	}

	public void createRMIURL() {

		StringBuilder endpoint = new StringBuilder(40);
		endpoint.append("//");
		endpoint.append(this.address);
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
}