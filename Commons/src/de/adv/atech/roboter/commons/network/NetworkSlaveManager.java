/*
 * Created on 11.12.2009
 */
package de.adv.atech.roboter.commons.network;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.rmi.Discovery;
import de.adv.atech.roboter.commons.rmi.RMIDiscovery;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

/**
 * 
 * @author sbu
 * 
 */
public class NetworkSlaveManager {

	ServerInterface serverReg;

	ServerInterface clientReg;

	String clientName;

	/**
	 * 
	 * @throws Exception
	 */
	public boolean init() throws Exception {
		Discovery.setProperties("RMIDiscovery.properties");

		int rmiPort = Discovery.getRMIRegistryPort() + 1;
		
		// TODO objid in use
		try
		{
		    LocateRegistry.createRegistry(rmiPort);
		}
		catch(ExportException ee)
		{
		    ControllerManager.debug("RVM1 already registered for RMI");
		}
        Registry reg = LocateRegistry.getRegistry(rmiPort);

        reg.rebind(this.clientName, this.serverReg);  

		// RMILookup.bind(this.server, Constant.RMI_SERVER_GUI);

		ControllerManager.debug("[Slave] Server gestartet");

		this.clientReg = (ServerInterface) RMIDiscovery.lookup(
				ServerInterface.class, null);

		// Client Objekt erzeugen

		// am Server registrieren
		this.clientReg.initConnection(this.clientName, rmiPort);

		return true;
	}

	public ServerInterface getServer() {
		return this.serverReg;
	}

	public ServerInterface getClient() {
		return this.clientReg;
	}

	/**
	 * 
	 * @param server
	 * @param clientName
	 */
	public NetworkSlaveManager(ServerInterface server, String clientName) {
		this.serverReg = server;
		this.clientName = clientName;
	}
}
