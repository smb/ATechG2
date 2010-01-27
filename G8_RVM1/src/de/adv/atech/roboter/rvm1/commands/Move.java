package de.adv.atech.roboter.rvm1.commands;

import de.adv.atech.roboter.commons.commands.DefaultMove;

public class Move extends DefaultMove {

	public int schlumpf;

	public Move() {
		super();
//		init(this);
		commandName = "Move";
	}
}
