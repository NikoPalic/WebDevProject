package eu.pracenjetroskova.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.CustomUserDetails;
import eu.pracenjetroskova.app.model.Users;
import eu.pracenjetroskova.app.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> optionalUsers =usersRepository.findByUsername(username);
		
		optionalUsers
				.orElseThrow(()-> new UsernameNotFoundException("Korisničko ime nije pronađeno"));
		return optionalUsers
				.map(CustomUserDetails::new).get();
	}
	
	

}
