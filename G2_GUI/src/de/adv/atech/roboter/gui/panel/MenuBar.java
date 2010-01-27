package de.adv.atech.roboter.gui.panel;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {

	JMenu file;

	JMenuItem file_Exit;

	public MenuBar() {

		this.file = new JMenu("Datei");

		this.file_Exit = new JMenuItem("Beenden");

		this.file.add(this.file_Exit);

		this.add(this.file);
	}

	public void init() {

	}

}
