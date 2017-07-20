#!/usr/bin/python3

########## Process db metrics output file, using timestamps ##########

# Import csv module
import csv

# Metrics filename
METRICS_FILE = '../dbreplication/results/run2/pro_db3.csv'

# Processed output filename
OUTPUT_FILE = '../dbreplication/results/run2/ppro_db3.csv'

# Timestamps for each run
timestamps = ['1500474643', '1500474853', '1500475073', '1500475323', '1500475593',
              '1500475922', '1500476312', '1500476772', '1500477272', '1500477811']

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
test_timestamp = int(timestamps.pop(0))

# Read metrics file
with open(METRICS_FILE, 'r', newline='') as infile:
    reader = csv.DictReader(infile, delimiter = ',')

    # Write to output file
    with open(OUTPUT_FILE, 'w', newline='') as outfile:
        writer = csv.DictWriter(outfile, fieldnames=['users','queries','throughput'])
        writer.writeheader()        
        for row in reader:

            # Keep a running maximum of m1_rate (average over 1m)
            max_m1_rate = max(float(row['m1_rate']), max_m1_rate)

            # If we have reached the end of a test
            if int(row['t'])>=test_timestamp:
                print(user_count, query_count, max_m1_rate)

                # Write the maximum throughput of that test to the output
                writer.writerow({'users': user_count,'queries': query_count,'throughput': round(max_m1_rate,2)})

                # Next rows are from the next test so reset the running maximum
                max_m1_rate = 0

                # Update the query count
                user_count = user_count + USER_INCREMENT
                query_count = query_count + QUERIES_PERUSER*user_count

                # Get the next end timestamp
                test_timestamp = int(timestamps.pop(0)) if len(timestamps)>0 else 9999999999
