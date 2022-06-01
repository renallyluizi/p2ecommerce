package br.edu.unifacisa.ecommerce.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unifacisa.ecommerce.repository.WishListRepository;

@Service
public class WishListService {

	@Autowired
	WishListRepository wishListRepository;
}
