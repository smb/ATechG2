package de.adv.atech.roboter.gui.panel;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {

	JButton newButton;

	public ToolBar() {

		init();

		setupPanel();
	}

	public void init() {
		this.newButton = new JButton("Neu");
	}

	private void setupPanel() {
		this.add(this.newButton);
	}

}
