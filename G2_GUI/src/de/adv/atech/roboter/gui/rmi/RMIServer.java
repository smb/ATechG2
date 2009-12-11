package de.adv.atech.roboter.gui.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.rmi.Discovery;
import de.adv.atech.roboter.commons.rmi.RMILookup;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

public class RMIServer extends UnicastRemoteObject implements ServerInterface {

	RMIServer() throws RemoteException {
		super();

		try {
			LocateRegistry.createRegistry(10232);
		}

		catch (RemoteException ex) {
			System.out.println("EX: " + ex.getMessage());
		}
		try {
			Properties tmpProps = new Properties();

			tmpProps.put("jip.rmi.multicast.address", "228.5.6.7");
			tmpProps.put("jip.rmi.multicast.port", "6789");
			tmpProps.put("jip.rmi.unicast.port", new Integer(5000).toString());
			tmpProps.put("jip.rmi.unicast.portRange", "20");
			tmpProps.put("jip.rmi.protocol.header", "ROBOTHEAD");
			tmpProps.put("jip.rmi.protocol.delim", "|");

			Discovery.setProperties(tmpProps);

			// Naming.rebind(Constant.RMI_SERVER_GUI, new RMIServer());

			RMILookup.bind(this, Constant.RMI_SERVER_GUI);

			System.out.println("Server gestartet");
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void main(String[] args) {
		try {
			new RMIServer();
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void processCommand(List<Command> commandList)
			throws RemoteException {

		System.out.println("Empfangene Daten: ");

		for (Iterator<Command> it = commandList.iterator(); it.hasNext();) {
			Command tmpCommand = it.next();

			System.out.println(tmpCommand.toString());
		}
	}

}
