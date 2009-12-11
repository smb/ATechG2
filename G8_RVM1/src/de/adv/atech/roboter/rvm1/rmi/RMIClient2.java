package de.adv.atech.roboter.rvm1.rmi;

import java.net.InetAddress;

import de.adv.atech.roboter.commons.network.NetworkSlaveManager;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

public class RMIClient2
{

  public static void main(String[] args)
  {
    try
    {

      String client = "Client: "
          + InetAddress.getLocalHost().getHostAddress().toString();

      NetworkSlaveManager ncl = new NetworkSlaveManager(new RMIServer(), "RVM1");

      ServerInterface server = ncl.initServer();

      server.ping();

    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
