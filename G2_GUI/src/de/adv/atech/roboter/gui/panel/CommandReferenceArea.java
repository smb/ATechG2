package de.adv.atech.roboter.gui.panel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.exceptions.ClientException;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.interfaces.CommandChangedListener;
import de.adv.atech.roboter.commons.interfaces.CommandManager;
import de.adv.atech.roboter.gui.components.MyHTMLEditorKit;
import de.adv.atech.roboter.gui.core.GUIController;
import edu.stanford.ejalbert.BrowserLauncher;

public class CommandReferenceArea extends JEditorPane implements ItemListener,
		CommandChangedListener, HyperlinkListener {

	private StringBuffer commandRef;

	private String stringFormat = "<html><body bgcolor=\"%s\"><br>%s</body></html>";

	String BGColor = null;

	public CommandReferenceArea() {
		super();

		setEditable(false);

		setEditorKit(new MyHTMLEditorKit());

		BGColor = Integer.toHexString(this.getBackground().getRed())
				+ Integer.toHexString(this.getBackground().getGreen())
				+ Integer.toHexString(this.getBackground().getBlue());

		setAlignmentY(JLabel.TOP_ALIGNMENT);

		addHyperlinkListener(this);

		// setBorder(null);

		this.commandRef = new StringBuffer();

		// this.commandRef.append("Hallo<br>HUND<br>im<br><b>BUERO</b><br>");

		// this.setVerticalAlignment(SwingConstants.TOP);

		this.setText(String.format(this.stringFormat, BGColor, this.commandRef
				.toString()));

	}

	public void update(Client client) {
		this.commandRef = new StringBuffer(400);

		if (client != null) {

			CommandManager commandManager = null;
			commandManager = client.getCommandManager();

			try {
				displayCommandInfo(this.commandRef, commandManager);
			}
			catch (CommandException e) {
				this.commandRef
						.append("Command Liste konnte nicht gelesen werden: "
								+ e.getMessage());
			}
			catch (ClientException e) {

			}
		}

		this.setText(String.format(this.stringFormat, BGColor, this.commandRef
				.toString()));
	}

	public void displayCommandInfo(StringBuffer ref,
			CommandManager commandManager) throws CommandException,
			ClientException {
		for (Iterator<Class<? extends Command>> it = commandManager
				.getCommandList().iterator(); it.hasNext();) {
			Class<? extends Command> commandClass = it.next();

			Command command = commandManager.getCommandInstance(commandClass,
					false);

			ref
					.append(command
							.getSyntax(Constant.COMMAND_SYNTAX_STYLE_COMMAND_REFERENCE));

			ref.append("<br>");
		}
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		String clientIdentifier = (String) arg0.getItem();

		Client client = GUIController.getInstance().getClientManager()
				.getClient(clientIdentifier);

		if (client != null) {

			GUIController.getInstance().getClientManager().setActiveClient(
					client);

			client.getCommandManager().registerCommandChangedListener(this);

		}
		update(client);
		//
	}

	@Override
	public void commandChanged(Client client) {
		update(client);

	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			try {
				BrowserLauncher launcher = new BrowserLauncher();
				launcher.openURLinBrowser(e.getURL().toString());
			}
			catch (Exception r) {
			}
		}
	}
}
