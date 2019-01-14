package eu.pracenjetroskova.app.service;

import java.util.List;

import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.model.UsersCommonBalance;

public interface UsersCommonBalanceService {

	UsersCommonBalance createUCB(UsersCommonBalance usersCommonBalance);
	UsersCommonBalance updateUCB(UsersCommonBalance usersCommonBalance);
	List<UsersCommonBalance> findByUser(User user);
}
