#bin/bash

for n in {1..100}
do
  java RandomDelay
  python3 $method.py
  for method in linerRegression svr bagging
  do
    for numOfData in 5 10 15 20
    do
      for R in 20
      do
        for T in 6
        do
          for I in 20
          do
            ./identify $R $T $I $numOfData $method >data/result/multi/move/$R,$T,$I,$numOfData,$n.txt
          done
        done
      done
    done
  done
done

for method in linerRegression svr bagging
do
  for numOfData in 5 10 15 20
  do
    for R in 20
    do
      for T in 6
      do
        for I in 20
        do
          java evaluation/evaluation/Evaluation $R $T $I >>data/result/evaluation/move/$method,$numOfData.csv
        done
      done
    done
  done
done
