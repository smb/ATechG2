package de.adv.atech.roboter.gui.components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.JFileChooser;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.gui.components.filefilter.RobotCodeFileFilter;
import de.adv.atech.roboter.gui.core.GUIController;

public class SaveCodeDialog extends JFileChooser{

	public SaveCodeDialog() throws IOException {
		this.setFileFilter(new RobotCodeFileFilter());
		String editorContent = GUIController.getInstance().getEditorPanel().getEditorContent();
		int returnVal = this.showSaveDialog(GUIController.getInstance().getEditorPanel());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
           File selectedFile = this.getSelectedFile();
           String filename = selectedFile.getAbsolutePath() + Constant.EDITOR_LOADFILE_FILTER_ENDING;
           File file = new File (filename);
           Writer fw = new FileWriter(file); 
           fw.write(editorContent);
           fw.flush();
           fw.close();
        } 

	}
}
