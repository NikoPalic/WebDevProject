package eu.pracenjetroskova.app.service;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.dto.UserDto;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.repository.RoleRepository;
import eu.pracenjetroskova.app.repository.UserRepository;
import eu.pracenjetroskova.app.validation.EmailExistsException;

@Service
public class UserServiceImpl implements UserService {
	
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}



	@Override
	public void insertUser(User user) {
		userRepository.save(user);
	}
	
	@Transactional
    @Override
    public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException {
         
        if (emailExist(accountDto.getEmail())) {   
            throw new EmailExistsException(
              "There is an account with that email address: " + accountDto.getEmail());
        }
        User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setName(accountDto.getName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(accountDto.getPassword());
        user.setEmail(accountDto.getEmail());
        user.setRoles(Arrays.asList(roleRepository.findByRole("USER")));
        return userRepository.save(user);       
    }
    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

}
