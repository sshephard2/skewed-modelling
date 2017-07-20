#!/usr/bin/python3

########## Get timestamps of each run ##########

# Import csv module
import csv

# Metrics filename
METRICS_FILE = '../dbreplication/results/run3/athletics.csv'

# Timestamp list
timestamps = []

# Number of queries per user
QUERIES_PERUSER = 1425

# Starting number of users
USER_START = 15

# User increment per test run
USER_INCREMENT = 15

# Starting values
user_count = USER_START
query_count = QUERIES_PERUSER*user_count
max_m1_rate = 0

# Read metrics file
with open(METRICS_FILE, 'r', newline='') as infile:
    reader = csv.DictReader(infile, delimiter = ',')   
    for row in reader:

        # If we have reached the end of a test
        if int(row['count'])>=query_count:
            timestamps.append(row['t'])

            # Set the end query count to look for the end of this test
            user_count = user_count + USER_INCREMENT
            query_count = query_count + QUERIES_PERUSER*user_count

# Output the list
print(timestamps)

