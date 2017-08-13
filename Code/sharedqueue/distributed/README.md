# Shared queue with distributed database

Cassandra configured with 2 nodes.

Run the CQL script below on the DB.

`SOURCE 'distributed.cql';`

Run the `dbinit` tool on the DB as follows:

	dbinit host1 Distributed Athletics 1000 100 5
	dbinit host1 Distributed Cycling 2000 50 10

Find out where the data has been put:

	nodetool getendpoints distributed ticket Athletics
	nodetool getendpoints distributed ticket Cycling

Load test with cassandra-stress (tested at 475 ops/s)

`bin/cassandra-stress user profile=cqlstress-distributed.yaml "ops(athletics=1)" -rate threads=16`