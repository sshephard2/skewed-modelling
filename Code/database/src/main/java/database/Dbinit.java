package database;

import java.util.Calendar;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * @author Stephen Shephard
 *
 */
public class Dbinit {

	/**
	 * Supply as arguments: host, keyspace, sport, ticket prefix, number of seats, number of days
	 * @param args - host, keyspace, sport, prefix, seats, days
	 */
	public static void main(String[] args) {
		
		String host;
		String keyspace;
		String sport;
		int prefix;
		int seats;
		int days;
		
		// Process the input arguments
		if (args.length < 6) {
			throw new IllegalArgumentException("Requires arguments host, keyspace, sport, prefix, seats, days");
		}
		
		// Need to add checks and relevant exceptions
		host = args[0];
		keyspace = args[1];
		sport = args[2];
		
		try {
			prefix = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("prefix argument must be a valid number");
		}
		
		if (prefix <= 0) {
			throw new IllegalArgumentException("prefix argument must be a strictly positive number");
		}
		
		try {
			seats = Integer.parseInt(args[4]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("seats argument must be a valid number");
		}
		
		if (seats <= 0) {
			throw new IllegalArgumentException("seats argument must be a strictly positive number");
		}
		
		try {
			days = Integer.parseInt(args[5]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("days argument must be a valid number");
		}
		
		if (days <= 0) {
			throw new IllegalArgumentException("days argument must be a strictly positive number");
		}
		
		Cluster cluster = null;
		ResultSet rs = null;
		try {
		    cluster = Cluster.builder()
		            .addContactPoint(host) // use supplied Cassandra host
		            .build();
		    Session session = cluster.connect(keyspace); // attempt to connect to Cassandra   
		    
		    /**
		     * Ticket table schema
		     * 
		     * int id - unique ticket id
		     * varchar sport - type of sport
		     * int day - day of event
		     * int seat - seat number
		     * varchar owner - name of ticket owner (for booked ticket)
		     * 
		     * The partition key is sport
		     * The clustering columns are owner, day, id
		     */
		    
		    System.out.println("Creating ticket table in keyspace " + keyspace);
		    
		    // Drop ticket table if it exists
		    session.execute("DROP TABLE IF EXISTS ticket;");
		    
		    // Create ticket table
		    session.execute("CREATE TABLE ticket (id int, sport varchar, day int, seat int, owner varchar, PRIMARY KEY (sport, owner, day, id)) WITH comment = 'Tickets';");
		    
		    System.out.println("Creating " + sport + " tickets");
		    
		    // Generate and insert tickets
		    int id = prefix;
		    
		    // For each day of the sporting event
		    for (int day=1; day<=days; day++) {
		    	
		    	System.out.println("Creating " + seats + " tickets for day " + day);
		    	// For every seat in the venue
		    	for (int seat=1; seat<=seats; seat++) {
		    		
		    		// Insert a ticket entry
		    		session.execute("INSERT INTO ticket (id, sport, day, seat, owner) VALUES (?, ?, ?, ?, '');", id++, sport, day, seat);
		    	}
		    }
		    
		    System.out.println("Done");
		    
		} finally {
		    if (cluster != null) cluster.close();
		}
	}

}
