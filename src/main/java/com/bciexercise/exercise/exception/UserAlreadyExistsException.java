package com.bciexercise.exercise.exception;

public class UserAlreadyExistsException extends GlobalException {

	private static final long serialVersionUID = -8903753674189513806L;

	public UserAlreadyExistsException(final String detail, final int code) {
		super(detail, code);
	}
	
}
