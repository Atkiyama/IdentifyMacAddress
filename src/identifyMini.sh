#bin/bash

numOfTime=$1
#コマンドライン引数
for method in linerRegression svr bagging
do
  for numOfData in 1 2 3 4 6 7 8 9 11 12 13 14 16 17 18 19
  do
    for R in {1..20}
    do
      for T in 6
      do
        for I in {1..20}
        do
          for n in {1..100}
          do
          ./identify $R $T $I $numOfData $n $method > data/result/multi/move/$numOfTime/$method/$numOfData/$R,$T,$I,$n.txt
          done
          if [ "$R" -eq "1" -a "$I" -eq "1" ]
          then
            java evaluation/evaluation/EvaluationMini $R $T $I $numOfTime $method $numOfData > data/result/evaluation/move/$numOfTime/$method,$numOfData.txt &
          else
            java evaluation/evaluation/EvaluationMini $R $T $I $numOfTime $method $numOfData >> data/result/evaluation/move/$numOfTime/$method,$numOfData.txt &
          fi
          if [ "$R" -eq "1" -a "$I" -eq "1" ]; then
            echo "R,T,I,score" > data/result/evaluation/move/100/$method,$numOfData.csv
          fi
          java evaluation/evaluation/EvaluationMini $R $T $I $numOfTime $method $numOfData >> data/result/evaluation/move/$numOfTime/$method,$numOfData.csv
        done
      done
    done
  done
done
