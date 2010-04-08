package de.adv.atech.roboter.gui.components.manualControl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;

public class BaseControl extends JPanel {
	
	Command command;
	
	private JButton executeButton;
	private String title;
	
	public BaseControl(Command command, String title) {
		this.command = command;
		this.title = title;
		
		setLayout(new BorderLayout());
		setBorderTitle();
		
		executeButton = new JButton(title);
		executeButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sendCommand();
				} catch (CommandException e1) {
					System.out.println(e1);
				}
			}
		});
		add(executeButton, BorderLayout.SOUTH);
	}
	
	private void sendCommand() throws CommandException {
		setCommandParameters();
		System.out.println(command.toString());
		//TODO: impl
	}
	
	// wird von erbenden Klassen ggf. Ÿberschrieben
	void setCommandParameters() {}
	
	public void setExecuteButtonTitle(String title) {
		executeButton.setText(title);
	}
	
	private void setBorderTitle() {
		this.setBorder(
				new TitledBorder(
						new LineBorder(Color.black, 1, true),
						title, TitledBorder.CENTER, TitledBorder.BELOW_TOP));
	}

}
