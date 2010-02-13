package de.adv.atech.roboter.gui.core;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;

public class ActionHandler implements RobotListener {

	public void execute(RobotGUIEvent e) {
		ControllerManager.message(Constant.MESSAGE_TYPE_INFO,
				"Event gefeuert: " + e);
	}

}
