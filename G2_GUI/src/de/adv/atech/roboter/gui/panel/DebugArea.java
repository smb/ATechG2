package de.adv.atech.roboter.gui.panel;

import java.awt.TextArea;

public class DebugArea extends TextArea {

	public DebugArea() {
		super();
	}

	public void addText(String text) {
		super.append(text + System.getProperty("line.separator"));

	}

}
