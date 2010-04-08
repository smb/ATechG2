package de.adv.atech.roboter.gui.components.manualControl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import de.adv.atech.roboter.commons.interfaces.Command;

public abstract class AbstractParametrizableControl extends BaseControl {

	HashMap<Component, Enum<?>> componentParameterMap;
	JPanel panel;
	
	public AbstractParametrizableControl(Command command, String title) {
		super(command, title);
		componentParameterMap = new HashMap<Component, Enum<?>>();
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		add(panel, BorderLayout.CENTER);
	}

	@Override
	abstract void setCommandParameters();
	
	public abstract void addParameterControl(Enum<?> parameterName, int min, int max, String title);

}
