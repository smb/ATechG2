package de.adv.atech.roboter.gui.components;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.text.EditorKit;

import jsyntaxpane.DefaultSyntaxKit;

public class SyntaxEditorContainer extends Container {

	JEditorPane syntaxEditor;

	public SyntaxEditorContainer() {
		this.setLayout(new BorderLayout());

		DefaultSyntaxKit.initKit();

		syntaxEditor = new JEditorPane();
		JScrollPane scrollPane = new JScrollPane(syntaxEditor);
		JToolBar toolBar = new JToolBar();

		syntaxEditor.setContentType("text/java");

		toolBar.removeAll();
		EditorKit kit = syntaxEditor.getEditorKit();
		if (kit instanceof DefaultSyntaxKit) {
			DefaultSyntaxKit defaultSyntaxKit = (DefaultSyntaxKit) kit;
			defaultSyntaxKit.addToolBarActions(syntaxEditor, toolBar);
		}
		toolBar.validate();

		this.add(toolBar, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.doLayout();
	}

	/**
	 * 
	 * @return
	 */
	public String getEditorContent() {
		return syntaxEditor.getText();
	}
}
