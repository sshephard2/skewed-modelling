package uk.ncl.ac.sshephard.simplemicro;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Metered;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class TicketController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TicketRepository repository;

	/**
	 * RESTful API - /search is mapped to search method
	 * Perform searches on tickets
	 * @param requestParams map of query parameters (sport, owner, day)
	 * @return list of Ticket entities
	 */
    @CrossOrigin
    @RequestMapping("/search")
    @Metered
    public List<Ticket> search(@RequestParam Map<String,String> requestParams) {
    	
    	// If a sport is not specified, then return all
    	String sport = requestParams.get("sport");
    	if (sport == null || sport.isEmpty()) {
    		logger.info("search");
    		return repository.findAllBy();
    	}
    	
    	// If an owner is not specified, default to owner=""
    	String owner = requestParams.get("owner");
    	if (owner == null) {
    		owner = "";
    	}
    	
    	// If a day has not been specified, return all tickets by sport and owner
    	String dayString = requestParams.get("day");
    	if (dayString == null || dayString.isEmpty()) {
    		logger.info("search,{},{}", sport, owner);
    		return repository.findBySportAndOwner(sport, owner);
    	}
    	
    	// A day has been specified, so try to parse it
    	Integer day;
		try {
			day = Integer.parseInt(dayString);
		} catch (NumberFormatException e) {
			logger.error("search,{},{},{}", sport, owner, dayString);
			return Collections.<Ticket>emptyList();
		}

		// Day has been parsed
		logger.info("search,{},{},{}", sport, owner, dayString);
		return repository.findBySportAndOwnerAndDay(sport, owner, day);
    }
    
    /**
     * RESTful API - /control is mapped to control method
     * A 'dummy' method that doesn't access the database
     * for control measurement of throughput
     * @param log GET /control?log="Y" to log requests to the API
     * @return a ticket entity
     */
    @CrossOrigin
    @RequestMapping("/control")
    @Metered
    public Ticket control(@RequestParam(required=false, defaultValue="N") String log) {
    	
    	// If log parameter is "Y" then log this request
    	if (log.equals("Y")) {
    		logger.info("control");
    	}
    	
    	// Return a dummy ticket entity that indicates this is returned from /control
    	return new Ticket(0, "Control", 0, 0, "");
    }
}
