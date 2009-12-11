package de.adv.atech.roboter.gui.exceptions;

public class IllegalSyntaxException extends Exception {

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
