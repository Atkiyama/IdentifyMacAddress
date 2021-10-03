#!/bin/bash


LS=$(ls data/capture/single/move/txt)
for inputFileName in ${LS}
do
  java identifyMacAddress/io/ReadTXT data/capture/single/move/txt/$inputFileName > data/capture/single/move/csv/${inputFileName//.txt/.csv}
done
