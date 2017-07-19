#!/usr/bin/python3

########## Process count output file ##########

# Import csv module
import csv

# Metrics filename
METRICS_FILE = '../dbreplication/results/run2/db3/org.apache.cassandra.metrics.ThreadPools.CompletedTasks.request.ReadStage.csv'

# Processed output filename
OUTPUT_FILE = '../dbreplication/results/run2/pro_db3.csv'

# Starting values
start_timestamp = 0
last_six = []

# Read metrics file
with open(METRICS_FILE, 'r', newline='') as infile:
    reader = csv.DictReader(infile, delimiter = ',')

    # Write to output file
    with open(OUTPUT_FILE, 'w', newline='') as outfile:
        writer = csv.DictWriter(outfile, fieldnames=['t', 'count', 'mean_rate', 'm1_rate'])
        writer.writeheader()        
        for row in reader:

            count = int(row['value'])
            
            # mean_rate
            if start_timestamp == 0:
                start_timestamp = int(row['t'])
            elapsed_time = int(row['t']) - start_timestamp
            mean = 0 if elapsed_time == 0 else count/elapsed_time

            # m1_rate
            last_six.append(count)
            if len(last_six) == 7:
                last_count = last_six.pop(0)
            else:
                last_count = last_six[0]
            m1_rate = (count-last_count)/60

            # Write the maximum throughput of that test to the output
            writer.writerow({'t': row['t'], 'count': count, 'mean_rate': mean, 'm1_rate': m1_rate})

