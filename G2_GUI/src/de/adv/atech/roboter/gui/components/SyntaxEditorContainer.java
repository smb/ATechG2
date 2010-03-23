package de.adv.atech.roboter.gui.components;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SyntaxEditorContainer extends JPanel {

	JEditorPane edit;
	public SyntaxEditorContainer() {
		this.setLayout(new BorderLayout());
		edit = new JEditorPane();
		this.add(new JScrollPane(edit), BorderLayout.CENTER);
		

	}
	
	public String getEditorContent() {
		return edit.getText();
	}
}
