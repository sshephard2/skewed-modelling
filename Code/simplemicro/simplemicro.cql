/*
Ticket table schema

	int id - unique ticket id
	varchar sport - type of sport
	int day - day of event
	varchar owner - name of ticket owner (for booked ticket)
	int seat - seat number

The partition key is sport.
The clustering column is id.
*/

CREATE KEYSPACE IF NOT EXISTS Simplemicro WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};

USE Simplemicro;

CREATE TABLE IF NOT EXISTS ticket (
	id int,
	sport varchar,
	day int,
	owner varchar,
	seat int,
    PRIMARY KEY (sport, id)
) WITH comment = 'Tickets';
