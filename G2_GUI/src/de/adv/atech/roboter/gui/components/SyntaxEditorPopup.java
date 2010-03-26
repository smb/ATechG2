package de.adv.atech.roboter.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.BadLocationException;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.ErrorMessages;
import de.adv.atech.roboter.commons.exceptions.ClientException;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;
import de.adv.atech.roboter.commons.interfaces.CommandManager;
import de.adv.atech.roboter.gui.core.GUIController;

public class SyntaxEditorPopup extends JPopupMenu {
	
	private JEditorPane editorPane;
	private JMenu robotMenu;
	
	protected SyntaxEditorPopup(final JEditorPane editorPane) {
		this.editorPane = editorPane;
		
		JMenu languageMenu = new JMenu("Sprachbefehle");
		this.add(languageMenu);
		robotMenu = new JMenu("Roboterbefehle");
		robotMenu.setEnabled(false);
		this.add(robotMenu);
		
		JMenuItem loopMenuItem = new JMenuItem(Constant.COMMANDPARSER_COMMAND_LOOP);
		languageMenu.add(loopMenuItem);
		loopMenuItem.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				addTextToEditor(Constant.COMMANDPARSER_COMMAND_LOOP_STRING);
			}
		});
		
		JMenuItem loopendMenuItem = new JMenuItem(Constant.COMMANDPARSER_COMMAND_LOOPEND);
		languageMenu.add(loopendMenuItem);
		loopendMenuItem.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				addTextToEditor(Constant.COMMANDPARSER_COMMAND_LOOPEND_STRING);
			}
		});
		
		JMenuItem subMenuItem = new JMenuItem(Constant.COMMANDPARSER_COMMAND_SUB);
		languageMenu.add(subMenuItem);
		subMenuItem.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				addTextToEditor(Constant.COMMANDPARSER_COMMAND_SUB_STRING);
			}
		});
		
	}
	
	public void updatePopupMenu() throws CommandException, ClientException {
		CommandManager commandManager = GUIController.getInstance().getActiveClientCommandManager();
		List<Class<? extends Command>> commandList = commandManager.getCommandList();
		for (Iterator<Class<? extends Command>> iterator = commandList.iterator(); iterator.hasNext();) {
			robotMenu.setEnabled(true);
			Class<? extends Command> commandClass = iterator.next();
			final Command command = commandManager.getCommandInstance(commandClass, false);
			JMenuItem menuItem = new JMenuItem(command.getCommandName());
			robotMenu.add(menuItem);
			menuItem.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					addTextToEditor(command.getSyntax(Constant.COMMAND_SYNTAX_STYLE_EDITOR));
				}
			});
		}
	}
	
	private void addTextToEditor(String string) {
		int caretPos = editorPane.getCaretPosition();
		string = string + "\n";
		try {
			editorPane.getDocument().insertString(caretPos, string, null);
		} catch (BadLocationException e) {
			ControllerManager.message(Constant.MESSAGE_TYPE_ERROR, ErrorMessages.SYNTAXEDITOR_POPUP_CARET_ERROR);
		}
	}
}
