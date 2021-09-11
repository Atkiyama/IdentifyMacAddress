#!/bin/bash

echo "move or stay?"
read isMove
LS=$(ls data/capture/$isMove)
for inputFileName in ${LS}
do
  for R in {1..20}
  do
    for T in {1..20}
    do
      if [ "$R" -eq "0" -a "$T" -eq "0" ]
      then
        java identifyMacAddress/IdentifyMacAddress data/capture/$isMove/$inputFileName $R $T >data/result/single/$isMove/$inputFileName
      else
        java identifyMacAddress/IdentifyMacAddress data/capture/$isMove/$inputFileName $R $T >>data/result/single/$isMove/$inputFileName
      fi
    done
  done
  sleep 5m
  java dataAnalyze/analyze/DataAnalyze data/result/single/$isMove/$inputFileName > data/result/analyze/$isMove/analyze$inputFileName
  echo "$inputFileName is done"
done
