package de.adv.atech.roboter.gui.components;

import javax.swing.JFileChooser;

import de.adv.atech.roboter.gui.core.GUIController;

public class LoadCodeDialog extends JFileChooser{

	public LoadCodeDialog() {
		String editorContent = GUIController.getInstance().getEditorPanel().getEditorContent();
			
		int returnVal = this.showOpenDialog(GUIController.getInstance().getEditorPanel());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
           // TODO: impl
        } 

	}
}
