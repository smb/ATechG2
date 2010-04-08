package de.adv.atech.roboter.gui.components.filefilter;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import de.adv.atech.roboter.commons.Constant;

public class RobotCodeFileFilter extends FileFilter{

	@Override
	public boolean accept(File f) {
		return ((f.isDirectory()) || (f.getName().toLowerCase().endsWith(Constant.EDITOR_LOADFILE_FILTER_ENDING))); 
	}

	@Override
	public String getDescription() {
		return Constant.EDITOR_LOADFILE_FILTER_DESCRIPTION;
	}
	
}
