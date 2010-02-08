package de.adv.atech.roboter.gui.panel;

import javax.swing.JButton;
import javax.swing.JToolBar;

import de.adv.atech.roboter.gui.ActionManager;
import de.adv.atech.roboter.gui.action.EndAction;
import de.adv.atech.roboter.gui.action.SendAction;
import de.adv.atech.roboter.gui.core.GUIController;

public class ToolBar extends JToolBar {

	private JButton exitButton;

	private JButton sendButton;

	private ActionManager actionManager;

	public ToolBar() {

		this.actionManager = GUIController.getInstance().getActionManager();

		init();

		setupPanel();
	}

	public void init() {
		this.actionManager.registerActionSmart(EndAction.class);

		this.actionManager.registerActionSmart(SendAction.class);

		this.exitButton = actionManager.getJButton(EndAction.class, null);

		this.sendButton = actionManager.getJButton(SendAction.class, null);
	}

	private void setupPanel() {
		this.add(this.exitButton);

		this.add(this.sendButton);
	}

}
