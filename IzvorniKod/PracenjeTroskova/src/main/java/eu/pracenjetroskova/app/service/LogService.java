package eu.pracenjetroskova.app.service;

import java.util.List;

import eu.pracenjetroskova.app.model.Log;

public interface LogService {

	Log createLog (Log log);
	void deleteALl (List<Log> logs);
}
