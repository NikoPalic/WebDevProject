package eu.pracenjetroskova.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.pracenjetroskova.app.model.Log;
import eu.pracenjetroskova.app.repository.LogRepository;

@Service
public class LogServiceImpl implements LogService{

	private final LogRepository logRepository;

	@Autowired
	public LogServiceImpl(LogRepository logRepository) {
		super();
		this.logRepository = logRepository;
	}

	@Override
	public Log createLog(Log log) {
		return logRepository.save(log);
	}
	
	
	
}
