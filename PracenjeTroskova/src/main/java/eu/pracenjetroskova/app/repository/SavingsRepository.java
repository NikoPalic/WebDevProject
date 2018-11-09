package eu.pracenjetroskova.app.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pracenjetroskova.app.model.Savings;

public interface SavingsRepository extends JpaRepository<Savings,Long> {

}