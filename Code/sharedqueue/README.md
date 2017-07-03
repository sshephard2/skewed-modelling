# Shared queue with distributed database

https://sshephard.queue.core.windows.net/sharedqueue

Use JMeter to place messages directly onto the shared queue, via the Azure RESTful API

Simulating the Return operation

Ticket number is random, owner is empty string (pre-populate with a random owner id?)

Otherwise JMeter test plan will be similar to the simplemicro one (distribution and ramping up the demand)

One worker application (running on large VM), multi-threaded to dequeue and populate DB?
(and a control version?)

