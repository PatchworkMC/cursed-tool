package com.patchworkmc.curse;

/**
 * Base class for all exceptions within the CurseApi.
 */
public class CurseException extends Exception {
	/**
	 * Creates a new CurseException with the specified message.
	 *
	 * @param message The message of the exception
	 */
	public CurseException(String message) {
		super(message);
	}

	/**
	 * Creates a new CurseException which was caused by another exception
	 * and adds a message to it.
	 *
	 * @param message The message of the curse exception
	 * @param cause   The cause this exception was generated
	 */
	public CurseException(String message, Throwable cause) {
		super(message, cause);
	}
}
