package de.adv.atech.roboter.commons.commands;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;

/**
 * 
 * @author sbu
 * 
 */
public abstract class AbstractCommand implements Command, Serializable {

	/**
   * 
   */
	protected String commandName = null;

	// Map<Enum<?>, Field> parameterMap;

	/**
   * 
   */
	private AbstractCommand child;

	private Class enumClass;

	/**
   * 
   */
	public AbstractCommand() {
		//
	}

	/**
	 * 
	 * @param child
	 */
	public void init(AbstractCommand child, Class enumClass) {
		this.child = child;
		this.enumClass = enumClass;

	}

	/**
   * 
   */
	public String getCommandName() {
		return this.commandName;
	}

	/**
   * 
   */
	public Map<Enum<?>, Field> getParameters() throws CommandException {
		return findParameters();
	}

	private Map<Enum<?>, Field> findParameters() throws CommandException {
		Map<Enum<?>, Field> returnMap = new LinkedHashMap<Enum<?>, Field>();

		Field[] enumFields = this.enumClass.getFields();

		for (int i = 0; i < enumFields.length; i++) {
			Field tmpField = enumFields[i];
			Field classField = null;

			// Passendes Feld zum enumFeld finden...

			try {
				// classField =
				// this.child.getClass().getField(tmpField.getName());
				// getDeclaredField benutzen -> Parameter koennen "protected"
				// sein
				classField = this.child.getClass().getDeclaredField(
						tmpField.getName());
			} catch (SecurityException e) {
				throw new CommandException(e, this);
			} catch (NoSuchFieldException e) {
				throw new CommandException(e, this);
			}

			returnMap.put(Enum.valueOf(enumClass, tmpField.getName()),
					classField);
		}

		return returnMap;
	}

	/**
   * 
   */
	public Object getParameter(Enum<?> name) throws CommandException {
		Object returnObject = null;

		Map<Enum<?>, Field> parameterMap = findParameters();

		if (parameterMap.containsKey(name)) {
			Field parameterField = parameterMap.get(name);

			try {
				returnObject = parameterField.get(this.child);
			} catch (IllegalArgumentException e) {
				throw new CommandException(e, this);
			} catch (IllegalAccessException e) {
				throw new CommandException(e, this);
			}
		}
		return returnObject;
	}

	/**
	 * 
	 * @param name
	 * @param object
	 */
	public void setParameter(Enum<?> name, Object object)
			throws CommandException {
		Map<Enum<?>, Field> parameterMap = findParameters();

		if (parameterMap.containsKey(name)) {
			Field parameterField = this.getParameters().get(name);

			try {
				parameterField.set(this.child, object);
			} catch (IllegalArgumentException e) {
				throw new CommandException(e, this);
			} catch (IllegalAccessException e) {
				throw new CommandException(e, this);
			}
		}
	}

	/**
	 * Alle lesbaren Informationen ausgeben - Prototyp fuer die Entwicklung
	 * TODO: Besser machen!
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String className = this.getClass().getName();
		Field[] declaredFields = this.getClass().getDeclaredFields();

		sb.append("[");
		sb.append(className);
		sb.append(": [");

		for (int i = 0; i < declaredFields.length; i++) {
			Field tmpField = declaredFields[i];
			String tmpFieldName = tmpField.getName();

			sb.append(tmpFieldName);
			sb.append(" (");
			sb.append(tmpField.getType().getName());
			sb.append(")");
			sb.append(": ");

			try {
				Object tmpFieldContent = tmpField.get(this);

				if (tmpFieldContent == null) {
					sb.append("*NULL*");
				} else {
					sb.append(tmpFieldContent.toString());
				}

			} catch (IllegalAccessException e) {
				sb.append("*unknown*");
			} catch (Exception e) {
				sb.append("*internal java error*");
			}

			if (i != declaredFields.length - 1) {
				sb.append(", ");
			}
		}

		sb.append("]]");

		return sb.toString();
	}
}
