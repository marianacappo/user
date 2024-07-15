package com.bciexercise.exercise.exception;

public class UserNotFoundException extends GlobalException {

	private static final long serialVersionUID = -8634050402483247035L;

	public UserNotFoundException(final String detail, final int code) {
		super(detail, code);
	}
	
}
