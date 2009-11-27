package de.adv.atech.roboter.commons.commands;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import de.adv.atech.roboter.commons.interfaces.Command;

public abstract class AbstractCommand implements Command {

	protected String commandName = null;

	Map<String, Field> parameterMap;

	protected AbstractCommand child;

	public AbstractCommand() {

	}

	public void init(AbstractCommand child) {
		this.child = child;
		this.parameterMap = new HashMap<String, Field>();
		this.parameterMap = findParameters();
	}

	public String getCommandName() {
		return this.commandName;
	}

	public Map<String, Field> getParameters() {
		return this.parameterMap;
	}

	private Map<String, Field> findParameters() {
		Map<String, Field> returnMap = new HashMap<String, Field>();

		Field[] fields = this.child.getClass().getFields();
		for (int i = 0; i < fields.length; i++) {
			Field tmpField = fields[i];
			returnMap.put(tmpField.getName(), tmpField);
		}

		return returnMap;
	}

	public Object getParameter(String name) {
		Object returnObject = null;

		if (this.parameterMap.containsKey(name)) {
			Field parameterField = this.getParameters().get(name);

			try {
				returnObject = parameterField.get(this.child);
			}
			catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnObject;
	}

	public void setParameter(String name, Object object) {
		if (this.parameterMap.containsKey(name)) {
			Field parameterField = this.getParameters().get(name);

			try {
				parameterField.set(this.child, object);
			}
			catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
