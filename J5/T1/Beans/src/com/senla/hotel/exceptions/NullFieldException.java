package com.senla.hotel.exceptions;

public class NullFieldException extends Exception {


	private static final long serialVersionUID = 5095104434279780261L;

	public NullFieldException() {
		super("Not all parameters are setted");
	}
}
