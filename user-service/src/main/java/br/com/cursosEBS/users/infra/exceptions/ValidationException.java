package br.com.cursosEBS.users.infra.exceptions;

public class ValidationException extends RuntimeException {

	public ValidationException(String message) {
		super(message);
	}

}
