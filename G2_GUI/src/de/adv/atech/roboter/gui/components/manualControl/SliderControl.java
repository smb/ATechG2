package de.adv.atech.roboter.gui.components.manualControl;

import java.awt.Component;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;

public class SliderControl extends AbstractParametrizableControl {

	public SliderControl(Command command, String title) {
		super(command, title);
	}
	
	@Override
	public void addParameterControl(Enum<?> parameterName, int min, int max, String title) {
		JSlider slider = new JSlider();
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setValue(min);
		slider.setBorder(BorderFactory.createTitledBorder(title));
		slider.setPaintTicks(true);

		final JLabel valueLabel = new JLabel(String.valueOf(slider.getValue()));
		valueLabel.setText(String.valueOf(max)); // um max Breite von Jlabel zu setzen
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(max/2), valueLabel);
		slider.setLabelTable(labelTable);
		slider.setPaintLabels(true);
		
		valueLabel.setText(String.valueOf(slider.getValue()));
		slider.addChangeListener(new ChangeListener() {	
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				valueLabel.setText(String.valueOf(slider.getValue()));
			}
		});
		componentParameterMap.put(slider, parameterName);
		panel.add(slider);
	}

	@Override
	void setCommandParameters() {
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i++) {
			JSlider slider = (JSlider) components[i];
			double value = slider.getValue();
			Enum<?> parameterName = componentParameterMap.get(slider);
			try {
				command.setParameter(parameterName, value);
			} catch (CommandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
