#bin/bash

LS=$(ls data/capture/ver3/csv/)
for inputFileName in ${LS}
do
    java processed/timeAdjustment/Split data/capture/ver3/csv/$inputFileName
done
