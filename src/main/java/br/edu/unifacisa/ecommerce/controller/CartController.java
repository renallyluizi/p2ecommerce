package br.edu.unifacisa.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.unifacisa.ecommerce.comnon.ApiResponse;
import br.edu.unifacisa.ecommerce.dto.cart.AddToCartDto;
import br.edu.unifacisa.ecommerce.dto.cart.CartDto;
import br.edu.unifacisa.ecommerce.exceptions.AuthenticationFailException;
import br.edu.unifacisa.ecommerce.exceptions.CartItemNotExistException;
import br.edu.unifacisa.ecommerce.exceptions.ProductNotExistException;
import br.edu.unifacisa.ecommerce.model.Product;
import br.edu.unifacisa.ecommerce.sevice.AuthenticationService;
import br.edu.unifacisa.ecommerce.sevice.CartService;
import br.edu.unifacisa.ecommerce.sevice.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
			@RequestParam("token") String token) throws AuthenticationFailException, ProductNotExistException {
		authenticationService.authenticate(token);

		int userId = authenticationService.getUser(token).getId();

		cartService.addToCart(addToCartDto, userId);

		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);

	}

	@GetMapping("/")
	public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token)
			throws AuthenticationFailException {
		authenticationService.authenticate(token);
		int userId = authenticationService.getUser(token).getId();
		CartDto cartDto = cartService.listCartItems(userId);
		return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
	}

	@PutMapping("/update/{cartItemId}")
	public ResponseEntity<ApiResponse> updateCartItem(@RequestBody @Valid AddToCartDto cartDto,
			@RequestParam("token") String token) throws AuthenticationFailException, ProductNotExistException {
		authenticationService.authenticate(token);
		int userId = authenticationService.getUser(token).getId();

		Product product = productService.getProductById(cartDto.getProductId());

		cartService.updateCartItem(cartDto, userId, product);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") int itemID,
			@RequestParam("token") String token) throws AuthenticationFailException, CartItemNotExistException {
		authenticationService.authenticate(token);

		int userId = authenticationService.getUser(token).getId();

		cartService.deleteCartItem(itemID, userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
	}
}
