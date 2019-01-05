package eu.pracenjetroskova.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.Category;
import eu.pracenjetroskova.app.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository categoryRepository;
	
	
	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}



	@Override
	public void createCategory(Category category) {
		categoryRepository.save(category);
		
	}



	@Override
	public Category findByname(String name) {
		return categoryRepository.findByname(name);
	}
	
	
}
