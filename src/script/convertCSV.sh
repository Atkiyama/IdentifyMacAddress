#!/bin/bash


LS=$(ls data/capture/ver3/txt)
for inputFileName in ${LS}
do
  java dataAnalyze/io/ReadTXT data/capture/ver3/txt/$inputFileName > data/capture/ver3/csv/${inputFileName//.txt/.csv}
done
