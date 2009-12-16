package de.adv.atech.roboter.commons;

import java.util.List;

import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.interfaces.CommandManager;

/**
 * Wrapper fuer CommandManager der eine Refernt auf den Client haelt
 * 
 * @author sb
 * 
 */
public class ClientCommandManager extends AbstractCommandManager implements
		CommandManager {

	/**
	 * 
	 */
	protected Client client;

	public ClientCommandManager(Client client) {
		this.client = client;
	}

	public boolean isRegisteredCommand(Class<? extends Command> command)
			throws CommandException {
		return super.isRegisteredCommand(this.client, command);
	}

	public boolean registerCommand(Class<? extends Command> commandClass)
			throws CommandException {
		return super.registerCommand(this.client, commandClass);
	}

	public Command resolveCommand(String command, boolean forceNewInstance)
			throws CommandException {
		return super.resolveCommand(this.client, command, forceNewInstance);
	}

	public boolean unregisterCommand(Class<? extends Command> commandClass)
			throws CommandException {
		return super.unregisterCommand(this.client, commandClass);
	}

	public boolean registerCommandList(List<Class<? extends Command>> commandClassList)
			throws CommandException {
		return super.registerCommandList(this.client, commandClassList);
	}

	public List<Class<? extends Command>> getCommandList() throws CommandException {
		return this.getCommandList(this.client);
	}

}
