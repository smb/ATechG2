/*
 * Created on 11.12.2009
 */
package de.adv.atech.roboter.commons.network;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import de.adv.atech.roboter.commons.rmi.Discovery;
import de.adv.atech.roboter.commons.rmi.RMIDiscovery;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

/**
 * 
 * @author sbu
 * 
 */
public class NetworkSlaveManager
{

  ServerInterface server;

  String clientName;

  int rmiPort = 12321;

  /**
   * 
   * @throws Exception
   */
  public ServerInterface initServer() throws Exception
  {
    // TODO: Client-Port irgendwo aufnehmen
    LocateRegistry.createRegistry(this.rmiPort);

    Discovery.setProperties("RMIDiscovery.properties");

    Naming.rebind(this.clientName, this.server);

    // RMILookup.bind(this.server, Constant.RMI_SERVER_GUI);

    System.out.println("(Slave-)Server gestartet");

    // Verbindung herstellen zum Master

    return (ServerInterface) RMIDiscovery.lookup(ServerInterface.class, null);
  }

  /**
   * 
   * @param server
   * @param clientName
   */
  public NetworkSlaveManager(ServerInterface server, String clientName)
  {
    this.server = server;
    this.clientName = clientName;
  }
}
