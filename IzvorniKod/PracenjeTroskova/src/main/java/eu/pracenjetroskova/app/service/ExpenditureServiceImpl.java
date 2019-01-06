package eu.pracenjetroskova.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.Expenditure;
import eu.pracenjetroskova.app.model.User;
import eu.pracenjetroskova.app.repository.ExpenditureRepository;

@Service
public class ExpenditureServiceImpl implements ExpenditureService{
	
	private final ExpenditureRepository expenditureRepository;
	
	
	@Autowired
	public ExpenditureServiceImpl(ExpenditureRepository expenditureRepository) {
		super();
		this.expenditureRepository = expenditureRepository;
	}



	@Override
	public void createExpenditure(Expenditure expenditure) {
		expenditureRepository.save(expenditure);
		
	}



	@Override
	public List<Expenditure> findByUserID(User userID) {
		return expenditureRepository.findByUserID(userID);
	}



	@Override
	public void deleteExpenditure(Long id) {
		expenditureRepository.deleteById(id);
		
	}



	@Override
	public Optional<Expenditure> findById(Long id) {
		return expenditureRepository.findById(id);
	}



	@Override
	public void saveExpenditure(Expenditure expenditure) {
		expenditureRepository.save(expenditure);
		
	}

}
