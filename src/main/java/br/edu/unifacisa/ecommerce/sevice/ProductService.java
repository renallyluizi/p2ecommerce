package br.edu.unifacisa.ecommerce.sevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unifacisa.ecommerce.dto.ProductDto;
import br.edu.unifacisa.ecommerce.exceptions.ProductNotExistException;
import br.edu.unifacisa.ecommerce.model.Category;
import br.edu.unifacisa.ecommerce.model.Product;
import br.edu.unifacisa.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public void createProduct(ProductDto productDto, Category category) {
		Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        productRepository.save(product);
				
		
	}

	public ProductDto getProductDto(Product product) {
		ProductDto productDto = new ProductDto();
		productDto.setCategoryId(product.getCategory().getId());
		productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setPrice(product.getPrice());
        productDto.setName(product.getName());
        productDto.setId(product.getId());
        return productDto;
	}
	
	public List<ProductDto> getAllProduct() {
		List<Product> allProducts = productRepository.findAll();
		List<ProductDto> productDtos = new ArrayList<>();
		for(Product  product: allProducts) {
			productDtos.add(getProductDto(product));
		} 
		return productDtos;
	}

	public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (!optionalProduct.isPresent()) {
			throw new Exception("product not present");
		}
		Product product = optionalProduct.get(); 
		productDto.setDescription(productDto.getDescription());
        productDto.setImageUrl(productDto.getImageUrl());
        productDto.setPrice(productDto.getPrice());
        productDto.setName(productDto.getName());
        productRepository.save(product);
	}

	public Product getProductById(Long productId) throws ProductNotExistException {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (!optionalProduct.isPresent() )
			throw new ProductNotExistException("Product id is invalid " + productId);
		
		// TODO Auto-generated method stub
		return optionalProduct.get();
	}
	
	
	
}
