package de.adv.atech.roboter.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import de.adv.atech.roboter.gui.components.SyntaxEditorContainer;
import de.adv.atech.roboter.gui.core.GUIController;

public class MainFrame extends JFrame {

	public MainFrame() {
		super("Roboter-Steuerung");

		SyntaxEditorContainer syntaxEditorPane = new SyntaxEditorContainer();

		this.setLayout(new BorderLayout());

		JScrollPane jScrollPane = new JScrollPane(syntaxEditorPane);
		this.add(jScrollPane, BorderLayout.CENTER);

		DebugPanel debugPanel = new DebugPanel();

		GUIController.getInstance().setDebugPanel(debugPanel);

		JScrollPane debugScrollPane = new JScrollPane(debugPanel);

		this.add(debugScrollPane, BorderLayout.SOUTH);

		this.setSize(800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
