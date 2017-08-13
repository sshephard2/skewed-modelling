# Shared queue with distributed database with replication
Cassandra configured with 3 nodes, using `ByteOrderedPartitioner`.

There are 3 tokens (one per node) to force partitioning by sport (first byte of each token = ascii code of first letter of each sport):

	41ffffffffffffffff
	43ffffffffffffffff
	44ffffffffffffffff

Run the CQL script below on the DB.

`SOURCE 'replicated.cql';`

Run the `dbinit` tool on the DB as follows:

	dbinit host1 Replicated Athletics 1000 100 5
	dbinit host1 Replicated Cycling 2000 50 10
	dbinit host1 Replicated Diving 3000 50 10

Find out where the data has been put:

	nodetool getendpoints replicated ticket Athletics
	nodetool getendpoints replicated ticket Cycling
	nodetool getendpoints replicated ticket Diving

Load test with cassandra-stress (tested at 600 ops/s)

`bin/cassandra-stress user profile=cqlstress-replicated.yaml "ops(athletics=1)" -rate threads=16`