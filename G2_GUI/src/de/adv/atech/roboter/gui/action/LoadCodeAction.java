package de.adv.atech.roboter.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractButton;
import javax.swing.Icon;

import de.adv.atech.roboter.gui.components.LoadCodeDialog;
import de.adv.atech.roboter.gui.media.Media;

public class LoadCodeAction extends GUIAbstractAction {

	public static final String actionName = "Öffnen";

	public LoadCodeAction() {
		super(actionName, Media.getIcon("folder_page_white"));
	}
	
	public LoadCodeAction(String name, Icon icon) {
		super(name, icon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEvent event) throws Exception {
		new LoadCodeDialog();
	}

	@Override
	public void registerButtonListener(AbstractButton button) {
		// TODO Auto-generated method stub
		
	}

}
