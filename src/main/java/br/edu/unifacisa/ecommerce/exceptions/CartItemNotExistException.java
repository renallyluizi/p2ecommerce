package br.edu.unifacisa.ecommerce.exceptions;

public class CartItemNotExistException extends IllegalArgumentException {
	public CartItemNotExistException(String msg) {
		super(msg);
	}
}
