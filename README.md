# Performance modelling and simulation of skewed demand in complex systems

Platform decisions:

* Build the applications on Microsoft Azure
* Use an Azure Storage Queue for the shared queue
* Use MongoDB for the distributed database
* Develop in Java

## Plan outline

Build PEPA models for microservices and shared queue architectures for a multi-tier OLTP application, using the example of Olympic ticketing.

Build the applications for those architectures and instrument them to measure throughput.
Test the models against the real applications under different scenarios of skewed demand.

* Build model
* Build application
* Instrument application
* Measure and test model

### Model 1 - separate DBs for sport types "simple Microservices"

There are two DBs, one for Archery tickets, one for Athletics.  Athletics will have skewed demand.

It's expected that this architecture will lead to isolation of the skewed demand and that the results of testing the model will not be surprising, but that this will provide a useful control for other architectures.

### Model 2 - shared queue middleware

Requests via a shared queue to worker applications going to a distributed DB with two nodes, Athletics and Archery.

### Model 3 - realistic Microservices

DBs by operation (Search, Book, Return) with a backplane process for eventual consistency

### Model 4 - distributed DB with replication

### Model 5 - priority queues?

### Model 6 - smaller Microservices?

Question: do this as a separate model or use this instead of model 3

* Book Archery
* Book Athletics
* Search
* Return
