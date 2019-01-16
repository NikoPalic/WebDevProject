package eu.pracenjetroskova.app.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.CustomUserDetails;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private String role="USER";
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
	    try {
	        Optional<User> user = userRepository.findByUsername(username);
	        if (!user.isPresent()) {
	            throw new UsernameNotFoundException(
	              "No user found with username: " + username);
	        }
	         
	        return new org.springframework.security.core.userdetails.User(
	          user.get().getUsername(), 
	          user.get().getPassword(), 
	          user.get().getActive(), 
	          true,true,true,
	          getAuthorities());
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }

}

	private Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(this.role));
        return authorities;
	}
}