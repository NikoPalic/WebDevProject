package eu.pracenjetroskova.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{
	
	VerificationToken findByToken(String token);
	 
    VerificationToken findByUser(User user);

}
