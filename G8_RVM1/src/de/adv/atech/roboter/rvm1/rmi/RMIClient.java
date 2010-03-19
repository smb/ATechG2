package de.adv.atech.roboter.rvm1.rmi;

import java.rmi.RemoteException;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.GenericController;
import de.adv.atech.roboter.commons.commands.rvm1.*;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.network.NetworkSlaveManager;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

public class RMIClient
{
    private ServerInterface GUI_client;
    
    public RMIClient(RMIServer rmiServer)
    {
		try
		{
			Client localClient = GenericController.getInstance()
					.getClientManager().getClient(Constant.CLIENT_SELF);

			localClient.getCommandManager()
			        .registerCommand(MoveToCoordinates.class);

	         localClient.getCommandManager()
                    .registerCommand(MoveToNest.class);
			
			NetworkSlaveManager ncl = 
			        new NetworkSlaveManager(rmiServer, rmiServer.getName());

			ncl.init();

			this.GUI_client = ncl.getClient();

			this.GUI_client.ping();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

    
    public void sendCommand(Command command)
    {
        
        try
        {
            // TODO command to commandlist
            this.GUI_client.processCommand(null);
        }
        catch( RemoteException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}