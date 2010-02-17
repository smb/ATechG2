package de.adv.atech.roboter.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;

import de.adv.atech.roboter.gui.components.AboutDialog;
import de.adv.atech.roboter.gui.core.GUIController;
import de.adv.atech.roboter.gui.media.Media;

public class AboutAction extends GUIAbstractAction {

	public static final String actionName = "About";

	public AboutAction() {
		super(actionName, Media.getIcon("information"));
	}

	public void execute(ActionEvent event) throws Exception {

		JDialog aboutDialog = new AboutDialog(GUIController.getInstance()
				.getMainFrame());
		aboutDialog.pack();
		aboutDialog.setVisible(true);

	}

}
