package com.bciexercise.exercise.enumeration;

public enum Errors {

	EMAILALREADYEXISTS("El correo ya esta registrado", 1000),
	ARGUMENTNOTVALIDEXCEPTION("Alguno de los parametros no es correcto", 2000),
	USERNOTFOUNDEXCEPTION("Usuario invalido", 3000),
	GENERALEXCEPTION("Exception occured inside API", 9000);

	private String detail;
	private int code;

	Errors(String detail, int code) {
		this.detail = detail;
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
