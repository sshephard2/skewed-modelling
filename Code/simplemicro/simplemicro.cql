/*
Simplemicro keyspace uses no replication
*/

CREATE KEYSPACE IF NOT EXISTS Simplemicro WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};