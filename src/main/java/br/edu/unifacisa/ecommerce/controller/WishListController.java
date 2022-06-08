package br.edu.unifacisa.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unifacisa.ecommerce.comnon.ApiResponse;
import br.edu.unifacisa.ecommerce.dto.ProductDto;
import br.edu.unifacisa.ecommerce.model.Product;
import br.edu.unifacisa.ecommerce.model.User;
import br.edu.unifacisa.ecommerce.model.WishList;
import br.edu.unifacisa.ecommerce.sevice.AuthenticationService;
import br.edu.unifacisa.ecommerce.sevice.WishListService;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

	@Autowired
	WishListService wishListService;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, @RequestParam("token") String token){
		
		authenticationService.authenticate(token);
		
		User user = authenticationService.getUser(token);
		
		WishList wishList = new WishList(user, product);
		
		wishListService.createWishList(wishList);
		
		ApiResponse apiResponse = new ApiResponse(true, "Added to wishlis");
		
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
		
	}
	

	@GetMapping("/{token}")
	public ResponseEntity<List<ProductDto>> getWishList(@PathVariable String token) {
		
		authenticationService.authenticate(token);
		
		User user = authenticationService.getUser(token);
		
		List<ProductDto> wishListForUser = wishListService.getWishListForUser(user);
	
		return new ResponseEntity<>(wishListForUser, HttpStatus.OK);
	}

	
	 
}