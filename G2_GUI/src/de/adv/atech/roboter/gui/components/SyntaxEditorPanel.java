package de.adv.atech.roboter.gui.components;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.adv.atech.roboter.gui.components.numberedEditorKit.NumberedEditorKit;

public class SyntaxEditorPanel extends JPanel {

	JEditorPane edit;
	
	public SyntaxEditorPanel() {
        edit = new JEditorPane();
        edit.setEditorKit(new NumberedEditorKit());

        JScrollPane scroll = new JScrollPane(edit);
        this.setLayout(new BorderLayout());
        this.add(scroll, BorderLayout.CENTER);
        setSize(300, 300);
        setVisible(true);
    }
	
	public String getEditorContent() {
		return edit.getText();
	}
}