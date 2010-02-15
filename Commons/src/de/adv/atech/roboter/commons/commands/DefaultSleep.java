package de.adv.atech.roboter.commons.commands;

import de.adv.atech.roboter.commons.exceptions.CommandException;

/**
 * 
 * @author sbu
 * 
 */
public class DefaultSleep extends AbstractCommand {

	protected Long duration;

	public enum Parameter {
		duration
	}

	public DefaultSleep() {
		super();
		init(this, Parameter.class);
		this.commandName = "Sleep";
	}

	@Override
	public String getCode(Object informationRef) throws CommandException {
		return null;
	}
}
