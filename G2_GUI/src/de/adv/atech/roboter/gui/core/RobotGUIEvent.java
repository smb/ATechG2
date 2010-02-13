package de.adv.atech.roboter.gui.core;

import java.awt.AWTEvent;

/**
 * 
 * @author sb
 * 
 */
public class RobotGUIEvent extends AWTEvent {

	protected String type;

	protected String actionCommand;

	static int eventId = 1;

	public RobotGUIEvent(Object source, String type, String actionCommand) {
		super(source, (eventId++) + AWTEvent.RESERVED_ID_MAX);

		this.type = type;
		this.actionCommand = actionCommand;
	}

	public String getType() {
		return this.type;
	}

	public String getActionCommand() {
		return this.actionCommand;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(200);

		sb.append("[RobotGUIEvent id=");
		sb.append(this.getID());
		sb.append("][type=");
		sb.append(this.getType());
		sb.append(",actionCommand=");
		sb.append(this.getActionCommand());
		sb.append(",source=");
		sb.append(this.getSource());

		return sb.toString();
	}

}
