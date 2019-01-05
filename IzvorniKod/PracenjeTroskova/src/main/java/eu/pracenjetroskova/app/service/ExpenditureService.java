package eu.pracenjetroskova.app.service;

import java.util.List;

import eu.pracenjetroskova.app.model.Expenditure;
import eu.pracenjetroskova.app.model.User;

public interface ExpenditureService {

	void createExpenditure(Expenditure expenditure);
	List<Expenditure> findByUserID (User userID);
}
