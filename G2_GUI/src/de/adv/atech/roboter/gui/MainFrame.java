package de.adv.atech.roboter.gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import de.adv.atech.roboter.gui.components.SyntaxEditorContainer;

public class MainFrame extends JFrame {

	public MainFrame() {
		super("Roboter-Steuerung");
		
		SyntaxEditorContainer syntaxEditorPane = new SyntaxEditorContainer();
		
		JScrollPane jScrollPane = new JScrollPane(syntaxEditorPane);
		this.add(jScrollPane);
		
		this.setSize(800, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
