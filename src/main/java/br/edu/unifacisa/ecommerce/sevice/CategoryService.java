package br.edu.unifacisa.ecommerce.sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unifacisa.ecommerce.model.Category;
import br.edu.unifacisa.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public List<Category> listCategory() {
		return categoryRepository.findAll();
	}
	
	public void createCategory(Category category) {
		categoryRepository.save(category);
			
	}
	
	public Category readCategory(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}

	public Optional<Category> readCategory(Integer categoryId) {
		return categoryRepository.findById(categoryId);
	}

	public void updateCategory(Integer categoryID, Category newCategory) {
		Category category = categoryRepository.findById(categoryID).get();
		category.setCategoryName(newCategory.getCategoryName());
		category.setDescription(newCategory.getDescription());
		category.setImageUrl(newCategory.getImageUrl());
		categoryRepository.save(category);
	}
	
	public boolean findById(int categoryId) {
		return categoryRepository.findById(categoryId).isPresent();
	}
	
}
