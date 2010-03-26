/**
 * 
 */
package de.adv.atech.roboter.commons.exceptions;

import java.lang.reflect.Field;

import de.adv.atech.roboter.commons.Constant;
import de.adv.atech.roboter.commons.ControllerManager;

/**
 * @author sb
 * 
 */
public abstract class AbstractException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5638410199255361920L;

	/**
	 * 
	 */
	public AbstractException() {
	}

	/**
	 * @param message
	 */
	public AbstractException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public AbstractException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public AbstractException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Alle lesbaren Informationen ausgeben - Prototyp fuer die Entwicklung
	 * TODO: Besser machen! Und "komprimierter", Abhaengig vom LoggingLevel etc.
	 */
	public void dumpException() {
		StringBuilder sb = new StringBuilder(200);

		String className = this.getClass().getSimpleName();
		Field[] declaredFields = this.getClass().getDeclaredFields();

		sb.append("Exception: ");
		sb.append(className);
		sb.append("\n");

		sb.append("Message:   ");

		sb.append(this.getMessage());

		sb.append("\n");

		sb.append("Fields:");

		for (int i = 0; i < declaredFields.length; i++) {
			Field tmpField = declaredFields[i];
			String tmpFieldName = tmpField.getName();
			String tmpFieldType = tmpField.getType().getName();

			sb.append("\t   - ");
			sb.append(tmpFieldName);
			sb.append(": ");
			try {
				Object tmpObject = tmpField.get(this);
				if (tmpObject == null) {
					sb.append("*NULL*");
				}
				else {
					sb.append(tmpObject.toString());
				}
			}
			catch (IllegalAccessException e) {
				sb.append("[");
				sb.append(tmpFieldType);
				sb.append(": [*unknown/private*]]");
			}
			catch (Exception e) {
				// RICHTIG FAIL! Java kaputt..
				e.printStackTrace();
				sb.append("Java Kaputt! - " + e.getMessage());
			}
			sb.append("\n");
		}

		// TODO: Logger benutzen!
		ControllerManager.message(Constant.MESSAGE_TYPE_ERROR, sb.toString());

	}
}
