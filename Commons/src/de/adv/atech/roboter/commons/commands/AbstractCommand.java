package de.adv.atech.roboter.commons.commands;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;
import de.adv.atech.roboter.commons.exceptions.CommandException;
import de.adv.atech.roboter.commons.interfaces.Command;

/**
 * 
 * @author sbu
 * 
 */
public abstract class AbstractCommand implements Command, Serializable,
		Cloneable {

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
			/*
			 * sb / 15.02.2009 / Ausgelagert in eigene Methode
			 */
			classField = resolveClassField(tmpField);

			returnMap.put(Enum.valueOf(enumClass, tmpField.getName()),
					classField);
		}

		return returnMap;
	}

	/**
	 * 
	 * @param field
	 * @return
	 */
	public Class getParameterClass(Field field) throws CommandException {
		Class parameter = null;

		if (field != null) {
			Field tmpField = resolveClassField(field);
			parameter = tmpField.getType();
		}

		return parameter;
	}

	/**
	 * 
	 * @param field
	 * @return
	 */
	public Class getParameterClass(String fieldName) throws CommandException {
		return getParameterClass(resolveClassField(fieldName));
	}

	/**
	 * Wrapper fuer resolveClassField(String fieldName)
	 * 
	 * @see resolveClassField(String fieldName)
	 * @param field
	 * @return
	 * @throws CommandException
	 */
	public Field resolveClassField(Field field) throws CommandException {
		Field classField = null;

		if (field.isEnumConstant()) {
			classField = resolveClassField(field.getName());
		}
		else {
			classField = field;
		}

		return classField;
	}

	/**
	 * Enum-Fieldname in ein Class Field aufloesen
	 * 
	 * @param fieldName
	 * @return
	 * @throws CommandException
	 */
	public Field resolveClassField(String fieldName) throws CommandException {
		Field classField = null;

		try {
			classField = this.child.getClass().getDeclaredField(fieldName);
		}
		catch (SecurityException e) {
			throw new CommandException(e, this);
		}
		catch (NoSuchFieldException e) {
			throw new CommandException(e, this);
		}

		return classField;
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
			}
			catch (IllegalArgumentException e) {
				throw new CommandException(e, this);
			}
			catch (IllegalAccessException e) {
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
			}
			catch (IllegalArgumentException e) {
				throw new CommandException(e, this);
			}
			catch (IllegalAccessException e) {
				throw new CommandException(e, this);
			}
		}
	}

	public Command clone() {
		Command clone;
		try {
			clone = (Command) super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
		return clone;
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
				}
				else {
					sb.append(tmpFieldContent.toString());
				}

			}
			catch (IllegalAccessException e) {
				sb.append("*unknown*");
			}
			catch (Exception e) {
				sb.append("*internal java error*");
			}

			if (i != declaredFields.length - 1) {
				sb.append(", ");
			}
		}

		sb.append("]]");

		return sb.toString();
	}

	/**
	 * 
	 * @return
	 */
	private String getCommandReferenceSyntax() {

		StringBuffer localRef = new StringBuffer(100);

		localRef.append("<b>");
		localRef.append(this.getCommandName());
		localRef.append("</b><br>");

		try {

			if (this.getParameters().size() > 0) {

				for (Iterator<Entry<Enum<?>, Field>> it = this.getParameters()
						.entrySet().iterator(); it.hasNext();) {
					Entry<Enum<?>, Field> entry = it.next();

					localRef.append(" - "
							+ entry.getKey()
							+ " ("
							+ this.getParameterClass(entry.getValue())
									.getSimpleName() + ")");

					localRef.append("<br>");
				}
			}
			else {
				localRef.append("[Keine Parameter vorhanden]");
			}

			localRef.append("<br>");
		}
		catch (CommandException ex) {
			localRef.append("- Parameter konnten nicht gelesen werden: "
					+ ex.getMessage());
			localRef.append("<br>");
		}

		return localRef.toString();
	}

	private String getEditorSyntax() {

		StringBuffer localRef = new StringBuffer(100);

		localRef.append(this.getCommandName());

		try {
			if (this.getParameters().size() > 0) {

				localRef.append(" (");

				for (Iterator<Entry<Enum<?>, Field>> it = this.getParameters()
						.entrySet().iterator(); it.hasNext();) {
					Entry<Enum<?>, Field> entry = it.next();

					String paramType = this.getParameterClass(entry.getValue())
							.getSimpleName();

					String paramName = entry.getKey().toString();

					localRef.append(paramType);
					localRef.append(paramName);

					if (it.hasNext()) {
						localRef.append(",");
					}
				}

				localRef.append(")");
			}
		}
		catch (CommandException ex) {
			ControllerManager.message(Constant.MESSAGE_TYPE_ERROR,
					"Parameter konnten nicht gelesen werden: "
							+ ex.getMessage());
		}

		return localRef.toString();
	}

	public String getSyntax(int style) {
		String syntaxString = null;

		if (style == Constant.COMMAND_SYNTAX_STYLE_EDITOR) {
			syntaxString = getEditorSyntax();
		}
		if (style == Constant.COMMAND_SYNTAX_STYLE_COMMAND_REFERENCE) {
			syntaxString = getCommandReferenceSyntax();
		}

		return syntaxString;
	}
}
