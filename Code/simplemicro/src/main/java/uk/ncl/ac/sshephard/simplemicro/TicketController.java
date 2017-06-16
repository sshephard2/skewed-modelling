package uk.ncl.ac.sshephard.simplemicro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class TicketController {
	
	@Autowired
	private TicketRepository repository;

    @CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping("/search")
    public List<Ticket> search(@RequestParam(value="sport", defaultValue="") String sport) {
    	if (sport.isEmpty()) {
    		return repository.findAllBy();
    	}
    	else {
    		return repository.findBySport(sport);
    	}
    }
    
/**
    public Ticket book(@RequestParam(value="name", defaultValue="") String owner) {
    	int id = counter.incrementAndGet();
        Ticket ticket = new Ticket(id, "Athletics", 1, id, owner);
        
        cassandraOperations.insert(ticket);
        
        return ticket;
    }
    */

}
