rm -rf /tmp/throughput_metrics
bin/cassandra -Dcassandra.metricsReporterConfigFile=metrics-reporter-throughput.yaml
