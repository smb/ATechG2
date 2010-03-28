package de.adv.atech.roboter.commons.commands.rvm1;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.exceptions.CommandException;

public class ErrorInfo extends AbstractCommand {

	public String Message;
	public Integer Code;

	public enum Parameter {
		Message, Code
	}

	public ErrorInfo() {
		super();
		init(this, Parameter.class);
		this.commandName = "Error";
	}

	@Override
	public String getCode(Object informationRef) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

}
