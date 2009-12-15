package de.adv.atech.roboter.rvm1.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;

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
	public void initConnection(String clientIdentifier) throws RemoteException {
		// Handshake

	}

	/**
 * 
 */
	public void ping() throws RemoteException {
		System.out.println("** PING? PONG! **");
	}

}
