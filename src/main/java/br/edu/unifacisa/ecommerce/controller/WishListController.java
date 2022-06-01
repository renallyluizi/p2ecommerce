package br.edu.unifacisa.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unifacisa.ecommerce.comnon.ApiResponse;
import br.edu.unifacisa.ecommerce.model.Product;
import br.edu.unifacisa.ecommerce.sevice.WishListService;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

	@Autowired
	WishListService wishListService;
	
	public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, @RequestParam("token") String token){
		return null;
		
	}
}