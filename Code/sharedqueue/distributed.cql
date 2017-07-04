/*
Distributed keyspace uses no replication
*/

CREATE KEYSPACE IF NOT EXISTS Distributed WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};