package de.adv.atech.roboter.gui.panel;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import de.adv.atech.roboter.commons.commands.rvm1.MoveToCoordinates;
import de.adv.atech.roboter.commons.commands.rvm1.MoveToNest;
import de.adv.atech.roboter.commons.commands.rvm1.MoveToOrigin;
import de.adv.atech.roboter.commons.commands.rvm1.SetSpeed;
import de.adv.atech.roboter.gui.components.manualControl.BaseControl;
import de.adv.atech.roboter.gui.components.manualControl.CheckboxControl;
import de.adv.atech.roboter.gui.components.manualControl.SliderControl;
import de.adv.atech.roboter.gui.components.manualControl.SpinnerControl;

public class ControlPanel extends JPanel {

	public ControlPanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
		BaseControl nest = new BaseControl(new MoveToNest(), "Nest");
		nest.setExecuteButtonTitle("Nestposition");
		add(nest);
		add(new BaseControl(new MoveToOrigin(), "Origin"));
		
		SliderControl slider = new SliderControl(new MoveToCoordinates(), "Move to Coordiantes");
		slider.addParameterControl(MoveToCoordinates.Parameter.X, 0, 100, "X");
		slider.addParameterControl(MoveToCoordinates.Parameter.Y, 0, 100, "Y");
		slider.addParameterControl(MoveToCoordinates.Parameter.Z, 0, 100, "Z");
		add(slider);
		
		SpinnerControl spinner = new SpinnerControl(new SetSpeed(), "Speed");
		spinner.addParameterControl(SetSpeed.Parameter.Speed, 0, 100, "Speed");
		add(spinner);
		
		CheckboxControl checkBox = new CheckboxControl(new SetSpeed(), "Beschleunigung");
		checkBox.addParameterControl(SetSpeed.Parameter.FastAcceleration, 0, 0, "Schnell?");
		add(checkBox);
	}

}
