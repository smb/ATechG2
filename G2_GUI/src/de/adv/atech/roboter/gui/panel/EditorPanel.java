package de.adv.atech.roboter.gui.panel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import de.adv.atech.roboter.gui.components.SyntaxEditorContainer;

public class EditorPanel extends JPanel {

	SyntaxEditorContainer syntaxEditorPane;

	ClientInfoPanel clientInfoPanel;

	public EditorPanel() {

		this.syntaxEditorPane = new SyntaxEditorContainer();

		this.clientInfoPanel = new ClientInfoPanel();

		this.setLayout(new BorderLayout());

		this.add(this.syntaxEditorPane, BorderLayout.CENTER);

		this.add(this.clientInfoPanel, BorderLayout.EAST);

	}

	public String getEditorContent() {
		return this.syntaxEditorPane.getEditorContent();
	}

}
