package eu.pracenjetroskova.app.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pracenjetroskova.app.model.UsersCommonBalance;

public interface ExpenditureRepository extends JpaRepository<UsersCommonBalance,Long> {

}