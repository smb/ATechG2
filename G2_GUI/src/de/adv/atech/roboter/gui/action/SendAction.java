package de.adv.atech.roboter.gui.action;

import java.awt.event.ActionEvent;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;

public class SendAction extends GUIAbstractAction {

	public SendAction() {
		super("Senden", null);
	}

	public void execute(ActionEvent event) throws Exception {
		ControllerManager.message(Constant.MESSAGE_TYPE_INFO,
				"Senden Knopf gedrueckt");
	}

}
