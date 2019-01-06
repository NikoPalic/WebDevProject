package eu.pracenjetroskova.app.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pracenjetroskova.app.model.Revenue;
import eu.pracenjetroskova.app.model.Savings;
import eu.pracenjetroskova.app.model.User;

public interface SavingsRepository extends JpaRepository<Savings,Long> {

	List<Savings> findByUserID (User userID);
	Optional<Savings> findById(Long id);
}