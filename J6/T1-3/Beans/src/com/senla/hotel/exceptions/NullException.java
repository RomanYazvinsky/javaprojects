package com.senla.hotel.exceptions;

public class NullException extends Exception {


	private static final long serialVersionUID = 5095104434279780261L;

	public NullException() {
		super("Some var is null");
	}
}
