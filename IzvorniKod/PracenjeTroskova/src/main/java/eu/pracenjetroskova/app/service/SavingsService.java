package eu.pracenjetroskova.app.service;

import java.util.List;

import eu.pracenjetroskova.app.model.Savings;
import eu.pracenjetroskova.app.model.User;

public interface SavingsService {

	void createSavings(Savings savings);
	List<Savings> findByUserID (User userID);
}
