package de.adv.atech.roboter.gui;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.AbstractButton;

import de.adv.atech.roboter.gui.action.GUIAbstractAction;

public class ActionManager {

	protected Map<String, GUIAbstractAction> actionMap;

	public ActionManager() {
		this.actionMap = new LinkedHashMap<String, GUIAbstractAction>();

	}

	public boolean registerAction(GUIAbstractAction action) {
		this.actionMap.put(action.getName(), action);

		return true;
	}

	public boolean registerActionSmart(GUIAbstractAction action) {
		String name = action.getName();

		if (!isRegisteredAction(name)) {
			registerAction(action);
		}

		return true;
	}

	public boolean unregisterClient(GUIAbstractAction action) {
		this.actionMap.remove(action);

		return true;
	}

	public Map<String, GUIAbstractAction> getRegisteredActions() {
		return this.actionMap;
	}

	public boolean isRegisteredAction(GUIAbstractAction action) {
		return this.actionMap.containsValue(action);
	}

	public boolean isRegisteredAction(String name) {
		return this.actionMap.containsKey(name);
	}

	public GUIAbstractAction getClient(String identifier) {
		GUIAbstractAction action = null;

		if (this.actionMap.containsKey(identifier)) {
			action = this.actionMap.get(identifier);
		}

		return action;
	}

	public AbstractButton getButton(String identifier, int buttonType,
			String labelOverride) {
		GUIAbstractAction action = getClient(identifier);

		return action.getButton(buttonType, labelOverride);
	}

}
