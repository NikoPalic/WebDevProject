package eu.pracenjetroskova.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.Savings;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.repository.SavingsRepository;

@Service
public class SavingsServiceImpl implements SavingsService{
	
	private final SavingsRepository savingsRepository;
	
	
	@Autowired
	public SavingsServiceImpl(SavingsRepository savingsRepository) {
		super();
		this.savingsRepository = savingsRepository;
	}



	@Override
	public void createSavings(Savings savings) {
		savingsRepository.save(savings);
		
	}



	@Override
	public List<Savings> findByUserID(User userID) {
		return savingsRepository.findByUserID(userID);
	}



	@Override
	public void deleteSavings(Long id) {
		savingsRepository.deleteById(id);
		
	}



	@Override
	public Optional<Savings> findById(Long id) {
		return savingsRepository.findById(id);
	}



	@Override
	public void saveRevenue(Savings savings) {
		savingsRepository.save(savings);
		
	}

}