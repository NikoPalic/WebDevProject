package eu.pracenjetroskova.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pracenjetroskova.app.model.Users;

public interface UsersRepository extends JpaRepository<Users,UUID> {

	Optional<Users> findByUsername(String username);
	

}
