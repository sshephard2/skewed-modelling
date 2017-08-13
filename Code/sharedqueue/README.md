# Shared queue systems

Multi-threaded queue worker application.  Consumes ticket messages from an Azure Storage Queue.  If the tickets have sport=Control, then the application just records metrics.  Otherwise perform a search against the Cassandra database first.

Configure the Cassandra DB connection details and Azure Storage API details in `main\resources\config.properties` before building.

Queue worker has one mandatory parameter, the number of threads to use.

For testing use JMeter to place ticket messages onto the shared queue, using the Azure RESTful API.