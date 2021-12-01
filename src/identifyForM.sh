#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
for numOfData in {1..20}
do
  java RandomDelay $numOfData
  python linerRegression.py
  python svr.py
  python bagging.py
  for method in linerRegression svr bagging old
  do
    for R in 10
    do
      for T in 6
      do
        for I in 10
        do
          for n in {1..100}
          do
            if [ $method = "old" ]; then
              java identifyMacAddress/identify/IdentifyMove > data/result/multi/move/$method/$n.txt
            else
              ./identify $R $T $I $numOfData $n $method > data/result/multi/move/$method/$n.txt
            fi
          done
          if [ "$R" -eq "1" -a "$I" -eq "1" ]; then
            if [ $method = "old" ]; then
              java evaluation/evaluation/Evaluation100Old $R $T $numOfData > data/result/evaluation/move/$numOfTime/$method.txt
            else
              java evaluation/evaluation/EvaluationForM $numOfData $method > data/result/evaluation/move/$numOfTime/$method.txt
            fi
          else
            if [ $method = "old" ]; then
              java evaluation/evaluation/Evaluation100Old $R $T $numOfData >> data/result/evaluation/move/$numOfTime/$method.txt
            else
                java evaluation/evaluation/EvaluationForM $numOfData $method >> data/result/evaluation/move/$numOfTime/$method.txt
            fi
          fi
        done
      done
    done
  done
done
