package de.adv.atech.roboter.gui.components.numberedEditorKit;

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

public class NumberedEditorKit extends StyledEditorKit {
	
	public ViewFactory getViewFactory() {
		return new NumberedViewFactory();
	}
}