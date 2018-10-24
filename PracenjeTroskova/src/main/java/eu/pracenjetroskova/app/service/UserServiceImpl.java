package eu.pracenjetroskova.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.Users;
import eu.pracenjetroskova.app.repository.UsersRepository;

@Service
public class UserServiceImpl implements UserService {
	
	
	private final UsersRepository usersRepository;
	
	
	@Autowired
	public UserServiceImpl(UsersRepository usersRepository) {
		super();
		this.usersRepository = usersRepository;
	}



	@Override
	public void insertUser(Users user) {
		usersRepository.save(user);
	}
	
	

}
