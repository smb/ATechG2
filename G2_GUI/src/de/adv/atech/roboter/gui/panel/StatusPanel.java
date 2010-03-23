package de.adv.atech.roboter.gui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import de.adv.atech.roboter.gui.core.GUIController;

public class StatusPanel extends JPanel {

	public StatusPanel() {

		init();

		setupPanel();

	}

	public void init() {
		GUIController controller = GUIController.getInstance();

		controller.setDebugArea(new DebugArea());

		controller.setStatusBar(new StatusBar());

	}

	public void setupPanel() {
		GUIController controller = GUIController.getInstance();

		this.setLayout(new BorderLayout());

		this.add(controller.getDebugArea(), BorderLayout.CENTER);

		this.add(controller.getStatusBar(), BorderLayout.SOUTH);
	}

}
