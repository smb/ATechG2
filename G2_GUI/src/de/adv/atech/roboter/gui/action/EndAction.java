package de.adv.atech.roboter.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractButton;

import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.gui.media.Media;

public class EndAction extends GUIAbstractAction {

	public static final String actionName = "Beenden";

	public EndAction() {
		super(actionName, Media.getIcon("cross"));
	}

	public void execute(ActionEvent event) throws Exception {
		ControllerManager.getInstance().getController().shutdown();
	}

	public void registerButtonListener(AbstractButton button) {
		//
	}

}
