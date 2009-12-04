package de.adv.atech.roboter.gui.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {

	RMIServer() throws RemoteException {
		super();
	}

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		}

		catch (RemoteException ex) {
			System.out.println(ex.getMessage());
		}
		try {
			Naming.rebind(Constant.RMI_SERVER_GUI, new RMIServer());
		}
		catch (MalformedURLException ex) {
			System.out.println(ex.getMessage());
		}
		catch (RemoteException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void processCommand(List<Command> commandList)
			throws RemoteException {
		for (Iterator<Command> it = commandList.iterator(); it.hasNext();) {
			Command tmpCommand = it.next();

			System.out.println("Empfangene Daten: ");

			System.out.println(tmpCommand.toString());
		}
	}

}
