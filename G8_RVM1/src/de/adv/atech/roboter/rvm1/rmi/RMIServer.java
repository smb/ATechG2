package de.adv.atech.roboter.rvm1.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.GenericController;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.rmi.ServerInterface;
import de.adv.atech.roboter.rvm1.controller.TranslatorToRmi;
import de.adv.atech.roboter.rvm1.controller.TranslatorToSerial;


public class RMIServer extends UnicastRemoteObject
                       implements ServerInterface
{

    private TranslatorToRmi rmiTranslator;
    private TranslatorToSerial serialTranslator;
    
	RMIServer(TranslatorToRmi sender,
	          TranslatorToSerial reciever)
              throws RemoteException
	{
        // TODO logging
	    
	    super();
	    this.rmiTranslator = sender;
	    this.serialTranslator = reciever;
	}

	RMIServer(TranslatorToRmi sender,
	          TranslatorToSerial reciever,
	          int port)
	          throws RemoteException
    {
        // TODO logging
	    
		super(port);
	    this.rmiTranslator = sender;
        this.serialTranslator = reciever;
	}

	public void processCommand(List<Command> commandList)
			                   throws RemoteException
    {
        // TODO logging 

		for( Iterator<Command> it = commandList.iterator(); it.hasNext(); )
		{
			Command tmpCommand = it.next();
			
			// TODO send to serialTranslator
		}
	}

	/**
	 * @param client
	 */
	public void initConnection(String clientIdentifier, int localPort)
			throws RemoteException {
		// Handshake

	}

	public List<Class<? extends Command>> getCommandList()
			throws RemoteException {
		List<Class<? extends Command>> commandClassList = null;

		Client localClient = GenericController.getInstance().getClientManager()
				.getClient(Constant.CLIENT_SELF);

		try {
			commandClassList = localClient.getCommandManager().getCommandList();
		}
		catch (CommandException ex) {
			throw new RemoteException();
		}

		return commandClassList;
	}

	public long ping() throws RemoteException
	{
	    // TODO logging
		return System.currentTimeMillis();
	}

}
