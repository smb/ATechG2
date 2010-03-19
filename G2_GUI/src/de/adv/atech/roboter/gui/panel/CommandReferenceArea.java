package de.adv.atech.roboter.gui.panel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.interfaces.CommandChangedListener;
import de.adv.atech.roboter.commons.interfaces.CommandManager;
import de.adv.atech.roboter.gui.core.GUIController;

public class CommandReferenceArea extends JLabel implements ItemListener,
		CommandChangedListener {

	private StringBuffer commandRef;

	private String stringFormat = "<html><br>%s</html>";

	public CommandReferenceArea() {
		super();

		this.commandRef = new StringBuffer();

		// this.commandRef.append("Hallo<br>HUND<br>im<br><b>BUERO</b><br>");

		this.setVerticalAlignment(SwingConstants.TOP);

		this.setText(String.format(this.stringFormat, this.commandRef
				.toString()));

	}

	public void update(Client client) {

		CommandManager commandManager = null;
		commandManager = client.getCommandManager();

		this.commandRef = new StringBuffer(400);

		try {
			displayCommandInfo(this.commandRef, commandManager);
		}
		catch (CommandException e) {
			this.commandRef
					.append("Command Liste konnte nicht gelesen werden: "
							+ e.getMessage());
		}

		this.setText(String.format(this.stringFormat, this.commandRef
				.toString()));
	}

	public void displayCommandInfo(StringBuffer ref,
			CommandManager commandManager) throws CommandException {
		for (Iterator<Class<? extends Command>> it = commandManager
				.getCommandList().iterator(); it.hasNext();) {
			Class<? extends Command> commandClass = it.next();

			Command command = commandManager.getCommandInstance(commandClass,
					false);

			appendCommandInfo(ref, command);

			ref.append("<br>");
		}
	}

	public void appendCommandInfo(StringBuffer ref, Command command) {

		StringBuffer localRef = new StringBuffer(200);

		localRef.append("<b>");
		localRef.append(command.getCommandName());
		localRef.append("</b><br>");

		try {

			for (Iterator<Entry<Enum<?>, Field>> it = command.getParameters()
					.entrySet().iterator(); it.hasNext();) {
				Entry<Enum<?>, Field> entry = it.next();

				localRef.append(" - " + entry.getKey() + " ("
						+ command.getParameterClass(entry.getValue()) + ")");

				localRef.append("<br>");
			}

			localRef.append("<br>");
		}
		catch (CommandException ex) {
			localRef.append("- Parameter konnten nicht gelesen werden: "
					+ ex.getMessage());
			localRef.append("<br>");
		}

		ref.append(localRef);
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		String clientIdentifier = (String) arg0.getItem();

		Client client = GUIController.getInstance().getClientManager()
				.getClient(clientIdentifier);

		client.getCommandManager().registerCommandChangedListener(this);

		update(client);
		//
	}

	@Override
	public void commandChanged(Client client) {
		update(client);

	}
}
