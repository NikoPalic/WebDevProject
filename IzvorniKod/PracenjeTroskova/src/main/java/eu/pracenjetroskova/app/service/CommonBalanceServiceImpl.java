package eu.pracenjetroskova.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.CommonBalance;
import eu.pracenjetroskova.app.repository.CommonBalanceRepository;

@Service
public class CommonBalanceServiceImpl implements CommonBalanceService{
	
	private final CommonBalanceRepository commonBalanceRepository;

	@Autowired
	public CommonBalanceServiceImpl(CommonBalanceRepository commonBalanceRepository) {
		super();
		this.commonBalanceRepository = commonBalanceRepository;
	}

	@Override
	public CommonBalance createCommon(CommonBalance commonBalance) {
		return commonBalanceRepository.save(commonBalance);
	}

	@Override
	public Optional<CommonBalance> findById(Long id) {
		return commonBalanceRepository.findById(id);
	}

	@Override
	public CommonBalance updateCommon(CommonBalance commonBalance) {
		return commonBalanceRepository.saveAndFlush(commonBalance);
	}

	@Override
	public List<CommonBalance> findAllById(List<Long> ids) {
		return commonBalanceRepository.findAllById(ids);
	}

	@Override
	public void deleteBallance(CommonBalance commonBalance) {
		commonBalanceRepository.delete(commonBalance);
		
	}
	
	

}
