package de.adv.atech.roboter.commons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import de.adv.atech.roboter.commons.interfaces.Command;

public class CommandManager {

	HashMap<Client, List<Command>> commands;

	public CommandManager() {
		this.commands = new HashMap<Client, List<Command>>();
	}

	public List<Command> getCommandList(Client client) {
		List<Command> tmpCommandList = null;

		if (this.commands.containsKey(client)) {
			tmpCommandList = this.commands.get(client);
		}

		if (tmpCommandList == null) {
			tmpCommandList = new Vector<Command>();
		}

		return tmpCommandList;
	}

	public boolean registerCommand(Client client, Command command) {

		return getCommandList(client).add(command);
	}

	public boolean unregisterCommand(Client client, Command command) {
		return getCommandList(client).remove(command);
	}

	public boolean isRegisteredCommand(Client client, Command command) {
		return getCommandList(client).contains(command);
	}

	public Command resolveCommand(Client client, String command) {
		Command resolvedCommand = null;

		if (command != null) {

			List<Command> tmpCommandList = getCommandList(client);
			for (Iterator<Command> it = tmpCommandList.iterator(); it.hasNext();) {
				Command tmpCommand = it.next();
				if (command.equalsIgnoreCase(tmpCommand.getCommandName())) {
					resolvedCommand = tmpCommand;
					break;
				}
			}
		}

		return resolvedCommand;
	}
}
