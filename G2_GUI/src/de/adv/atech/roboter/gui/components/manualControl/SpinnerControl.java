package de.adv.atech.roboter.gui.components.manualControl;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;

import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;

public class SpinnerControl extends AbstractParametrizableControl {

	public SpinnerControl(Command command, String title) {
		super(command, title);
	}

	@Override
	public void addParameterControl(Enum<?> parameterName, int min, int max, String title) {
		JPanel helpPanel = new JPanel(new BorderLayout());
		helpPanel.add(new JLabel(title), BorderLayout.WEST);
		
		JSpinner spinner = new JSpinner();
		helpPanel.add(spinner, BorderLayout.CENTER);
		
		componentParameterMap.put(spinner, parameterName);
		panel.add(helpPanel);
	}

	@Override
	void setCommandParameters() {
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i++) {
			JPanel helpPanel = (JPanel) components[i];
			JSpinner spinner = (JSpinner) helpPanel.getComponent(1);
			double value = ((Integer)spinner.getValue()).doubleValue();
			Enum<?> parameterName = componentParameterMap.get(spinner);
			try {
				command.setParameter(parameterName, value);
			} catch (CommandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
