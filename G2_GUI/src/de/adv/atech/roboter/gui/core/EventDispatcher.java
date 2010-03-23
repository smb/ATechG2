package de.adv.atech.roboter.gui.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.event.EventListenerList;

/**
 * Dynamischer Event-Dispatcher
 * 
 * @author sb
 */
public class EventDispatcher {

	public static final String TYPE_ACTION = "ACTION";

	Map<String, Map<String, List<RobotListener>>> eventMap;

	public EventDispatcher() {
		this.eventMap = new HashMap<String, Map<String, List<RobotListener>>>();
	}

	EventListenerList eventListeners;

	public void registerEvent(RobotListener listener, String actionCommand,
			String type) {

		getListenerList(actionCommand, type).add(listener);
	}

	public void unregisterEvent(RobotListener listener, String actionCommand,
			String type) {
		getListenerList(actionCommand, type).remove(listener);
	}

	protected List<RobotListener> getListenerList(String actionCommand,
			String type) {
		Map<String, List<RobotListener>> actionEventMap = null;
		List<RobotListener> listenerList = null;

		actionEventMap = eventMap.get(type);

		if (actionEventMap == null) {
			actionEventMap = new HashMap<String, List<RobotListener>>();
			eventMap.put(type, actionEventMap);
		}
		else {
			listenerList = actionEventMap.get(actionCommand);
		}

		if (listenerList == null) {
			listenerList = new ArrayList<RobotListener>();
			actionEventMap.put(actionCommand, listenerList);
		}

		return listenerList;
	}

	public void fireActionEvent(RobotGUIEvent event) {
		Map<String, List<RobotListener>> actionEventMap = eventMap
				.get(TYPE_ACTION);
		List<RobotListener> listenerList = actionEventMap.get(event
				.getActionCommand());

		for (Iterator<RobotListener> it = listenerList.iterator(); it.hasNext();) {
			(it.next()).execute(event);
		}
	}

}
