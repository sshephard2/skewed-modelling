# Code

General notes

Updating cluster name on Cassandra

	cqlsh> UPDATE system.local SET cluster_name = 'test' where key='local';
	# flush the sstables to persist the update.
	bash $ ./nodetool flush system
