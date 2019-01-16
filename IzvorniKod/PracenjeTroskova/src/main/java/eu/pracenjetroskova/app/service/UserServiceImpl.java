package eu.pracenjetroskova.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.dto.UserDto;
import eu.pracenjetroskova.app.model.Category;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.model.VerificationToken;
import eu.pracenjetroskova.app.repository.UserRepository;
import eu.pracenjetroskova.app.repository.VerificationTokenRepository;
import eu.pracenjetroskova.app.validation.EmailExistsException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	private final UserRepository userRepository;
	
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
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
        if (usernameExist(accountDto.getUsername())) {   
            throw new EmailExistsException(
              "There is an account with that username: " + accountDto.getUsername());
        }
        User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setName(accountDto.getName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder().encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setFunds(0.0);
        user.setActive(false);
        List<Category> kategorije = new ArrayList<>();
        for (int i=10000; i<10012;i++) {
        	Category temp=categoryService.findById(Long.valueOf(i));
        	if(temp!=null) {
        		kategorije.add(temp);
        	}
        }
        user.setCategories(kategorije);
        return userRepository.save(user);       
    }
	@Override
    public boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }



	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}


	@Override
	public void updateUser(User user) {
		userRepository.saveAndFlush(user);
		
	}


	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Override
	public boolean usernameExist(String username) {
		Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return true;
        }
        return false;
	}


	@Override
	public User getUser(String verificationToken) {
		User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
	}


	@Override
	public void createVerificationToken(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
		
	}


	@Override
	public VerificationToken getVerificationToken(String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}

	
	
}
