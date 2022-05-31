package br.edu.unifacisa.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unifacisa.ecommerce.model.AuthenticationToken;
import br.edu.unifacisa.ecommerce.model.User;

public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {

	AuthenticationToken findByUser(User user);
}

