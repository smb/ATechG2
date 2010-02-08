package de.adv.atech.roboter.gui.panel;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.adv.atech.roboter.gui.ActionManager;
import de.adv.atech.roboter.gui.action.AboutAction;
import de.adv.atech.roboter.gui.action.EndAction;
import de.adv.atech.roboter.gui.core.GUIController;

public class MenuBar extends JMenuBar {

	JMenu file;

	JMenu help;

	JMenuItem file_Exit;

	JMenuItem help_About;

	ActionManager actionManager;

	public MenuBar() {

		this.file = new JMenu("Datei");

		this.help = new JMenu("Hilfe");

		actionManager = GUIController.getInstance().getActionManager();

		init();
	}

	public void init() {
		actionManager.registerActionSmart(EndAction.class);

		actionManager.registerActionSmart(AboutAction.class);

		this.file_Exit = actionManager.getJMenuItem(EndAction.class, null);

		this.help_About = actionManager.getJMenuItem(AboutAction.class, null);

		this.file.add(this.file_Exit);

		this.help.add(this.help_About);

		this.add(this.file);

		this.add(Box.createHorizontalGlue());

		this.add(this.help);
	}
}
