package de.adv.atech.roboter.commons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import de.adv.atech.roboter.commons.interfaces.Command;

public class CommandManager {

	HashMap<Schlumpf, List<Command>> commands;

	public CommandManager() {
		this.commands = new HashMap<Schlumpf, List<Command>>();
	}

	public List<Command> getCommandList(Schlumpf schlumpf) {
		List<Command> tmpCommandList = null;

		if (this.commands.containsKey(schlumpf)) {
			tmpCommandList = this.commands.get(schlumpf);
		}

		if (tmpCommandList == null) {
			tmpCommandList = new Vector<Command>();
		}

		return tmpCommandList;
	}

	public boolean registerCommand(Schlumpf schlumpf, Command command) {

		return getCommandList(schlumpf).add(command);
	}

	public boolean unregisterCommand(Schlumpf schlumpf, Command command) {
		return getCommandList(schlumpf).remove(command);
	}

	public boolean isRegisteredCommand(Schlumpf schlumpf, Command command) {
		return getCommandList(schlumpf).contains(command);
	}

	public Command resolveCommand(Schlumpf schlumpf, String command) {
		Command resolvedCommand = null;

		if (command != null) {

			List<Command> tmpCommandList = getCommandList(schlumpf);
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
