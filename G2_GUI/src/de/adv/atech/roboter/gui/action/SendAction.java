package de.adv.atech.roboter.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractButton;

import de.adv.atech.roboter.commons.ClientManager;
import de.adv.atech.roboter.commons.interfaces.ClientChangedListener;
import de.adv.atech.roboter.gui.core.EventDispatcher;
import de.adv.atech.roboter.gui.core.GUIController;
import de.adv.atech.roboter.gui.core.RobotGUIEvent;
import de.adv.atech.roboter.gui.media.Media;

public class SendAction extends GUIAbstractAction implements
		ClientChangedListener {

	public static final String actionName = "Send";

	public SendAction() {
		super(actionName, Media.getIcon("script_go"));
	}

	public void execute(ActionEvent event) throws Exception {

		GUIController.getInstance().getEventDispatcher().fireActionEvent(
				new RobotGUIEvent(this, EventDispatcher.TYPE_ACTION, "Send"));

	}

	public void clientChanged(ClientManager clientManager, Object object) {
		if (clientManager.getRegisteredNetworkClientCount() > 0) {
			((AbstractButton) object).setEnabled(true);
		}
		else {
			((AbstractButton) object).setEnabled(false);
		}
	}

	public void registerButtonListener(AbstractButton button) {
		GUIController.getInstance().getClientManager()
				.registerClientChangedListener(this, button);
	}

	@Override
	public void activeClientChanged(ClientManager clientManager, Object object) {
		// TODO Auto-generated method stub

	}
}
