package de.adv.atech.roboter.gui;

import java.awt.TextArea;

public class DebugPanel extends TextArea {

	public DebugPanel() {
		super(7, 60);
	}

	public void addText(String text) {
		super.append(text + System.getProperty("line.separator"));
	}

}
