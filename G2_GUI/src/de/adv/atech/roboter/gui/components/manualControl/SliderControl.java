package de.adv.atech.roboter.gui.components.manualControl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.adv.atech.roboter.commons.interfaces.Command;

public class SliderControl extends BaseControl {

	JPanel panel;
	
	public SliderControl(Command command, String title) {
		super(command, title);
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		add(panel, BorderLayout.CENTER);
	}
	
	public List<Double> getParameters() {
		List<Double> parameterList = new ArrayList<Double>();
		Component[] components = panel.getComponents();
		for (int i = 0; i < components.length; i++) {
			JSlider slider = (JSlider) components[i];
			parameterList.add(Double.valueOf(slider.getValue()));
		}
		return parameterList;
	}
	
	public void addSlider(int min, int max, String title) {
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
		
		panel.add(slider);
	}

}
