# Simplemicro

## Cassandra setup

Create a separate Cassandra database for each sport.

Run the CQL script below on each DB.

`SOURCE 'simplemicro.cql'`

Run the `dbinit` tool on each DB as follows:

`dbinit host1 Simplemicro Athletics 1000 100 5`
`dbinit host2 Simplemicro Cycling 2000 50 10`