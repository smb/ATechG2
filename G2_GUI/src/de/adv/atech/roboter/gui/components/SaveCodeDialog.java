package de.adv.atech.roboter.gui.components;

import javax.swing.JFileChooser;

import de.adv.atech.roboter.gui.core.GUIController;

public class SaveCodeDialog extends JFileChooser{

	public SaveCodeDialog() {
		String editorContent = GUIController.getInstance().getEditorPanel().getEditorContent();
			
		int returnVal = this.showSaveDialog(GUIController.getInstance().getEditorPanel());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
           // TODO: impl
        } 

	}
}
