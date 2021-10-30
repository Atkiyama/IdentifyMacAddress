#bin/bash

#コマンドライン引数
numOfData=$1
for method in linerRegression svr bagging
do
  for n in {1..10000}
  do
    for R in {1..20}
    do
      for T in {1..20}
      do
        for I in {1..20}
        do
          forCount='ps|grep ./identify|wc -l'
          count=$(eval $forCount)
          while [$count -gt 25 ]
          do
            forCount='ps|grep ./identify|wc -l'
            count=$(eval $forCount)
          done
            ./identify $R $T $I $numOfData $n $method >> ./data/result/multi/move/$method/$numOfData/$R,$T,$P,$n.txt
        done
      done
    done
  done
done
