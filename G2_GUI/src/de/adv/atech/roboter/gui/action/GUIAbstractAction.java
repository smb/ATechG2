package de.adv.atech.roboter.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JMenuItem;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;

public abstract class GUIAbstractAction {

	public final static int TYPE_BUTTON = 0;

	public final static int TYPE_MENU_ITEM = 1;

	protected Icon icon;

	protected String label;

	protected String name;

	private ActionListener listener = new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			try {
				execute(arg0);
			}
			catch (Exception ex) {
				ControllerManager.message(Constant.MESSAGE_TYPE_ERROR, ex
						.getMessage());
			}
		}
	};

	public String getName() {
		return this.name;
	}

	public String getLabel() {
		return this.label;
	}

	public AbstractButton getButton(int buttonType, String labelOverride) {
		if (labelOverride == null) {
			labelOverride = this.label;
		}

		AbstractButton button = null;

		if (buttonType == TYPE_BUTTON) {
			button = new JButton(labelOverride);
		}
		if (buttonType == TYPE_MENU_ITEM) {
			button = new JMenuItem(labelOverride);
		}

		if (this.icon != null) {
			button.setIcon(this.icon);
		}

		button.addActionListener(this.listener);

		this.registerButtonListener(button);

		return button;
	}

	public abstract void registerButtonListener(AbstractButton button);

	public GUIAbstractAction(String name, String defaultLabel, Icon icon) {
		this.name = name;
		this.icon = icon;
		this.label = defaultLabel;
	}

	public GUIAbstractAction(String name, Icon icon) {
		this.name = name;
		this.icon = icon;
		this.label = name;
	}

	public abstract void execute(ActionEvent event) throws Exception;

}
