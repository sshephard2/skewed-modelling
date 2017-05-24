package uk.ncl.ac.sshephard.simplemicro;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

    private final AtomicInteger counter = new AtomicInteger();

    @RequestMapping("/book")
    public Ticket book(@RequestParam(value="name", defaultValue="") String owner) {
    	int id = counter.incrementAndGet();
        return new Ticket(id, "Athletics", 1, owner, id);
    }

}
