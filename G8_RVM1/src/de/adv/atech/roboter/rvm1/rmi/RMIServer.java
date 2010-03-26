package de.adv.atech.roboter.rvm1.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.GenericController;
import de.adv.atech.roboter.commons.exceptions.ClientException;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.rmi.ServerInterface;
import de.adv.atech.roboter.rvm1.controller.TranslatorToSerial;


public class RMIServer extends UnicastRemoteObject
                       implements ServerInterface
{

    private TranslatorToSerial serialTranslator;

    
	public RMIServer() throws RemoteException
	{
        // TODO logging
	    
	    super();
	}

	
	public RMIServer(int port)
                     throws RemoteException
    {
        // TODO logging
	    
		super(port);
	}

	
	public void setTranslator(TranslatorToSerial serialTrans)
	{
	    this.serialTranslator = serialTrans;
	}

	
	public void processCommand(List<Command> commandList)
			                   throws RemoteException
    {
        ControllerManager.message(0, "Recieve command list.");
        System.out.println("RMIServer: Its alive!");

		for( Iterator<Command> it = commandList.iterator(); it.hasNext(); )
		{
			Command tmpCommand = it.next();
			
			this.serialTranslator.processCommand(tmpCommand);
		}
	}

	
	public void initConnection(String clientIdentifier, int localPort)
			throws RemoteException
	{
		// TODO Handshake
	    System.out.println("Shaking hands. (has no effect)");
	}

	
	public List<Class<? extends Command>> getCommandList()
			throws RemoteException
	{
		List<Class<? extends Command>> commandClassList = null;

		Client localClient = GenericController.getInstance()
		        .getClientManager().getClient(Constant.CLIENT_SELF);

		try {
			commandClassList = localClient.getCommandManager().getCommandList();
		}
		catch (CommandException ex) {
			throw new RemoteException();
		}
		catch (ClientException e) {
			throw new RemoteException();
		}

		return commandClassList;
	}

	
	public long ping() throws RemoteException
	{
	    System.out.println("got pinged");
		return System.currentTimeMillis();
	}

	
    public String getName()
    {
        return "RV-M1";
    }
}
