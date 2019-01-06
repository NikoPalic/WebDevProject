package eu.pracenjetroskova.app.service;

import java.util.List;
import java.util.Optional;

import eu.pracenjetroskova.app.model.Expenditure;
import eu.pracenjetroskova.app.model.User;

public interface ExpenditureService {

	void createExpenditure(Expenditure expenditure);
	List<Expenditure> findByUserID (User userID);
	void deleteExpenditure (Long id);
	Optional<Expenditure> findById(Long id);
	void saveExpenditure(Expenditure expenditure);
}
