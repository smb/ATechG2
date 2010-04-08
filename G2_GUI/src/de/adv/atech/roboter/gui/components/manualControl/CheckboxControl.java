package de.adv.atech.roboter.gui.components.manualControl;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;

public class CheckboxControl extends AbstractParametrizableControl {

	public CheckboxControl(Command command, String title) {
		super(command, title);
	}

	@Override
	public void addParameterControl(Enum<?> parameterName, int min, int max, String title) {
		JCheckBox checkbox = new JCheckBox();
		checkbox.setText(title);
		componentParameterMap.put(checkbox, parameterName);
		panel.add(checkbox);
	}

	@Override
	void setCommandParameters() {
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i++) {
			JCheckBox checkBox = (JCheckBox) components[i];
			boolean value = checkBox.isSelected();
			Enum<?> parameterName = componentParameterMap.get(checkBox);
			try {
				command.setParameter(parameterName, value);
			} catch (CommandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
