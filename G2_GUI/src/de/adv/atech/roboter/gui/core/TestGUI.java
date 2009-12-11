/*
 * Created on 11.12.2009
 */
package de.adv.atech.roboter.gui.core;

import de.adv.atech.roboter.commons.network.NetworkMasterManager;
import de.adv.atech.roboter.gui.rmi.RMIServer;

public class TestGUI
{

  public static void main(String[] args)
  {
    try
    {
      NetworkMasterManager nmm = new NetworkMasterManager(new RMIServer());
      nmm.initServer();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
