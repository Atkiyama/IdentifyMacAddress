#bin/bash

#コマンドライン引数
for numOfData in 5 10 15 20
do
  for method in linerRegression svr bagging
  do
    for n in {1..10000}
    do
      for R in {1..20}
      do
        for T in 6
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
              ./identify $R $T $I $numOfData $n $method > ./data/result/multi/move/$method/$numOfData/$R,$T,$I,$n.txt &
          done
        done
      done
    done
  done
done