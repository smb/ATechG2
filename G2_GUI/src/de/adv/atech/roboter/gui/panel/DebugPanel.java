package de.adv.atech.roboter.gui.panel;

import javax.swing.JScrollPane;

import de.adv.atech.roboter.gui.core.GUIController;

public class DebugPanel extends JScrollPane {

	public DebugPanel() {

		init();

	}

	public void init() {

		GUIController controller = GUIController.getInstance();

	}

	public void setupPanel() {

		GUIController controller = GUIController.getInstance();

		this.setViewportView(controller.getDebugArea());

	}

}
