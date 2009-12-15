package de.adv.atech.roboter.rvm1.rmi;

import java.net.InetAddress;

import de.adv.atech.roboter.commons.network.NetworkSlaveManager;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

public class RMIClient {

	public static void main(String[] args) {
		try {

			NetworkSlaveManager ncl = new NetworkSlaveManager(new RMIServer(),
					"RVM1");

			ncl.init();

			ServerInterface client = ncl.getClient();

			client.ping();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
