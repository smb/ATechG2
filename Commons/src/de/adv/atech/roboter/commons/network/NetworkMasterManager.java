package de.adv.atech.roboter.commons.network;

import java.rmi.registry.LocateRegistry;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.rmi.Discovery;
import de.adv.atech.roboter.commons.rmi.RMILookup;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

/**
 * Server heisst dass ein Master-Prozess gestartet wird, zum Beispiel die GUI
 * 
 * @author sbu
 * 
 */
public class NetworkMasterManager
{
  /**
   * 
   */
  protected ServerInterface server;

  /**
   * 
   */
  public void initServer() throws Exception
  {
    LocateRegistry.createRegistry(10232);

    Discovery.setProperties("RMIDiscovery.properties");

    RMILookup.bind(this.server, Constant.RMI_SERVER_GUI);

    System.out.println("Server gestartet");
  }

  /**
   * 
   */
  public NetworkMasterManager(ServerInterface server)
  {
    this.server = server;
  }

}
