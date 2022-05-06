#bin/bash

for n in 1
do
  for R in 1
  do
    for T in 5
    do
      for P in 1
      do
        forCount='ps|grep java|wc -l'
        count=$(eval $forCount)
        while [$count -gt 1 ]
        do
          forCount='ps|grep java|wc -l'
          count=$(eval $forCount)
        done
          java identifyMacAddress/identify/IdentifyMove data/capture/convert/move/8/$n,convertData.csv $R $T $P $n >data/result/multi/move/8/$n/$R,$T,$P.txt &
      done
    done
  done
  #コレがないと現状落ちるのでいれておくこと(プロなら回避可能??)
done
