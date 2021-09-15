#!/bin/bash


LS=$(ls data/capture/single/stay)
for inputFileName in ${LS}
do
  for R in {1..20}
  do
    for T in {1..20}
    do
      if [ "$R" -eq "0" -a "$T" -eq "0" ]
      then
        java identifyMacAddress/identify/IdentifyStay data/capture/single/stay/$inputFileName $R $T >data/result/single/stay/$inputFileName
      else
        java identifyMacAddress/identify/IdentifyStay data/capture/single/stay/$inputFileName $R $T >>data/result/single/stay/$inputFileName
      fi
    done
  done
  sleep 5m
  java dataAnalyze/analyze/DataAnalyze data/result/single/stay/$inputFileName > data/result/analyze/stay/analyze$inputFileName
  echo "$inputFileName is done"
done
