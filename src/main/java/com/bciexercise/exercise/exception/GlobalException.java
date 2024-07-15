package com.bciexercise.exercise.exception;

public class GlobalException extends Exception {

	private static final long serialVersionUID = -6278317495959233588L;
	
	private int code;

	public GlobalException(final String detail, final int code) {
		super(detail);
		this.code = code;
	}
	public int getCode() {
		return code;
	}

}
