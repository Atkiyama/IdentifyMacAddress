#!/bin/bash

LS=$(ls data/capture/single/move/txt/)
for inputFileName in ${LS}
  do
    java dataAnalyze/DataAnalyze data/capture/single/move/txt/$inputFileName 9 20 15 100 >data/result/analyze/move/$inputFileName
  done
