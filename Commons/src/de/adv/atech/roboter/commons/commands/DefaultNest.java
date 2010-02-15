package de.adv.atech.roboter.commons.commands;

import de.adv.atech.roboter.commons.exceptions.CommandException;

/**
 * 
 * @author sbu
 * 
 */
public class DefaultNest extends AbstractCommand {

	public enum Parameter {

	}

	/**
   * 
   */
	public DefaultNest() {
		super();
		init(this, Parameter.class);
		this.commandName = "Nest";
	}

	@Override
	public String getCode(Object informationRef) throws CommandException {
		return null;
	}
}
