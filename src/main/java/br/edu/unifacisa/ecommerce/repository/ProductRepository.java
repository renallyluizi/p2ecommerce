package br.edu.unifacisa.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.unifacisa.ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
