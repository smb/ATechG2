package de.adv.atech.roboter.gui.panel;

import javax.swing.JButton;
import javax.swing.JToolBar;

import de.adv.atech.roboter.gui.action.EndAction;
import de.adv.atech.roboter.gui.action.GUIAbstractAction;
import de.adv.atech.roboter.gui.core.GUIController;

public class ToolBar extends JToolBar {

	JButton newButton;

	public ToolBar() {

		init();

		setupPanel();
	}

	public void init() {
		GUIController.getInstance().getActionManager().registerActionSmart(
				new EndAction());

		this.newButton = (JButton) GUIController.getInstance()
				.getActionManager().getButton("Beenden",
						GUIAbstractAction.TYPE_BUTTON, null);
	}

	private void setupPanel() {
		this.add(this.newButton);
	}

}
