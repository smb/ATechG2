package de.adv.atech.roboter.gui.rmi;

import java.rmi.Naming;
import java.util.List;
import java.util.Vector;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.commands.DefaultMove;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.rmi.ServerInterface;

public class RMIClient {

	public static void main(String[] args) {
		try {

			String url = "//10.2.13.62/" + Constant.RMI_SERVER_GUI;

			ServerInterface server = (ServerInterface) Naming.lookup(url);

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
