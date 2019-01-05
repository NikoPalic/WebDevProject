package eu.pracenjetroskova.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.Revenue;
import eu.pracenjetroskova.app.model.User;
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



	@Override
	public List<Revenue> findByUserID(User userID) {
		return revenueRepository.findByUserID(userID);
	}



	@Override
	public void deleteRevenue(Long id) {
		revenueRepository.deleteById(id);
		
	}



	@Override
	public Optional<Revenue> findById(Long id) {
		return revenueRepository.findById(id);
	}



	@Override
	public void saveRevenue(Revenue revenue) {
		revenueRepository.save(revenue);
		
	}

}
