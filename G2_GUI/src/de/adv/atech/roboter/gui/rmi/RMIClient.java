package de.adv.atech.roboter.gui.rmi;

import java.net.InetAddress;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import de.adv.atech.roboter.commons.commands.DefaultMove;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.rmi.Discovery;
import de.adv.atech.roboter.commons.rmi.RMIDiscovery;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

public class RMIClient {

	public static void main(String[] args) {
		try {

			String client = "Client: "
					+ InetAddress.getLocalHost().getHostAddress().toString();

			System.out.println(client);

			// String url = "//10.2.13.62/" + Constant.RMI_SERVER_GUI;
			//
			// ServerInterface server = (ServerInterface) Naming.lookup(url);

			Properties tmpProps = new Properties();

			tmpProps.put("jip.rmi.multicast.address", "228.5.6.7");
			tmpProps.put("jip.rmi.multicast.port", "6789");
			tmpProps.put("jip.rmi.unicast.port", new Integer(
					5000).toString());
			tmpProps.put("jip.rmi.unicast.portRange", "20");
			tmpProps.put("jip.rmi.protocol.header", "ROBOTHEAD");
			tmpProps.put("jip.rmi.protocol.delim", "|");

			Discovery.setProperties(tmpProps);

			ServerInterface server = (ServerInterface) RMIDiscovery.lookup(
					ServerInterface.class, null);

			List commandList = new Vector<Command>();

			Command move1 = new DefaultMove();
			Command move2 = new DefaultMove();

			move1.setParameter(DefaultMove.Parameter.X, 1.5);
			move1.setParameter(DefaultMove.Parameter.Y, 5.5);
			move1.setParameter(DefaultMove.Parameter.Z, 10.5);

			commandList.add(move1);
			commandList.add(move2);

			server.processCommand(commandList);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
