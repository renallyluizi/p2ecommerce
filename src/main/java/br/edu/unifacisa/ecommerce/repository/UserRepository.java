package br.edu.unifacisa.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.unifacisa.ecommerce.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findAll();

    User findByEmail(String email);

    User findUserByEmail(String email);
}
