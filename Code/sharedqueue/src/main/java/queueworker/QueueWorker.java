package queueworker;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QueueWorker {

	/**
	 * Main method
	 * Supply as arguments: number of threads
	 * @param args - threads
	 */
	public static void main(String[] args) {
		
		// Process the input arguments
		if (args.length < 1) {
			throw new IllegalArgumentException("Requires arguments: threads");
		}
		
		int numThreads;
		try {
			numThreads = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("threads argument must be a valid number");
		}
		
		if (numThreads <= 0) {
			throw new IllegalArgumentException("threads argument must be a strictly positive number");
		}
		
        // Retrieve the account settings and build the connection string
        Properties prop = new Properties();
        try {
            InputStream propertyStream = QueueWorker.class.getClassLoader().getResourceAsStream("config.properties");
            if (propertyStream != null) {
                prop.load(propertyStream);
            }
            else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            System.out.println("Failed to load config.properties file.");
            return;
        }
		
        final String accountName = prop.getProperty("AccountName");
        final String accountKey = prop.getProperty("AccountKey");
        final String queueName = prop.getProperty("QueueName");
        
        final String storageConnectionString =
        		"DefaultEndpointsProtocol=http;" +
        		"AccountName=" + accountName + ";" +
        		"AccountKey=" + accountKey;

		List<Thread> threads = new ArrayList<Thread>();
		
		// Start required number of threads
		for (int t=0; t<numThreads; t++) {
			Runnable task;
			try {
				task = new QueueWorkerRunnable(storageConnectionString, queueName);
				Thread worker = new Thread(task);
				worker.start();
				threads.add(worker);
			} catch (Exception e) {
				// Can't start thread
				e.printStackTrace();
			}
		}

		// Monitor until all threads have stopped
        int running = 0;
        do {
            running = 0;
            for (Thread thread : threads) {
                if (thread.isAlive()) {
                    running++;
                }
            }
        } while (running > 0);
        System.out.println("All threads stopped");
	}
}