package de.adv.atech.roboter.gui.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;

import de.adv.atech.roboter.gui.components.filefilter.RobotCodeFileFilter;
import de.adv.atech.roboter.gui.core.GUIController;

public class LoadCodeDialog extends JFileChooser{

	public LoadCodeDialog() throws IOException {
		this.setFileFilter(new RobotCodeFileFilter());
		
		int returnVal = this.showOpenDialog(GUIController.getInstance().getEditorPanel());
		File file = this.getSelectedFile();			
		
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	BufferedReader bufferedReader = new BufferedReader(
        	           new InputStreamReader(
        	           new FileInputStream(file))); 
        	StringBuffer contentOfFile = new StringBuffer();
        	String line; 
        	while ((line = bufferedReader.readLine()) != null) {
        	    contentOfFile.append(line);
        	    contentOfFile.append("\n");
        	}
        	String content = contentOfFile.toString();
        	GUIController.getInstance().getEditorPanel().setEditorContent(content);
        } 

	}
}
