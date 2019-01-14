package eu.pracenjetroskova.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.model.UsersCommonBalance;
import eu.pracenjetroskova.app.repository.UsersCommonBalanceRepository;

@Service
public class UsersCommonBalanceServiceImpl implements UsersCommonBalanceService{

	private final UsersCommonBalanceRepository uCBRepository;

	@Autowired
	public UsersCommonBalanceServiceImpl(UsersCommonBalanceRepository uCBRepository) {
		super();
		this.uCBRepository = uCBRepository;
	}

	@Override
	public UsersCommonBalance createUCB(UsersCommonBalance usersCommonBalance) {
		return uCBRepository.save(usersCommonBalance);
	}

	@Override
	public List<UsersCommonBalance> findByUser(User user) {
		return uCBRepository.findByUser(user);
	}

	@Override
	public UsersCommonBalance updateUCB(UsersCommonBalance usersCommonBalance) {
		return uCBRepository.saveAndFlush(usersCommonBalance);
	}

	
	
	
	
}
