package eu.pracenjetroskova.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pracenjetroskova.app.model.User;

public interface UserRepository extends JpaRepository<User,UUID> {

	Optional<User> findByUsername(String username);

	User findByEmail(String email);
	

}
