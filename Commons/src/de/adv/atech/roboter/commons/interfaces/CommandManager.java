package de.adv.atech.roboter.commons.interfaces;

import java.util.List;

import de.adv.atech.roboter.commons.exceptions.ClientException;
import de.adv.atech.roboter.commons.exceptions.CommandException;

public interface CommandManager {

	public List<Class<? extends Command>> getCommandList()
			throws CommandException, ClientException;

	public boolean isRegisteredCommand(Class<? extends Command> command)
			throws CommandException, ClientException;

	public boolean registerCommand(Class<? extends Command> commandClass)
			throws CommandException, ClientException;

	public Command resolveCommand(String command, boolean forceNewInstance)
			throws CommandException, ClientException;

	public boolean unregisterCommand(Class<? extends Command> commandClass)
			throws CommandException, ClientException;

	public boolean registerCommandList(
			List<Class<? extends Command>> commandClassList)
			throws CommandException, ClientException;

	public Command getCommandInstance(Class<? extends Command> commandClass,
			boolean forceNewInstance) throws CommandException, ClientException;

	public void registerCommandChangedListener(CommandChangedListener listener);

	public void fireCommandChangedEvent();
}
