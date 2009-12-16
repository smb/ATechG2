package de.adv.atech.roboter.commons;

import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.CommandManager;

public class LocalClient implements Client {

	protected CommandManager commandManager;

	public LocalClient() {
		this.commandManager = new ClientCommandManager(this);
	}

	public String getIdentifier() {
		return Constant.CLIENT_SELF;
	}

	public CommandManager getCommandManager() {
		return this.commandManager;
	}
}
