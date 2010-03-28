package de.adv.atech.roboter.gui.rmi;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.NetworkClient;
import de.adv.atech.roboter.commons.commands.rvm1.ErrorInfo;
import de.adv.atech.roboter.commons.commands.rvm1.StateInfo;
import de.adv.atech.roboter.commons.exceptions.ClientException;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Client;
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

		ControllerManager.message(Constant.MESSAGE_TYPE_INFO,
				"[GUI] Empfangene Daten: ");

		for (Iterator<Command> it = commandList.iterator(); it.hasNext();) {
			Command tmpCommand = it.next();

			try {
				if (tmpCommand instanceof ErrorInfo) {
					ControllerManager.message(Constant.MESSAGE_TYPE_POPUP,
							"Fehler", (String) tmpCommand
									.getParameter(ErrorInfo.Parameter.Message),
							"");
				}
				if (tmpCommand instanceof StateInfo) {
					ControllerManager
							.message(
									Constant.MESSAGE_TYPE_INFO,
									(String) tmpCommand
											.getParameter(StateInfo.Parameter.Message));
				}
			}
			catch (CommandException ex) {
				ControllerManager.message(Constant.MESSAGE_TYPE_ERROR, ex
						.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param client
	 */
	public void initConnection(String clientIdentifier, int localPort)
			throws RemoteException {
		String clientAddress = null;

		try {
			clientAddress = this.getClientHost();
		}
		catch (ServerNotActiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Client Host: " + clientAddress);

		NetworkClient client = new NetworkClient(clientIdentifier,
				clientAddress, localPort);

		// Client registrieren
		GUIController.getInstance().getClientManager().registerClient(client);

		// Command Liste anfragen

		try {
			List<Class<? extends Command>> commandList = client.getRMI()
					.getCommandList();

			System.out.println("received list: " + commandList.toString());

			client.getCommandManager().registerCommandList(commandList);
		}
		catch (CommandException e) {
			throw new RemoteException("getCommandList failed", e);
		}
		catch (Exception e) {
			throw new RemoteException("RMILookup failed", e);
		}

	}

	public List<Class<? extends Command>> getCommandList()
			throws RemoteException {
		List<Class<? extends Command>> commandClassList = null;

		Client localClient = GUIController.getInstance().getClientManager()
				.getClient(Constant.CLIENT_SELF);

		try {
			commandClassList = localClient.getCommandManager().getCommandList();
		}
		catch (CommandException ex) {
			throw new RemoteException(ex.getMessage());
		}
		catch (ClientException e) {
			throw new RemoteException(e.getMessage());
		}

		return commandClassList;
	}

	/**
 * 
 */
	public long ping() throws RemoteException {
		System.out.println("** PING? PONG! **");
		return System.currentTimeMillis();
	}

}
