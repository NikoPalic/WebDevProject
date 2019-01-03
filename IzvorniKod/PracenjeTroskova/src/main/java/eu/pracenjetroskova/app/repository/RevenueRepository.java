package eu.pracenjetroskova.app.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pracenjetroskova.app.model.Revenue;
import eu.pracenjetroskova.app.model.User;

public interface RevenueRepository extends JpaRepository<Revenue,Long> {

	List<Revenue> findByUserID(User userID);

}