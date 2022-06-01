package br.edu.unifacisa.ecommerce.exceptions;

public class AuthenticationFailException extends IllegalArgumentException {
	
	public AuthenticationFailException(String msg) {
		super(msg);
	}

	
}
