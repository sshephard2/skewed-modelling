# Shared queue with distributed database with replication

https://sshephard.queue.core.windows.net/sharedqueue

Use JMeter to place messages directly onto the shared queue, via the Azure RESTful API

Simulating the Return operation

Ticket number is random, owner is empty string (pre-populate with a random owner id?)

Otherwise JMeter test plan will be similar to the simplemicro one (distribution and ramping up the demand)

One worker application (running on large VM), multi-threaded to dequeue and populate DB?
(and a control version?)

Run the CQL script below on the DB.

`SOURCE 'replicated.cql';`

Run the `dbinit` tool on the DB as follows:

	dbinit host1 Replicated Athletics 1000 100 5
	dbinit host1 Replicated Cycling 2000 50 10

Find out where the data has been put:

	nodetool getendpoints replicated ticket Athletics
	nodetool getendpoints replicated ticket Cycling
