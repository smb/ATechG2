package de.adv.atech.roboter.gui.exceptions;

import de.adv.atech.roboter.commons.exceptions.AbstractException;

public class IllegalSyntaxException extends AbstractException {

	/**
	 * 
	 */
	public IllegalSyntaxException() {
	}

	/**
	 * @param message
	 */
	public IllegalSyntaxException(String message) {
		super(message);
	}

	/**
	 * @param message
	 */
	public IllegalSyntaxException(String message, int codeZeile) {
		super("Zeile " + codeZeile + ": " + message);
	}

	/**
	 * @param cause
	 */
	public IllegalSyntaxException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IllegalSyntaxException(String message, Throwable cause) {
		super(message, cause);
	}
}
