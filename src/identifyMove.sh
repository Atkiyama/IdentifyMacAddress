#bin/bash

#コマンドライン引数
numOfData=$1

for n in {1..100}
do
  for R in {1..20}
  do
    for T in 5
    do
      for P in {1..20}
      do
        java identifyMacAddress/identify/IdentifyMove data/capture/convert/move/$numOfData/$n,convertData.csv $R $T $P >data/result/multi/stay/$numOfData/$n/$R,$T,$P.txt
      done
    done
  done
  #コレがないと現状落ちるのでいれておくこと(プロなら回避可能??)
  sleep 5m
done
