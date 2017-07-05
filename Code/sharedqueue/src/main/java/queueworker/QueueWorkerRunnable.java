package queueworker;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.queue.*;

public class QueueWorkerRunnable implements Runnable {
	
	// Azure storage account and queue client
	private final CloudStorageAccount storageAccount;
	private final CloudQueueClient queueClient;
	private final CloudQueue queue;
	
	/**
	 * Constructor for QueueWorkerRunnable
	 * @param storageConnectionString
	 * @param queueName
	 * @throws URISyntaxException 
	 * @throws InvalidKeyException 
	 * @throws StorageException 
	 */
	public QueueWorkerRunnable(String storageConnectionString, String queueName) throws InvalidKeyException, URISyntaxException, StorageException {	
		// Retrieve storage account from connection-string
	    storageAccount = CloudStorageAccount.parse(storageConnectionString);

	    // Create the queue client
	    queueClient = storageAccount.createCloudQueueClient();

	    // Retrieve a reference to a queue
	    queue = queueClient.getQueueReference(queueName);
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
