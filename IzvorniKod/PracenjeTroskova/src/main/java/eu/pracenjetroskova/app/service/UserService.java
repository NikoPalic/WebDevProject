package eu.pracenjetroskova.app.service;

import java.util.Optional;

import eu.pracenjetroskova.app.dto.UserDto;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.validation.EmailExistsException;

public interface UserService {
	
	void insertUser(User user);
	User registerNewUserAccount(UserDto accountDto) throws EmailExistsException;
	Optional<User> findByUsername(String username);
	void updateUser(User user);

}
