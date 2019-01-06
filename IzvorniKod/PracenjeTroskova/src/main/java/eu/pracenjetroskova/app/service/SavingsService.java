package eu.pracenjetroskova.app.service;

import java.util.List;
import java.util.Optional;


import eu.pracenjetroskova.app.model.Savings;
import eu.pracenjetroskova.app.model.User;

public interface SavingsService {

	void createSavings(Savings savings);
	List<Savings> findByUserID (User userID);
	void deleteSavings(Long id);
	Optional<Savings> findById(Long id);
	void saveRevenue (Savings savings);
}
