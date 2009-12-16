package de.adv.atech.roboter.commons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;

public abstract class AbstractCommandManager {

	HashMap<Client, List<Class<Command>>> DPR_commandList;

	Map<Client, HashMap<String, Class<Command>>> commandMap;

	Map<Client, HashMap<Class<Command>, Command>> commandCacheMap;

	protected AbstractCommandManager() {
		this.DPR_commandList = new HashMap<Client, List<Class<Command>>>();
		this.commandMap = new HashMap<Client, HashMap<String, Class<Command>>>();
		this.commandCacheMap = new HashMap<Client, HashMap<Class<Command>, Command>>();
	}

	/**
	 * @deprecated Nicht implementert -> Maps
	 * @param client
	 * @return
	 */
	protected List<Class<Command>> getCommandList(Client client) {
		List<Class<Command>> tmpCommandList = null;

		if (this.DPR_commandList.containsKey(client)) {
			tmpCommandList = this.DPR_commandList.get(client);
		}

		if (tmpCommandList == null) {
			tmpCommandList = new Vector<Class<Command>>();
		}

		return tmpCommandList;
	}

	protected Map<String, Class<Command>> getCommandMap(Client client) {
		HashMap<String, Class<Command>> tmpCommandMap = null;

		if (this.commandMap.containsKey(client)) {
			tmpCommandMap = this.commandMap.get(client);
		}

		if (tmpCommandMap == null) {
			tmpCommandMap = new HashMap<String, Class<Command>>();
		}

		return tmpCommandMap;
	}

	protected Map<Class<Command>, Command> getCommandCacheMap(Client client) {
		HashMap<Class<Command>, Command> tmpCommandCacheMap = null;

		if (this.commandCacheMap.containsKey(client)) {
			tmpCommandCacheMap = this.commandCacheMap.get(client);
		}

		if (tmpCommandCacheMap == null) {
			tmpCommandCacheMap = new HashMap<Class<Command>, Command>();
		}

		return tmpCommandCacheMap;
	}

	public void cacheCommand(Client client, Class<Command> commandClass,
			Command command) {

		getCommandCacheMap(client).put(commandClass, command);
	}

	protected Command getCommandInstance(Client client,
			Class<Command> commandClass, boolean forceNewInstance)
			throws CommandException {
		Command command = null;
		boolean createNewInstance = forceNewInstance;

		if (!forceNewInstance) {
			if (getCommandCacheMap(client).containsKey(commandClass)) {
				command = getCommandCacheMap(client).get(commandClass);
				createNewInstance = false; // implizit - aber sicherer
			} else {
				createNewInstance = true;
			}
		}

		if (createNewInstance) {
			try {
				command = commandClass.newInstance();
			} catch (IllegalAccessException ex) {
				throw new CommandException(ex);
			} catch (InstantiationException ex) {
				throw new CommandException(ex);
			}
		} else {

		}

		return command;

	}

	protected boolean registerCommandList(Client client,
			List<Class<Command>> commandClassList) throws CommandException {

		boolean returnValue = true;

		for (Iterator<Class<Command>> it = commandClassList.iterator(); it
				.hasNext();) {
			returnValue = returnValue & registerCommand(client, it.next());
		}

		return returnValue;
	}

	protected boolean registerCommand(Client client, Class<Command> commandClass)
			throws CommandException {

		// return getCommandList(client).add(command);

		Command tmpCommand = getCommandInstance(client, commandClass, true);

		// Fuer Command-Cache benutzen
		cacheCommand(client, commandClass, tmpCommand);

		getCommandMap(client).put(tmpCommand.getCommandName(), commandClass);

		return true;
	}

	protected boolean unregisterCommand(Client client,
			Class<Command> commandClass) throws CommandException {
		// return getCommandList(client).remove(command);

		Command tmpCommand = getCommandInstance(client, commandClass, false);

		getCommandMap(client).remove(tmpCommand.getCommandName());
		return true;
	}

	protected boolean isRegisteredCommand(Client client, Class<Command> command)
			throws CommandException {
		// return getCommandList(client).contains(command);

		return getCommandMap(client).containsValue(command);
	}

	/**
	 * Gibt eine Instanz des Command Objektes zurueck
	 * 
	 * @param client
	 * @param command
	 * @param forceNewInstance
	 *            ACHTUNG: false nur bei lesenden (haeufig) Operationen
	 *            benutzen, da immer die gleiche Objektinstanz (Referenz)
	 *            verwendet wird - bei true wird ein neues Objekt erzeugt
	 *            (langsamer)
	 * @return
	 * @throws CommandException
	 */
	protected Command resolveCommand(Client client, String command,
			boolean forceNewInstance) throws CommandException {
		Command resolvedCommand = null;

		if (command != null) {
			Class<Command> commandClass = getCommandMap(client).get(command);

			resolvedCommand = getCommandInstance(client, commandClass,
					forceNewInstance);

			/*
			 * List<Class<Command>> tmpCommandList = getCommandList(client); for
			 * (Iterator<Class<Command>> it = tmpCommandList.iterator(); it
			 * .hasNext();) { Class<Command> tmpClass = it.next(); // Temporaere
			 * Instanz erzeugen Command tmpCommand = tmpClass.newInstance(); if
			 * (command.equalsIgnoreCase(tmpCommand.getCommandName())) {
			 * resolvedCommand = tmpCommand; break; } }
			 */
		}

		return resolvedCommand;
	}

}
