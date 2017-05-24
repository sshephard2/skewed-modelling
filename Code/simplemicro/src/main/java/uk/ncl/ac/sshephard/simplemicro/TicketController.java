package uk.ncl.ac.sshephard.simplemicro;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
	
	@Autowired
	private CassandraOperations cassandraOperations;

    private final AtomicInteger counter = new AtomicInteger();

    @RequestMapping("/book")
    public Ticket book(@RequestParam(value="name", defaultValue="") String owner) {
    	int id = counter.incrementAndGet();
        Ticket ticket = new Ticket(id, "Athletics", 1, owner, id);
        
        cassandraOperations.insert(ticket);
        
        return ticket;
    }

}
