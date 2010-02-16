package de.adv.atech.roboter.gui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import de.adv.atech.roboter.gui.components.SyntaxEditorContainer;

public class EditorPanel extends JPanel {

	SyntaxEditorContainer syntaxEditorPane;

	public EditorPanel() {

		syntaxEditorPane = new SyntaxEditorContainer();

		this.setLayout(new BorderLayout());

		this.add(syntaxEditorPane, BorderLayout.CENTER);

	}

	public String getEditorContent() {
		return syntaxEditorPane.getEditorContent();
	}

}
