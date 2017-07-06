package queueworker;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.queue.*;

public class QueueWorkerRunnable implements Runnable {
	
	// Azure storage account and queue client
	private final CloudStorageAccount storageAccount;
	private final CloudQueueClient queueClient;
	private final CloudQueue queue;
	
	// Metrics
	private final MetricRegistry metrics;
	private final Meter metredControl;
	private final Meter metredAthletics;
	private final Meter metredCycling;
	
	/**
	 * Constructor for QueueWorkerRunnable
	 * @param metrics
	 * @param storageConnectionString
	 * @param queueName
	 * @throws URISyntaxException 
	 * @throws InvalidKeyException 
	 * @throws StorageException 
	 */
	public QueueWorkerRunnable(MetricRegistry metrics, String storageConnectionString, String queueName) throws InvalidKeyException, URISyntaxException, StorageException {	
		// Retrieve storage account from connection-string
	    storageAccount = CloudStorageAccount.parse(storageConnectionString);

	    // Create the queue client
	    queueClient = storageAccount.createCloudQueueClient();

	    // Retrieve a reference to a queue
	    queue = queueClient.getQueueReference(queueName);
	    
	    // Set up metrics
	    this.metrics = metrics;
		metredControl = this.metrics.meter("control");
		metredAthletics = this.metrics.meter("athletics");
		metredCycling = this.metrics.meter("cycling");
	}

	/**
	 * Thread run method
	 */
	public void run() {
		ObjectMapper mapper = new ObjectMapper();
		while(true) {
		    // Attempt to retrieve message from the queue
		    CloudQueueMessage retrievedMessage = null;
			try {
				retrievedMessage = queue.retrieveMessage();
			    if (retrievedMessage != null) {
			        // Process the message in less than 30 seconds, and then delete the message
					String queueMessage = retrievedMessage.getMessageContentAsString();
				    queue.deleteMessage(retrievedMessage);	    
				    
				    Ticket ticket = mapper.readValue(queueMessage, Ticket.class);
				    
				    String sport = ticket.getSport();
				    if (sport.equals("Control")) {
					    // Record this ticket return in metrics
					    metredControl.mark();				    	
				    } else if (sport.equals("Athletics")) {
					    // Record this ticket return in metrics
					    metredAthletics.mark();					    	
				    } else if (sport.equals("Cycling")) {
					    // Record this ticket return in metrics
					    metredCycling.mark();					    	
				    } else {
				    	// Unrecognised ticket, ignore
				    }
				    
				    System.out.println(ticket.getSport() + ":" + ticket.getId());
			    }
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}
