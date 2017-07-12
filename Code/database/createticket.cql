/*
 * Ticket table schema
 * 
 * int id - unique ticket id
 * varchar sport - type of sport
 * int day - day of event
 * int seat - seat number
 * varchar owner - name of ticket owner (for booked ticket)
 * 
 * The partition key is sport
 * The clustering columns are owner, day, id
 */

CREATE TABLE IF NOT EXISTS ticket (
	id int,
	sport varchar,
	day int,
	seat int,
	owner varchar,
	PRIMARY KEY (sport, owner, day, id)
) WITH comment = 'Tickets';