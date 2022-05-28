package br.edu.unifacisa.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unifacisa.ecommerce.comnon.ApiResponse;
import br.edu.unifacisa.ecommerce.dto.ProductDto;
import br.edu.unifacisa.ecommerce.model.Category;
import br.edu.unifacisa.ecommerce.repository.CategoryRepository;
import br.edu.unifacisa.ecommerce.sevice.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	CategoryRepository categoryRepository;

	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) {
		Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
		if (!optionalCategory.isPresent()) {
			return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
		}

		productService.createProduct(productDto, optionalCategory.get());
		return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
	}

	
	@GetMapping("/")
	public ResponseEntity<List<ProductDto>> getProducts() {
		List<ProductDto> products = productService.getAllProduct();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	
	@GetMapping("/update/{productId}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDto productDto) throws Exception {
		Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
		if (!optionalCategory.isPresent()) {
			return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
		}
		productService.updateProduct(productDto, productId);
		return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);

}
}
