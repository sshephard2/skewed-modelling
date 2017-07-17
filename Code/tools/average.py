#!/usr/bin/python3

########## Average of processed metrics output files ##########

# Metrics filename
METRICS_PATH = '../sharedqueue/results/run*/'
METRICS_FILE = 'ppro_db2.csv'

# Processed output filename
OUTPUT_FILE = '../../Reports/data/builtddnr/average_db2.csv'

import pandas as pd
import glob
 
results = pd.DataFrame([])

# Read in data from each file
for counter, file in enumerate(glob.glob(METRICS_PATH + METRICS_FILE)):
    namedf = pd.read_csv(file, skiprows=0, usecols=['users', 'queries', 'throughput'])
    results = results.append(namedf)

# Output mean average values to csv
results.groupby(['queries']).mean().to_csv(OUTPUT_FILE)
