package de.adv.atech.roboter.gui.action;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;

import de.adv.atech.roboter.gui.components.AboutDialog;
import de.adv.atech.roboter.gui.core.GUIController;

public class AboutAction extends GUIAbstractAction {

	public static final String actionName = "About";

	public AboutAction() {
		super(actionName, null);
	}

	public void execute(ActionEvent event) throws Exception {
		JPanel options = new AboutDialog();

		JDialog aboutDialog = new JDialog(GUIController.getInstance()
				.getMainFrame(), "About");
		aboutDialog.getRootPane().setLayout(new BorderLayout());
		aboutDialog.getRootPane().add(options, BorderLayout.CENTER);

		aboutDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		aboutDialog.pack();

		aboutDialog.setVisible(true);

	}

}
