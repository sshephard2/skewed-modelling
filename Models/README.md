# Models
PEPA models here

Models for

* simple microservices - one DB for Athletics tickets, one separate DB for Cycling
* shared queue - middleware architecture with distributed database
* operational microservices - DBs by operation (Book, Search, Return)
* microservices with event sourcing - as above plus DB eventual consistency via event streaming (the event streaming process may be modelled as a queue?)
* replicated distributed DB - distributed DB with partition replication
