package de.adv.atech.roboter.commons.exceptions;

import de.adv.atech.roboter.commons.interfaces.Command;

/**
 * 
 * @author sb
 * 
 */
public class CommandException extends AbstractException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	protected Command command;

	public CommandException() {

	}

	public CommandException(String arg0) {
		super(arg0);

	}

	public CommandException(Throwable arg0) {
		super(arg0);
	}

	public CommandException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CommandException(Throwable arg0, Command command) {
		super(arg0);

		this.command = command;
	}

	public Command getCommand() {
		return this.command;
	}
}
