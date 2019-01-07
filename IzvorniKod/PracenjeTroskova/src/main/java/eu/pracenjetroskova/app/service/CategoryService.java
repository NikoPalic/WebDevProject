package eu.pracenjetroskova.app.service;

import eu.pracenjetroskova.app.model.Category;

public interface CategoryService {

	void createCategory (Category category);
	Category findByname(String name);
	Category findById(Long id);
	void deleteCategory (Long id);
}
