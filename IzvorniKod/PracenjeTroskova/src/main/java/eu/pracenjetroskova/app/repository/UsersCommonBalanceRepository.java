package eu.pracenjetroskova.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.model.UsersCommonBalance;
import eu.pracenjetroskova.app.model.UsersCommonBalanceId;

public interface UsersCommonBalanceRepository extends JpaRepository<UsersCommonBalance, UsersCommonBalanceId>{

	List<UsersCommonBalance> findByUser(User user);
}
