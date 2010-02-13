package de.adv.atech.roboter.gui.action;

import java.awt.event.ActionEvent;

import de.adv.atech.roboter.gui.core.EventDispatcher;
import de.adv.atech.roboter.gui.core.GUIController;
import de.adv.atech.roboter.gui.core.RobotGUIEvent;

public class SendAction extends GUIAbstractAction {

	public static final String actionName = "Send";

	public SendAction() {
		super(actionName, null);
	}

	public void execute(ActionEvent event) throws Exception {

		GUIController.getInstance().getEventDispatcher().fireActionEvent(
				new RobotGUIEvent(this, EventDispatcher.TYPE_ACTION, "Send"));

	}
}
