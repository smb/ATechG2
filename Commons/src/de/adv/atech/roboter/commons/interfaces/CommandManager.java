package de.adv.atech.roboter.commons.interfaces;

import java.util.List;

import de.adv.atech.roboter.commons.exceptions.CommandException;

public interface CommandManager {

	public boolean isRegisteredCommand(Class<Command> command)
			throws CommandException;

	public boolean registerCommand(Class<Command> commandClass)
			throws CommandException;

	public Command resolveCommand(String command, boolean forceNewInstance)
			throws CommandException;

	public boolean unregisterCommand(Class<Command> commandClass)
			throws CommandException;

	public boolean registerCommandList(List<Class<Command>> commandClassList)
			throws CommandException;
}
