package de.adv.atech.roboter.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractButton;
import javax.swing.Icon;

import de.adv.atech.roboter.gui.components.SaveCodeDialog;
import de.adv.atech.roboter.gui.media.Media;

public class SaveCodeAction extends GUIAbstractAction {

	public static final String actionName = "Speichern";

	public SaveCodeAction() {
		super(actionName, Media.getIcon("disk"));
	}
	
	public SaveCodeAction(String name, Icon icon) {
		super(name, icon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEvent event) throws Exception {
		new SaveCodeDialog();
	}

	@Override
	public void registerButtonListener(AbstractButton button) {
		// TODO Auto-generated method stub
		
	}

}
