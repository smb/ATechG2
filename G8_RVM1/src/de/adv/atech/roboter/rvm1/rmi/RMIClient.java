package de.adv.atech.roboter.rvm1.rmi;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.GenericController;
import de.adv.atech.roboter.commons.commands.DefaultMove;
import de.adv.atech.roboter.commons.commands.DefaultNest;
import de.adv.atech.roboter.commons.commands.DefaultSleep;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.network.NetworkSlaveManager;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

public class RMIClient {

	public static void main(String[] args) {
		try {
			Client localClient = GenericController.getInstance()
					.getClientManager().getClient(Constant.CLIENT_SELF);

			localClient.getCommandManager().registerCommand(DefaultMove.class);

			localClient.getCommandManager().registerCommand(DefaultSleep.class);

			localClient.getCommandManager().registerCommand(DefaultNest.class);

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
