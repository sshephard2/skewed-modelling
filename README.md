# Performance modelling of skewed demand in complex systems

## Plan outline

Build PEPA models for microservices and shared queue architectures for a multi-tier OLTP application, using the example of Olympic ticketing.

Build the applications for those architectures and instrument them to measure throughput.

Test the models against the real applications under different scenarios of skewed demand.

It's expected that the microservices architecture will lead to isolation of the skewed demand and that the results of testing the model will not be surprising, but that this will provide a useful control for the shared queue architecture.

Note on microservices: explore the idea of a backplane process for eventual consistency and its impact?

Platform decisions:

* Build the applications on Microsoft Azure
* Use an Azure Storage Queue for the shared queue
* Use MongoDB for the distributed database
* Develop in Java

Questions:

A 'natural' microservices architecture would suggest partitioning by operation (Search, Book, Return), a natural skewed demand scenario would suggest partitioning by sport.

### Microservices

* Build model
* Build application
* Instrument application
* Measure and test model

### Shared queue

* Build model
* Build application
	Adapt the application previously built to use a shared queue
* Instrument application
* Measure and test model
* Experiment