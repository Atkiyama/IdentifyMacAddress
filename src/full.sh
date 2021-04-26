#!/bin/bash

for inputFileName in f50cm f50cmB l1m l1mB r20cm r20cmB r50cm r1m r60cm r50cm2 l70cm l90cm
do
  for R in {0..10}
  do
    for T in {0..10}
    do
      if [ "$R" -eq "0" -a "$T" -eq "0" ]
      then
        java identifyMacAddress/IdentifyMacAddress data/capure/$inputFileName.txt $R $T >data/result/single/$inputFileName.txt
      else
        java identifyMacAddress/IdentifyMacAddress data/capture/$inputFileName.txt $R $T >>data/result/single/$inputFileName.txt
      fi
    done
  done
  sleep 5m
  java dataAnalyze/DataAnalyze data/result/$inputFileName.txt > data/result/analyze/analyze$inputFileName.txt
  echo "$inputFileName is done"
done
