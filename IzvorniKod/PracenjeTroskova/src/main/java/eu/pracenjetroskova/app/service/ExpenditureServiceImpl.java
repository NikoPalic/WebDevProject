package eu.pracenjetroskova.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.Expenditure;
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

}
