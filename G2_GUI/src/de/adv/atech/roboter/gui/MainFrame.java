package de.adv.atech.roboter.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.adv.atech.roboter.gui.components.SyntaxEditorContainer;
import de.adv.atech.roboter.gui.core.GUIController;
import de.adv.atech.roboter.gui.panel.MenuBar;
import de.adv.atech.roboter.gui.panel.RootPanel;

public class MainFrame extends JFrame {

	public MainFrame() {
		super("Roboter-Steuerung");

		SyntaxEditorContainer syntaxEditorPane = new SyntaxEditorContainer();

		this.setLayout(new BorderLayout());
		
		GUIController controller = GUIController.getInstance();

		controller.setRootPanel(new RootPanel());

		controller.setMenuBar(new MenuBar());

		this.setJMenuBar(controller.getMenuBar());

		this.add(controller.getRootPanel());

		this.setSize(800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
