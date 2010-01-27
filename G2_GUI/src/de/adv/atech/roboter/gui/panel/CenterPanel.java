package de.adv.atech.roboter.gui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import de.adv.atech.roboter.gui.core.GUIController;

public class CenterPanel extends JPanel {

	public CenterPanel() {

		init();

		setupPanel();

	}

	public void init() {
		GUIController controller = GUIController.getInstance();

		controller.setMainPanel(new MainPanel());

	}

	public void setupPanel() {
		GUIController controller = GUIController.getInstance();

		this.setLayout(new BorderLayout());

		this.add(controller.getMainPanel(), BorderLayout.CENTER);
	}

}
