package database;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * @author Stephen Shephard
 *
 */
public class Dbstress {
	
	static final MetricRegistry metrics = new MetricRegistry();
	
	static void startMetrics() {
		final CsvReporter reporter = CsvReporter.forRegistry(metrics)
				.convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS)
				.build(new File("./logs/"));
		reporter.start(10, TimeUnit.SECONDS);
	}

	/**
	 * Supply as arguments: host, keyspace, sport, number of queries
	 * @param args - host, keyspace, sport, queries
	 */
	public static void main(String[] args) {
		
		String host;
		String keyspace;
		String sport;
		int queries;
		
		// Process the input arguments
		if (args.length < 4) {
			throw new IllegalArgumentException("Requires arguments host, keyspace, sport, queries");
		}
		
		// Need to add checks and relevant exceptions
		host = args[0];
		keyspace = args[1];
		sport = args[2];
		
		try {
			queries = Integer.parseInt(args[3]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("queries argument must be a valid number");
		}
		
		if (queries <= 0) {
			throw new IllegalArgumentException("queries argument must be a strictly positive number");
		}
		
		Cluster cluster = null;
		
		try {
		    cluster = Cluster.builder()
		            .addContactPoint(host) // use supplied Cassandra host
		            .build();
		    Session session = cluster.connect(keyspace); // attempt to connect to Cassandra
		    
		    // Start metrics
		    startMetrics();
			Meter metredQuery = metrics.meter("queries");    
		    
		    // Run the requested number of queries against the database
		    for (int i=1; i<=queries; i++) {
		    	
		    	// Show progress every 1000 queries
		    	if (i%1000 == 0) {
		    		System.out.println(i + " queries of " + queries);
		    	}
		    	
		    	metredQuery.mark();
		    	// Query for all results from the ticket table for the requested sport
		    	session.execute("SELECT * FROM ticket WHERE sport=?;", sport);
		    }		    
		    
		    System.out.println("Done");
		    
		} finally {
		    if (cluster != null) cluster.close();
		}
	}
}
