package eu.pracenjetroskova.app.service;

import eu.pracenjetroskova.app.dto.UserDto;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.validation.EmailExistsException;

public interface UserService {
	
	void insertUser(User user);
	User registerNewUserAccount(UserDto accountDto) throws EmailExistsException;

}
