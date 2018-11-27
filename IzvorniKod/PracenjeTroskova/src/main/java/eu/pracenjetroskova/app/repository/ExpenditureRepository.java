package eu.pracenjetroskova.app.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pracenjetroskova.app.model.Expenditure;

public interface ExpenditureRepository extends JpaRepository<Expenditure,Long> {

}