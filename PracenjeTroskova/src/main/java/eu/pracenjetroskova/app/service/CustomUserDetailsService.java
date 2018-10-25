package eu.pracenjetroskova.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.CustomUserDetails;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUsers =userRepository.findByUsername(username);
		
		optionalUsers
				.orElseThrow(()-> new UsernameNotFoundException("Korisničko ime nije pronađeno"));
		return optionalUsers
				.map(CustomUserDetails::new).get();
	}
	
	

}
