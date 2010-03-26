package de.adv.atech.roboter.rvm1.rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.GenericController;
import de.adv.atech.roboter.commons.commands.rvm1.MoveJoints;
import de.adv.atech.roboter.commons.commands.rvm1.MoveToCoordinates;
import de.adv.atech.roboter.commons.commands.rvm1.MoveToNest;
import de.adv.atech.roboter.commons.commands.rvm1.MoveToOrigin;
import de.adv.atech.roboter.commons.commands.rvm1.ResetRvm1;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.network.NetworkSlaveManager;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

public class RMIClient implements Runnable
{
    private ServerInterface GUI_client;
    private RMIServer rmiServer;
    
    public RMIClient(RMIServer rmiServer)
    {
        this.rmiServer = rmiServer;
        this.etablishConnection(false);
	}
    
    private void etablishConnection(boolean discardOld)
    {
        try
        {
            Client localClient = GenericController.getInstance()
                    .getClientManager().getClient(Constant.CLIENT_SELF);

            localClient.getCommandManager()
                    .registerCommand(MoveToCoordinates.class);

            localClient.getCommandManager()
                    .registerCommand(MoveToNest.class);
            
            localClient.getCommandManager()
                    .registerCommand(MoveToOrigin.class);
            
            localClient.getCommandManager()
                    .registerCommand(ResetRvm1.class);
            
            localClient.getCommandManager()
                    .registerCommand(MoveJoints.class);
            
            NetworkSlaveManager ncl = 
                    new NetworkSlaveManager(this.rmiServer,
                            this.rmiServer.getName());
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
            List<Command> commandList = new ArrayList<Command>();
            commandList.add(command);
            this.GUI_client.processCommand(commandList);
        }
        catch( RemoteException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(7500);
                this.GUI_client.ping();
            }
            catch(Exception ex)
            {
                System.out.print("[RV-M1 RMIClient] Problem pinging the GUI client. ");
                System.out.println("Will re-etablish the connection.");
                this.etablishConnection(true);
            }
        }
    }

}