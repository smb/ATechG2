package de.adv.atech.roboter.gui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import de.adv.atech.roboter.gui.core.GUIController;

public class RootPanel extends JPanel {

	public RootPanel() {

		init();

		setupPanel();

	}

	public void init() {
		GUIController controller = GUIController.getInstance();

		controller.setCenterPanel(new CenterPanel());

		controller.setToolBar(new ToolBar());

		controller.setStatusPanel(new StatusPanel());

	}

	private void setupPanel() {

		GUIController controller = GUIController.getInstance();

		this.setLayout(new BorderLayout());

		this.add(controller.getCenterPanel(), BorderLayout.CENTER);

		this.add(controller.getToolBar(), BorderLayout.NORTH);

		this.add(controller.getStatusPanel(), BorderLayout.SOUTH);
	}
}
