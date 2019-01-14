package eu.pracenjetroskova.app.service;

import java.util.List;
import java.util.Optional;

import eu.pracenjetroskova.app.model.CommonBalance;

public interface CommonBalanceService {

	CommonBalance createCommon(CommonBalance commonBalance);
	Optional<CommonBalance> findById(Long id);
	CommonBalance updateCommon (CommonBalance commonBalance);
	List<CommonBalance> findAllById(List<Long> ids);
}
