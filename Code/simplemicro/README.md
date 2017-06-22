# Simplemicro

## Cassandra setup

Changes to `conf/cassandra.yaml`

	broadcast_rpc_address: (IP address of server)

Create a separate Cassandra database for each sport.

Run the CQL script below on each DB.

`SOURCE 'simplemicro.cql'`

Run the `dbinit` tool on each DB as follows:

`dbinit host1 Simplemicro Athletics 1000 100 5`
`dbinit host2 Simplemicro Cycling 2000 50 10`

# Cassandra metrics

The file `metrics-reporter-throughput.yaml` has been configured to record `org.apache.cassandra.metrics.ThreadPools.CompletedTasks.request.ReadStage` every 10s to a csv file in `/tmp/throughput_metrics/`.

To use this metric configuration, start Cassandra from the command line as below:

`bin/cassandra -Dcassandra.metricsReporterConfigFile=metrics-reporter-throughput.yaml`

# Stress testing Cassandra

Test for op/s rate that Cassandra can support using cassandra-stress.  Installation below shows a limit of 670 op/s.

	bin/cassandra-stress user profile=cqlstress-simplemicro.yaml "ops(athletics=1)" -rate threads=8 -log file=simplemicro.log
	
	Results:
	Op rate                   :      670 op/s  [athletics: 670 op/s]
	Partition rate            :      670 pk/s  [athletics: 670 pk/s]
	Row rate                  :  335,098 row/s [athletics: 335,098 row/s]
	Latency mean              :   11.7 ms [athletics: 11.7 ms]
	Latency median            :   10.1 ms [athletics: 10.1 ms]
	Latency 95th percentile   :   24.3 ms [athletics: 24.3 ms]
	Latency 99th percentile   :   50.8 ms [athletics: 50.8 ms]
	Latency 99.9th percentile :   68.8 ms [athletics: 68.8 ms]
	Latency max               :   77.3 ms [athletics: 77.3 ms]
	Total partitions          :     22,122 [athletics: 22,122]
	Total errors              :          0 [athletics: 0]
	Total GC count            : 220
	Total GC memory           : 17.187 GiB
	Total GC time             :    1.7 seconds
	Avg GC time               :    7.9 ms
	StdDev GC time            :    1.5 ms
	Total operation time      : 00:00:33

# Stress testing system

Use [JMeter](http://jmeter.apache.org "JMeter") for testing against RESTful APIs with load.

Use test plans `calibration.jmx` and `Simplemicro.jmx` for development.

Using a Thread Group with:

	threads = 100
	loop count = 100

and a Poisson random timer with:

	lambda = 10,000ms

Gives an approximate input arrival rate of 10 events per second.

For a desired rate of *r* events per second, suggest lambda of *100,000/r* ms and loop count of at least *3r* to get enough samples for a mean rate over 5 minutes, *5r* is better.