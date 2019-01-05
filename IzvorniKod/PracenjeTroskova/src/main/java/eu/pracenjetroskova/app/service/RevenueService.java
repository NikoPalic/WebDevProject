package eu.pracenjetroskova.app.service;

import java.util.List;

import eu.pracenjetroskova.app.model.Revenue;
import eu.pracenjetroskova.app.model.User;

public interface RevenueService {

	void createRevenue(Revenue revenue);
	List<Revenue> findByUserID(User userID);
}
