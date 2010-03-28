package de.adv.atech.roboter.commons.commands.rvm1;

import de.adv.atech.roboter.commons.commands.AbstractCommand;
import de.adv.atech.roboter.commons.exceptions.CommandException;

public class StateInfo extends AbstractCommand {

    public String Message;

	public Double X;

	public Double Y;

	public Double Z;
	
	public Integer Tilt;
	
	public Integer Pitch;
	
	public Boolean Open;

	public enum Parameter {
		Message, X, Y, Z, Tilt, Pitch, Open
	}

	public StateInfo() {
		super();
		init(this, Parameter.class);
		this.commandName = "Status";
	}

	@Override
	public String getCode(Object informationRef) throws CommandException {
		// TODO Auto-generated method stub
		return null;
	}

}
