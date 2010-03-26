package de.adv.atech.roboter.gui.components;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.ErrorMessages;
import de.adv.atech.roboter.commons.exceptions.ClientException;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Client;
import de.adv.atech.roboter.commons.interfaces.CommandChangedListener;
import de.adv.atech.roboter.commons.interfaces.CommandManager;
import de.adv.atech.roboter.gui.components.numberedEditorKit.NumberedEditorKit;
import de.adv.atech.roboter.gui.core.GUIController;

public class SyntaxEditorPanel extends JPanel implements CommandChangedListener {

	JEditorPane editorPane;
	SyntaxEditorPopup popup;

	public SyntaxEditorPanel() {
		editorPane = new JEditorPane();
		editorPane.setEditorKit(new NumberedEditorKit());

		CommandManager commandManager = GUIController.getInstance().getActiveClientCommandManager();
		commandManager.registerCommandChangedListener(this);
		
		popup = new SyntaxEditorPopup(editorPane);
		editorPane.add(popup);
		editorPane.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())
					popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});

		JScrollPane scroll = new JScrollPane(editorPane);
		this.setLayout(new BorderLayout());
		this.add(scroll, BorderLayout.CENTER);
		this.setVisible(true);
	}

	public String getEditorContent() {
		return editorPane.getText();
	}

	@Override
	public void commandChanged(Client client) {
		try {
			popup.updatePopupMenu();
		} catch (CommandException e) {
			ControllerManager.message(Constant.MESSAGE_TYPE_ERROR, ErrorMessages.SYNTAXEDITOR_POPUP_COMMAND_ERROR);
		} catch (ClientException e) {
			ControllerManager.message(Constant.MESSAGE_TYPE_ERROR, ErrorMessages.SYNTAXEDITOR_POPUP_CLIENT_ERROR);
		}
	}
}