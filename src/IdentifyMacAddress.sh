#!/bin/bash

for R in {0..10}
do
  for T in {0..10}
  do
    if [ "$R" -eq "0" -a "$T" -eq "0" ]
    then
      java identifyMacAddress/IdentifyMacAddress data/result/multiData.csv $R $T >data/result/multi/$R,$T.txt
    else
      java identifyMacAddress/IdentifyMacAddress data/result/multiData.csv $R $T >>data/result/multi/$R,$T.txt
    fi
    echo "$R,$T isdone"
  done
done
