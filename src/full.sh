#!/bin/bash

for inputFileName in f50cm f50cmB l1m l1mB r20cm r20cmB r50cm r1m r60cm r50cm2 l70cm l90cm
do
  for R in {0..10}
  do
    for T in {0..10}
    do
      if [ "$R" -eq "0" -a "$T" -eq "0" ]
      then
        java identifyMacAddress/IdentifyMacAddress text/$inputFileName.txt $R $T >result/$inputFileName.txt
      else
        java identifyMacAddress/IdentifyMacAddress text/$inputFileName.txt $R $T >>result/$inputFileName.txt
      fi
      echo "$R,$T isdone"
    done
  done
  java dataAnalyze/DataAnalyze result/$inputFileName.txt > analyze/analyze$inputFileName.txt
done
