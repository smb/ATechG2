package de.adv.atech.roboter.gui.rmi;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;

import de.adv.atech.roboter.commons.Client;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.rmi.ServerInterface;
import de.adv.atech.roboter.gui.core.GUIController;

/**
 * 
 * @author sbu
 * 
 */
public class RMIServer extends UnicastRemoteObject implements ServerInterface {

	public RMIServer() throws RemoteException {
		super();
	}

	/**
   * 
   */
	public void processCommand(List<Command> commandList)
			throws RemoteException {

		System.out.println("[GUI] Empfangene Daten: ");

		for (Iterator<Command> it = commandList.iterator(); it.hasNext();) {
			Command tmpCommand = it.next();

			System.out.println(tmpCommand.toString());
		}
	}

	/**
	 * 
	 * @param client
	 */
	public void initConnection(String clientIdentifier) throws RemoteException {
		String clientAddress = null;

		try {
			clientAddress = this.getClientHost();
		} catch (ServerNotActiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Client client = new Client(clientIdentifier, clientAddress);

		// Handshake
		GUIController.getInstance().getClientManager().registerClient(client);
	}

	/**
 * 
 */
	public void ping() throws RemoteException {
		System.out.println("** PING? PONG! **");
	}

}
