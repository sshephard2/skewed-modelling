/*
Distributed keyspace uses no replication
*/

CREATE KEYSPACE IF NOT EXISTS Replicated WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 2};
USE Replicated;