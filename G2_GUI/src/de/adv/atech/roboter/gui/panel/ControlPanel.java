package de.adv.atech.roboter.gui.panel;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import de.adv.atech.roboter.commons.commands.rvm1.MoveToCoordinates;
import de.adv.atech.roboter.commons.commands.rvm1.MoveToNest;
import de.adv.atech.roboter.commons.commands.rvm1.MoveToOrigin;
import de.adv.atech.roboter.gui.components.manualControl.BaseControl;
import de.adv.atech.roboter.gui.components.manualControl.SliderControl;

public class ControlPanel extends JPanel {

	public ControlPanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(new BaseControl(new MoveToNest(), "Nestposition"));
		add(new BaseControl(new MoveToOrigin(), "Origin"));
		SliderControl slider = new SliderControl(new MoveToCoordinates(), "Move to Coordiantes");
		slider.addSlider(0, 100, "X");
		slider.addSlider(0, 100, "Y");
		slider.addSlider(0, 200, "Z");
		add(slider);
	}

}
