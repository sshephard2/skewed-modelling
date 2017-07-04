# Code

General notes

Updating cluster name on Cassandra

	cqlsh> UPDATE system.local SET cluster_name = 'Test Cluster' where key='local';
	# flush the sstables to persist the update.
	bash $ ./nodetool flush system

Node also in case of schema sync issues between different nodes in a cluster:

	nodetool resetlocalschema
	nodetool describecluster