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
          for n in {1..100}
          do
            ./identify $R $T $I $numOfData $n $method >data/result/multi/move/$n.txt
            if [ "$R" -eq "1" -a "$I" -eq "1" ]; then
              echo "R,T,I,score" > data/result/evaluation/move/$numOfTime/$method,$numOfData.csv
            fi
            java evaluation/evaluation/Evaluation $R $T $I $numOfTime $n.txt >> data/result/evaluation/move/$numOfTime/$method,$numOfData.csv
          done
        done
      done
    done
  done
done
