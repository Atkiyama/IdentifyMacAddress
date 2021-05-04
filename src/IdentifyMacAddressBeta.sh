#!/bin/bash

echo "textフォルダの読み込むファイル名を拡張子抜きで入力してください"
read inputFileName
for R in {0..10}
do
  for T in {0..10}
  do
    if [ "$R" -eq "0" -a "$T" -eq "0" ]
    then
      java identifyMacAddress/IdentifyMacAddress data/capture/$inputFileName.txt $R $T >data/result/single/$inputFileName.txt
    else
      java identifyMacAddress/IdentifyMacAddress data/capture/$inputFileName.txt $R $T >>data/result/single/$inputFileName.txt
    fi
    echo "$R,$T isdone"
  done
done
