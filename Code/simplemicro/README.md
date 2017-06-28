# Simplemicro

## Cassandra setup

Changes to `conf/cassandra.yaml` for basic setup and to throttle performance.

	rpc_address: 0.0.0.0
	...
	broadcast_rpc_address: (IP address of server)
	...
	concurrent_reads: 2
	concurrent_writes: 2
	concurrent_counter_writes: 2
	...
	native_transport_max_concurrent_connections: 2
	...
	rpc_max_threads: 2
	...
	stream_throughput_outbound_megabits_per_sec: 1

Create a separate Cassandra database for each sport.

Run the CQL script below on each DB.

`SOURCE 'simplemicro.cql';`

Run the `dbinit` tool on each DB as follows:

	dbinit host1 Simplemicro Athletics 1000 100 5
	dbinit host2 Simplemicro Cycling 2000 50 10

# Cassandra metrics

The file `metrics-reporter-throughput.yaml` has been configured to record `org.apache.cassandra.metrics.ThreadPools.CompletedTasks.request.ReadStage` every 10s to a csv file in `/tmp/throughput_metrics/`.

To use this metric configuration, start Cassandra from the command line as below:

`bin/cassandra -Dcassandra.metricsReporterConfigFile=metrics-reporter-throughput.yaml`

# Stress testing Cassandra

Run the `dbstress` tool on each DB as follows.  This runs 2 threads of 10,000 CQL queries each against the `ticket` table using a metred metric, output as `logs/queries.csv`

	dbstress host1 Simplemicro Athletics 10000 2
	dbstress host2 Simplemicro Cycling 10000 2

# Stress testing system

Use [JMeter](http://jmeter.apache.org "JMeter") for testing against RESTful APIs with load.

Run from command line as:

	jmeter-n.cmd Simplemicro.jmx

Use test plans `calibration.jmx` and `Simplemicro.jmx` for development.

Using a Thread Group with:

	threads = 100
	loop count = 100

and a Poisson random timer with:

	lambda = 10,000ms

Gives an approximate input arrival rate of 10 events per second.

For a desired rate of *r* events per second, suggest lambda of *100,000/r* ms and loop count of at least *3r* to get enough samples for a mean rate over 5 minutes, *5r* is better.