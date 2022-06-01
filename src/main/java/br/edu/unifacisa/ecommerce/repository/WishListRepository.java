package br.edu.unifacisa.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.unifacisa.ecommerce.model.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer>{
	
	
	
}
