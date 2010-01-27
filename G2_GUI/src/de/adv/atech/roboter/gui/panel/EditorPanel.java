package de.adv.atech.roboter.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import de.adv.atech.roboter.gui.components.SyntaxEditorContainer;

public class EditorPanel extends JPanel {

	public EditorPanel() {

		SyntaxEditorContainer syntaxEditorPane = new SyntaxEditorContainer();

		this.setLayout(new BorderLayout());

		this.add(syntaxEditorPane, BorderLayout.CENTER);

	}

}
