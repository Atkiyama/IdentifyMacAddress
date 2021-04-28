#!/bin/bash
LS=$(ls data/capture/)
for inputFileName in ${LS}
do
  for R in {0..10}
  do
    for T in {0..10}
    do
      if [ "$R" -eq "0" -a "$T" -eq "0" ]
      then
        java identifyMacAddressSingle/drive/IdentifyMacAddress data/capture/$inputFileName $R $T >data/result/single/$inputFileName
      else
        java identifyMacAddressSingle/drive/IdentifyMacAddress data/capture/$inputFileName $R $T >>data/result/single/$inputFileName
      fi
    done
  done
  java dataAnalyze/analyze/DataAnalyze data/result/single/$inputFileName > data/result/analyze/analyze$inputFileName
  echo "$inputFileName is done"
done
