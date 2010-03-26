package de.adv.atech.roboter.gui.core;

import java.util.Iterator;
import java.util.List;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.NetworkClient;
import de.adv.atech.roboter.commons.exceptions.AbstractException;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.gui.commandparser.CommandParser;

public class ActionHandler implements RobotListener {

	public void execute(RobotGUIEvent e) {
		ControllerManager.message(Constant.MESSAGE_TYPE_INFO, "Event: " + e);

		if (e.getActionCommand().equals("Send")) {
			CommandParser cp = new CommandParser();

			String editorContent = GUIController.getInstance().getEditorPanel()
					.getEditorContent();

			try {
				List<Command> commandList = cp.getCommandList(editorContent);

				StringBuffer sb = new StringBuffer();

				sb.append("Erkannte Befehle:\n");

				for (Iterator<Command> it = commandList.iterator(); it
						.hasNext();) {
					Command tmpCommand = it.next();

					sb.append("- ");
					sb.append(tmpCommand);
					sb.append("\n");
				}

				ControllerManager.message(Constant.MESSAGE_TYPE_INFO, sb
						.toString());

				NetworkClient client = GUIController.getInstance()
						.getClientManager().getActiveNetworkClient();

				if (client != null) {
					client.getRMI().processCommand(commandList);
				}
			}
			catch (AbstractException ex) {
				ex.dumpException();
			}
			catch (Exception ex) {
				ControllerManager.message(Constant.MESSAGE_TYPE_ERROR,
						"Fehler: " + ex.getMessage());

			}

		}
	}
}
