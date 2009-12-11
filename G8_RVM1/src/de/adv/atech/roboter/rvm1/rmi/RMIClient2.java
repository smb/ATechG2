package de.adv.atech.roboter.rvm1.rmi;

import java.net.InetAddress;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import de.adv.atech.roboter.commons.commands.DefaultMove;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.network.NetworkSlaveManager;
import de.adv.atech.roboter.commons.rmi.Discovery;
import de.adv.atech.roboter.commons.rmi.RMIDiscovery;
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

      Command
      
      ServerInterface server = ncl.initServer();

    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
