package de.adv.atech.roboter.commons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.adv.atech.roboter.commons.exceptions.ClientException;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.ClientChangedListener;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.interfaces.CommandChangedListener;
import de.adv.atech.roboter.commons.interfaces.CommandManager;

public class ActiveClientCommandManager implements CommandManager,
		ClientChangedListener, CommandChangedListener {

	public ActiveClientCommandManager(ClientManager clientManager) {
		this.listenerMap = new HashMap<CommandChangedListener, Client>();

		clientManager.registerClientChangedListener(this, null);
	}

	Map<CommandChangedListener, Client> listenerMap;

	Client activeClient = null;

	CommandManager activeClientCommandManager = null;

	@Override
	public void clientChanged(ClientManager clientManager, Object object) {
		// Ignorieren
	}

	@Override
	public void activeClientChanged(ClientManager clientManager, Object object) {
		try {
			this.activeClient = clientManager.getActiveClient();
			this.activeClientCommandManager = this.activeClient
					.getCommandManager();
			activeClientCommandManager.registerCommandChangedListener(this);
		}
		catch (ClientException e) {
			this.activeClient = null;
			this.activeClientCommandManager = null;
			fireCommandChangedEvent();
		}
	}

	@Override
	public void commandChanged(Client client) {
		this.fireCommandChangedEvent();
	}

	public CommandManager getActiveClientCommandManager()
			throws ClientException {
		if (this.activeClient == null
				|| this.activeClientCommandManager == null) {
			throw new ClientException("Kein aktiver Client gesetzt");
		}
		return this.activeClientCommandManager;
	}

	public ActiveClientCommandManager(Client client) {

	}

	public void registerCommandChangedListener(CommandChangedListener listener,
			Client client) {
		this.listenerMap.put(listener, client);
	}

	@Override
	public void fireCommandChangedEvent() {
		for (Iterator<Entry<CommandChangedListener, Client>> it = this.listenerMap
				.entrySet().iterator(); it.hasNext();) {
			Entry<CommandChangedListener, Client> entry = it.next();
			entry.getKey().commandChanged(entry.getValue());
		}
	}

	@Override
	public Command getCommandInstance(Class<? extends Command> commandClass,
			boolean forceNewInstance) throws CommandException, ClientException {
		return this.getActiveClientCommandManager().getCommandInstance(
				commandClass, forceNewInstance);
	}

	@Override
	public List<Class<? extends Command>> getCommandList()
			throws CommandException, ClientException {
		return this.getActiveClientCommandManager().getCommandList();
	}

	@Override
	public boolean isRegisteredCommand(Class<? extends Command> command)
			throws CommandException, ClientException {
		return this.getActiveClientCommandManager()
				.isRegisteredCommand(command);
	}

	@Override
	public boolean registerCommand(Class<? extends Command> commandClass)
			throws CommandException, ClientException {
		return this.getActiveClientCommandManager().registerCommand(
				commandClass);
	}

	@Override
	public void registerCommandChangedListener(CommandChangedListener listener) {
		this.registerCommandChangedListener(listener);
	}

	@Override
	public boolean registerCommandList(
			List<Class<? extends Command>> commandClassList)
			throws CommandException, ClientException {
		return this.getActiveClientCommandManager().registerCommandList(
				commandClassList);
	}

	@Override
	public Command resolveCommand(String command, boolean forceNewInstance)
			throws CommandException, ClientException {
		return this.getActiveClientCommandManager().resolveCommand(command,
				forceNewInstance);
	}

	@Override
	public boolean unregisterCommand(Class<? extends Command> commandClass)
			throws CommandException, ClientException {
		return this.getActiveClientCommandManager().unregisterCommand(
				commandClass);
	}

}
