package org.kp.tpmg.ttg.clinicianconnect.exception;

public class PhoneBookServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhoneBookServiceException() {
		super();
	}
	public PhoneBookServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public PhoneBookServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	public PhoneBookServiceException(String message) {
		super(message);
	}
	public PhoneBookServiceException(Throwable cause) {
		super(cause);
	}
}
