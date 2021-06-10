#!/bin/bash
LS=$(ls data/capture/)
for inputFileName in ${LS}
do
  for R in {1..10}
  do
    for T in {1..10}
    do
      if [ "$R" -eq "1" -a "$T" -eq "1" ]
      then
        java identifyMacAddress/IdentifyMacAddress data/capture/$inputFileName $R $T >data/result/single/$inputFileName
      else
        java identifyMacAddress/IdentifyMacAddress data/capture/$inputFileName $R $T >>data/result/single/$inputFileName
      fi
    done
  done
  java dataAnalyze/analyze/DataAnalyze data/result/single/$inputFileName > data/result/analyze/analyze$inputFileName
  echo "$inputFileName is done"
done
