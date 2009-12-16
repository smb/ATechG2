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

/**
 * 
 * @author sbu
 * 
 */
public class RMIServer extends UnicastRemoteObject implements ServerInterface {

	RMIServer() throws RemoteException {
		super();
	}

	RMIServer(int port) throws RemoteException {
		super(port);
	}

	/**
   * 
   */
	public void processCommand(List<Command> commandList)
			throws RemoteException {

		System.out.println("[RVM1] Empfangene Daten: ");

		for (Iterator<Command> it = commandList.iterator(); it.hasNext();) {
			Command tmpCommand = it.next();

			System.out.println(tmpCommand.toString());
		}
	}

	/**
	 * 
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
		} catch (CommandException ex) {
			throw new RemoteException();
		}

		return commandClassList;
	}

	/**
 * 
 */
	public void ping() throws RemoteException {
		System.out.println("** PING? PONG! **");
	}

}
