package eu.pracenjetroskova.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.Savings;
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

}