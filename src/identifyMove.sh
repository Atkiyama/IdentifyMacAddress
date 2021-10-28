#bin/bash

#コマンドライン引数
numOfData=$1
for method in {linerRegression svr bagging}
  for n in {1..10000}
  do
    for R in {1..20}
    do
      for T in {1..20}
      do
        for P in {1..20}
        do
          forCount='ps|grep java|wc -l'
          count=$(eval $forCount)
          while [$count -gt 25 ]
          do
            forCount='ps|grep java|wc -l'
            count=$(eval $forCount)
          done
            java identifyMacAddress/identify/IdentifyMove data/capture/convert/move/$numOfData/$n,convertData.csv $R $T $P $n >data/result/multi/move/$numOfData/$n/$R,$T,$P.txt &
        done
      done
    done
    #コレがないと現状落ちるのでいれておくこと(プロなら回避可能??)
  done
done
