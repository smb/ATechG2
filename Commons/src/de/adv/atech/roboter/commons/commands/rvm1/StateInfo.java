package de.adv.atech.roboter.commons.commands.rvm1;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.exceptions.CommandException;

public class StateInfo extends AbstractCommand {

	protected String Message;

	protected Double X;

	protected Double Y;

	protected Double Z;

	// more to come

	public enum Parameter {
		Message, X, Y, Z
	}

	public StateInfo() {
		super();
		init(this, Parameter.class);
		this.commandName = "StatusInfo";
	}

	@Override
	public String getCode(Object informationRef) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

}
