package de.adv.atech.roboter.commons.interfaces;

import java.util.List;

import de.adv.atech.roboter.commons.exceptions.CommandException;

public interface CommandManager {

	public List<Class<? extends Command>> getCommandList() throws CommandException;

	public boolean isRegisteredCommand(Class<? extends Command> command)
			throws CommandException;

	public boolean registerCommand(Class<? extends Command> commandClass)
			throws CommandException;

	public Command resolveCommand(String command, boolean forceNewInstance)
			throws CommandException;

	public boolean unregisterCommand(Class<? extends Command> commandClass)
			throws CommandException;

	public boolean registerCommandList(List<Class<? extends Command>> commandClassList)
			throws CommandException;
}
