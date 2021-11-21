#bin/bash


for method in linerRegression svr bagging
do
  for numOfData in 20
  do
    for R in 20
    do
      for T in 6
      do
        for I in 20
        do
          for n in {1..100}; do
            java RandomDelay
            python3 $method.py $I
            ./identify $R $T $I $numOfData $method >data/result/multi/move/$n.txt
          done
          if [ "$R" -eq "1" -a "$I" -eq "1" ]; then
            echo "R,T,I,score" > data/result/evaluation/move/$method,$numOfData.csv
          fi
          java evaluation/evaluation/Evaluation $R $T $I >>data/result/evaluation/move/$method,$numOfData.csv
        done
      done
    done
  done
done
