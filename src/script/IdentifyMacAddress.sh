#!/bin/bash

for n in {1..100}
do
  for R in {1..20}
  do
    for T in {1..20}
    do
      java identifyMacAddress/IdentifyMacAddress data/capture/convert/$n,convertData.csv $R $T >data/result/multi/$n/$R,$T.txt
    done
  done
  sleep 5m
done
