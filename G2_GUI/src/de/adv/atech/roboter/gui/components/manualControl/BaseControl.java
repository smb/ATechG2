package de.adv.atech.roboter.gui.components.manualControl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;

public class BaseControl extends JPanel {

	JButton executeButton;
	
	Command command;
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
		List<Double> parameterList = getParameters();
		if (parameterList != null && !parameterList.isEmpty()) {
			int i=0;
			Set<Enum<?>> parameterSet = command.getParameters().keySet();
			for (Iterator<Enum<?>> iterator = parameterSet.iterator(); iterator.hasNext();) {
				Enum<?> parameterKey = (Enum<?>) iterator.next();	
				if (i < parameterList.size()) {
					command.setParameter(parameterKey, parameterList.get(i));	
				}
				i++;
			}
		}
		System.out.println(command.toString());
		//TODO: impl
	}
	
	List<Double> getParameters() {
		return null;
	}
	
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
