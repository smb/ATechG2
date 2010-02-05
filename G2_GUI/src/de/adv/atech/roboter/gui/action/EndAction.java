package de.adv.atech.roboter.gui.action;

import java.awt.event.ActionEvent;

import de.adv.atech.roboter.commons.ControllerManager;

public class EndAction extends GUIAbstractAction {

	public EndAction() {
		super("Beenden", null);
	}

	public void execute(ActionEvent event) throws Exception {
		ControllerManager.getInstance().getController().shutdown();
	}

}
