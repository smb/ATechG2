package de.adv.atech.roboter.gui;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JMenuItem;

import de.adv.atech.roboter.gui.action.GUIAbstractAction;

public class ActionManager {

	protected Map<Class<? extends GUIAbstractAction>, GUIAbstractAction> actionMap;

	public ActionManager() {
		this.actionMap = new LinkedHashMap<Class<? extends GUIAbstractAction>, GUIAbstractAction>();

	}

	public boolean registerAction(Class<? extends GUIAbstractAction> identifier) {

		GUIAbstractAction action;
		try {
			action = identifier.newInstance();

			this.actionMap.put(identifier, action);

			return true;

		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean registerActionSmart(
			Class<? extends GUIAbstractAction> identifier) {

		if (!isRegisteredAction(identifier)) {
			registerAction(identifier);
		}

		return true;
	}

	public boolean unregisterClient(GUIAbstractAction action) {
		this.actionMap.remove(action);

		return true;
	}

	public Map<Class<? extends GUIAbstractAction>, GUIAbstractAction> getRegisteredActions() {
		return this.actionMap;
	}

	public boolean isRegisteredAction(
			Class<? extends GUIAbstractAction> indentifier) {
		return this.actionMap.containsKey(indentifier);
	}

	public boolean isRegisteredAction(GUIAbstractAction action) {
		return this.actionMap.containsValue(action);
	}

	public GUIAbstractAction getClient(
			Class<? extends GUIAbstractAction> identifier) {
		GUIAbstractAction action = null;

		if (this.actionMap.containsKey(identifier)) {
			action = this.actionMap.get(identifier);
		}

		return action;
	}

	public AbstractButton getButton(
			Class<? extends GUIAbstractAction> identifier, int buttonType,
			String labelOverride) {
		GUIAbstractAction action = getClient(identifier);

		return action.getButton(buttonType, labelOverride);
	}

	public JMenuItem getJMenuItem(
			Class<? extends GUIAbstractAction> identifier, String labelOverride) {

		return (JMenuItem) this.getButton(identifier,
				GUIAbstractAction.TYPE_MENU_ITEM, labelOverride);
	}

	public JButton getJButton(Class<? extends GUIAbstractAction> identifier,
			String labelOverride) {

		return (JButton) this.getButton(identifier,
				GUIAbstractAction.TYPE_BUTTON, labelOverride);
	}

}
