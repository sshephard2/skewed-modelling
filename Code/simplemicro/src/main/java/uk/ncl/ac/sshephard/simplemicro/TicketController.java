package uk.ncl.ac.sshephard.simplemicro;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class TicketController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TicketRepository repository;

    @CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping("/search")
    public List<Ticket> search(@RequestParam(value="sport", defaultValue="") String sport, @RequestParam(value="owner", defaultValue="") String owner) {
    	if (sport.isEmpty()) {
    		logger.info("search");
    		return repository.findAllBy();
    	}
    	else {
    		logger.info("search,{},{}", sport, owner);
    		return repository.findBySportAndOwner(sport, owner);
    	}
    }

}
