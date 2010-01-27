package de.adv.atech.roboter.gui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.gui.core.GUIController;

public class MainPanel extends JTabbedPane {

	public MainPanel() {

		init();

		setupPanel();

	}

	public void init() {
		GUIController controller = GUIController.getInstance();

		controller.setEditorPanel(new EditorPanel());

		controller.setControlPanel(new ControlPanel());

	}

	
	public void setupPanel() {
		GUIController controller = GUIController.getInstance();
		
		this.addTab(Constant.TEXT_EDITOR_TAB, controller.getEditorPanel());
		
		this.addTab(Constant.TEXT_CONTROL_TAB, controller.getControlPanel());
	}
}
