package de.adv.atech.roboter.commons.network;

import java.rmi.registry.LocateRegistry;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.rmi.Discovery;
import de.adv.atech.roboter.commons.rmi.RMILookup;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

/**
 * Server heisst dass ein Master-Prozess gestartet wird, zum Beispiel die GUI
 * 
 * @author sbu
 * 
 */
public class NetworkMasterManager {

	/**
   * 
   */
	protected ServerInterface server;

	/**
   * 
   */
	public void initServer() throws Exception {
		Discovery.setProperties("RMIDiscovery.properties");

		int rmiPort = Discovery.getRMIRegistryPort();

		LocateRegistry.createRegistry(rmiPort);

		Discovery.setProperties("RMIDiscovery.properties");

		RMILookup.bind(this.server, Constant.RMI_SERVER_GUI, rmiPort);

		ControllerManager.debug("[Master]: Server gestartet");
	}

	/**
   * 
   */
	public NetworkMasterManager(ServerInterface server) {
		this.server = server;
	}

}
