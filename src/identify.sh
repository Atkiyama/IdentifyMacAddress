#bin/bash

#コマンドライン引数
for numOfData in 5 10 15 20
do
  for method in linerRegression svr bagging
  do
      for R in {1..20}
      do
        for T in 6
        do
          for I in {1..20}
          do
            for n in {1..10000}
            do
              ./identify $R $T $I $numOfData $n $method > ./data/result/multi/move/$R,$T,$I,$n.txt
            done
            if [ "$R" -eq "1" -a "$I" -eq "1"]
            then
              java evaluation/Evaluation $R $T $I > data/result/evaluation/move/$method,$numOfData.txt
            else
              java evaluation/Evaluation $R $T $I >> data/result/evaluation/move/$method,$numOfData.txt
            fi
          done
        done
      done
  done
done
