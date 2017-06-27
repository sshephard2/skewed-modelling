package database;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
	 * Supply as arguments: host, keyspace, sport, number of queries, number of threads
	 * @param args - host, keyspace, sport, queries, threads
	 */
	public static void main(String[] args) {
		
		String host;
		String keyspace;
		String sport;
		int queries;
		int numThreads;
		
		// Process the input arguments
		if (args.length < 5) {
			throw new IllegalArgumentException("Requires arguments host, keyspace, sport, queries, threads");
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
		
		try {
			numThreads = Integer.parseInt(args[4]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("threads argument must be a valid number");
		}
		
		if (numThreads <= 0) {
			throw new IllegalArgumentException("threads argument must be a strictly positive number");
		}
		
		// Start Cassandra session and threads
		Cluster cluster = null;
		List<Thread> threads = new ArrayList<Thread>();
		try {
			cluster = Cluster.builder()
					.addContactPoint(host) // use supplied Cassandra host
					.build();

			Session session = cluster.connect(keyspace); // attempt to connect to Cassandra

			// Start metrics
			startMetrics();

			// Use Meter metric
			Meter metredQuery = metrics.meter("queries");
			
			// Start required number of threads
			for (int t=0; t<numThreads; t++) {
				Runnable task = new DbstressRunnable(session, metredQuery, new Integer(t+1).toString(), sport, queries);
				Thread worker = new Thread(task);
				worker.start();
				threads.add(worker);
			}
		} finally {
			// Do nothing
		}
        int running = 0;
        do {
            running = 0;
            for (Thread thread : threads) {
                if (thread.isAlive()) {
                    running++;
                }
            }
        } while (running > 0);
		if (cluster != null) cluster.close();
	}
}
