#!/bin/bash
LS=$(ls data/capture/single/move/txt)
for inputFileName in ${LS}
do
  for R in {1..10}
  do
    for T in {1..10}
    do
      if [ "$R" -eq "1" -a "$T" -eq "1" ]
      then
        java identifyMacAddress/identify/IdentifyStay data/capture/single/move/txt/$inputFileName $R $T >data/result/single/move/$inputFileName
      else
        java identifyMacAddress/identify/IdentifyStay data/capture/single/move/txt/$inputFileName $R $T >>data/result/single/move/$inputFileName
      fi
    done
  done
  java dataAnalyze/analyze/DataAnalyze data/result/single/move/$inputFileName > data/result/analyze/analyze$inputFileName
  echo "$inputFileName is done"
done
