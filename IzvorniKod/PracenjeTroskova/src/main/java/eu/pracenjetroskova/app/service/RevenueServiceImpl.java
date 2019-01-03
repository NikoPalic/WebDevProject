package eu.pracenjetroskova.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.Revenue;
import eu.pracenjetroskova.app.repository.RevenueRepository;

@Service
public class RevenueServiceImpl implements RevenueService{
	
	private final RevenueRepository revenueRepository;
	
	
	@Autowired
	public RevenueServiceImpl(RevenueRepository revenueRepository) {
		super();
		this.revenueRepository = revenueRepository;
	}



	@Override
	public void createRevenue(Revenue revenue) {
		revenueRepository.save(revenue);
		
	}

}
