package de.adv.atech.roboter.commons.commands;

public class DefaultMove extends AbstractCommand {

	public Double X;

	public Double Y;

	public Double Z;

	public DefaultMove() {
		super();
		init(this);
		commandName = "Move";
	}
}
