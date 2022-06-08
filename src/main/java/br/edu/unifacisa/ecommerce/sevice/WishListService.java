package br.edu.unifacisa.ecommerce.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.edu.unifacisa.ecommerce.dto.ProductDto;
import br.edu.unifacisa.ecommerce.model.User;
import br.edu.unifacisa.ecommerce.model.WishList;
import br.edu.unifacisa.ecommerce.repository.WishListRepository;

@Service
public class WishListService {

	@Autowired
	WishListRepository wishListRepository;

	@Autowired
	ProductService productService;
	
	public void createWishList(WishList wishList) {
		wishListRepository.save(wishList);
		
	}
	
	public List<ProductDto> getWishListForUser(User user){
		final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
		List<ProductDto> productsDto = new ArrayList<>();
		for (WishList wishList: wishLists) {
			productsDto.add(productService.getProductDto(wishList.getProduct()));
		}
		
		return productsDto;
	}
}
