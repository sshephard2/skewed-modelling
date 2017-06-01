# Models
## PEPA models

`ddist_norep.pepa`

A model of a simple horizontally partitioned distributed database with no replication.

`ddist_withrep.pepa`

A model of a simple horizontally partitioned distributed database with Cassandra style replication using consistent hashing.

`simplemicro.pepa`

Simple Microservices architecture model - one DB for Athletics tickets, one separate DB for Cycling

`simplemicro_no_workers.pepa`

As above without the worker applications - drop this model?

`generic_sharedqueue.pepa`

A queue shared between two pairs of Arrival/Service processes.

Uses aggregation to specify the number of places in the queue i.e. `Qempty[N]`

Experiments

For N=1,2,5,10 and 20 places in the queue, use service rates s1 = s2 = 5.0, arrival rate a2 = 1.0, arrival rates a1 from 1.0-10.0

Note that:
* the higher the number N of places in the queue, the closer the actual throughputs get to their intended rates, so the arrival rates become less limited by the service rates (they become less tightly coupled)

`sharedqueue.pepa`

Architecture model for a shared middleware queue with a distributed database backend.

`sharedqueue_withrep.pepa`

Architecture model for a shared middleware queue with a distributed database with replication backend.

## To-do list

Models for

* Event streaming - model of a Kafka-like streaming component (the event streaming process may be modelled as a queue?)
* Operational microservices - DBs by operation (Book, Search, Return) plus DB eventual consistency via event streaming
