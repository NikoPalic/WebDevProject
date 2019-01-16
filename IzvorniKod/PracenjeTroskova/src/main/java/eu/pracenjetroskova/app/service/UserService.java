package eu.pracenjetroskova.app.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import eu.pracenjetroskova.app.dto.UserDto;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.validation.EmailExistsException;

public interface UserService {
	
	void insertUser(User user);
	User registerNewUserAccount(UserDto accountDto) throws EmailExistsException;
	Optional<User> findByUsername(String username);
	void updateUser(User user);
	boolean emailExist(String email);
	User findByEmail(String email);
	PasswordEncoder passwordEncoder();
	boolean usernameExist(String username);

}
