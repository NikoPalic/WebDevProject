package eu.pracenjetroskova.app.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pracenjetroskova.app.model.Expenditure;
import eu.pracenjetroskova.app.model.User;

public interface ExpenditureRepository extends JpaRepository<Expenditure,Long> {

	List<Expenditure> findByUserID (User userID);
	Optional<Expenditure> findById (Long id);
}