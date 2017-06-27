package database;

import com.codahale.metrics.Meter;
import com.datastax.driver.core.Session;

/**
 * @author Stephen Shephard
 *
 */
public class DbstressRunnable implements Runnable {
	
	private String threadName;
	
	private Session session;
	private Meter metredQuery;

	private String sport;
	private int queries;
	
	/**
	 * Constructor for DbstressRunnable
	 * @param session
	 * @param metredQuery
	 * @param threadName
	 * @param sport
	 * @param queries
	 */
	DbstressRunnable(Session session, Meter metredQuery, String threadName, String sport, int queries) {
		this.session = session;
		this.metredQuery = metredQuery;
		this.threadName = threadName;
		this.sport = sport;
		this.queries = queries;
	}
	
	/**
	 * Thread run method
	 */
	public void run() {

		try {
	    	    
		    // Run the requested number of queries against the database
		    for (int i=1; i<=queries; i++) {
		    	
		    	// Show progress every 1000 queries
		    	if (i%1000 == 0) {
		    		System.out.println("Thread " + threadName + ":" + i + " queries of " + queries);
		    	}
		    	
		    	// Query for all results from the ticket table for the requested sport
		    	session.execute("SELECT * FROM ticket WHERE sport=?;", sport);
		    	metredQuery.mark();
		    }		    	    
		    
		} finally {
			System.out.println("Thread " + threadName + " done");
		}		
	}
}
