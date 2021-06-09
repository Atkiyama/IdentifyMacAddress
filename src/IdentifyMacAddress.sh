#!/bin/bash

for n in {1..100}
do
  for R in {0..20}
  do
    for T in {0..10}
    do
      java identifyMacAddress/IdentifyMacAddress data/capture/convert/$n,convertData.csv $R $T >data/result/multi/$n/$R,$T.txt
      echo "$n,$R,$T"
    done
  done
done
