package br.edu.unifacisa.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.unifacisa.ecommerce.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	 List<Cart> findAllByUserIdOrderByCreatedDateDesc(Integer userId);
}
