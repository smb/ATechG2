package de.adv.atech.roboter.commons.commands;

import de.adv.atech.roboter.commons.exceptions.CommandException;

/**
 * 
 * @author sbu
 * 
 */
public class DefaultMove extends AbstractCommand {

	protected Double X;

	protected Double Y;

	protected Double Z;

	protected String KeinGueltigerParameter;

	/**
	 * 
	 * @author sbu
	 * 
	 *         Enum ohne "KeinGueltigerParameter" - liste der "gueltigen"
	 *         Parameter
	 */
	public enum Parameter {
		X, Y, Z
	}

	/**
   * 
   */
	public DefaultMove() {
		super();
		init(this, Parameter.class);
		this.commandName = "Move";
	}

	@Override
	public String getCode(Object informationRef) throws CommandException {
		return "OG";
	}
}
