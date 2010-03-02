package de.adv.atech.roboter.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

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
		DefaultSyntaxKit kit = (DefaultSyntaxKit) syntaxEditor.getEditorKit();
		
		InputStream inputSream = SyntaxEditorContainer.class.getResourceAsStream("editor.properties");
		Properties editorProperties = new Properties();
		try {
			editorProperties.load(inputSream);
			inputSream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		kit.setConfig(editorProperties);		
		syntaxEditor.setEditorKit(kit);
		syntaxEditor.setBackground(Color.WHITE);
		
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
