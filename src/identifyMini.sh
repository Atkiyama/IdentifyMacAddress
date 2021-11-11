#bin/bash

numOfTime=$1
#コマンドライン引数
for method in linerRegression svr bagging
do
  for numOfData in 5 10 15 20
  do
    for R in {1..20}
    do
      for T in 6
      do
        for I in {1..20}
        do
          #for n in {1..100}
          # do
          #   forCount='ps|grep identify|wc -l'
          #   count=$(eval $forCount)
          #   while [$count -gt 20 ]
          #   do
          #     forCount='ps|grep identify|wc -l'
          #     count=$(eval $forCount)
          #   done
          # ./identify $R $T $I $numOfData $n $method > data/result/multi/move/$numOfTime/$method/$numOfData/$R,$T,$I,$n.txt
          # done
          # if [ "$R" -eq "1" -a "$I" -eq "1" ]
          # then
          #   java evaluation/evaluation/EvaluationMini $R $T $I $numOfTime $method $numOfData > data/result/evaluation/move/$numOfTime/$method,$numOfData.txt &
          # else
          #   java evaluation/evaluation/EvaluationMini $R $T $I $numOfTime $method $numOfData >> data/result/evaluation/move/$numOfTime/$method,$numOfData.txt &
          # fi
          if [ "$R" -eq "1" -a "$I" -eq "1" ]; then
            echo "R,T,I,score" > data/result/evaluation/move/100/$method,$numOfData.csv
          fi
          java evaluation/evaluation/EvaluationMini $R $T $I $numOfTime $method $numOfData >> data/result/evaluation/move/$numOfTime/$method,$numOfData.csv
        done
      done
    done
  done
done
