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
        commandString='ps|grep java|wc -l'
        count=$(eval $commandString)
        while [ "$count" -gt "10" ]
        do
          commandString='ps|grep java|wc -l'
          count=$(eval $commandString)
        done
          java identifyMacAddress/identify/IdentifyMove data/capture/convert/move/$numOfData/$n,convertData.csv $R $T $P $n >data/result/multi/move/$numOfData/$n/$R,$T,$P.txt 
      done
    done
  done
  #コレがないと現状落ちるのでいれておくこと(プロなら回避可能??)
done
