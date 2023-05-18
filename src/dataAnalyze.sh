#!/bin/bash


#該当すると思われるアドレスを抽出
LS=$(ls data/capture/ver3/txt/)
for inputFileName in ${LS}
do
    java dataAnalyze/DataAnalyze data/capture/ver3/original/$inputFileName 9 15 8 0 >data/result/analyze/ver3/$inputFileName
done
