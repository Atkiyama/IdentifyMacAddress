#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
for numOfData in {1..20}
do
  java processed/delay/DelayForM $numOfData
  R=15
  T=6
  I=15
  python regression.py
  for method in randomForest svr bagging linerRegression
  do
    ./identify 2 $method
    if [ $numOfData -eq "1" ]; then
      java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I> data/result/evaluation/move/$method.txt
    else
      java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I>> data/result/evaluation/move/$method.txt
    fi
  done
  for n in {1..100}
  do
    java identifyMacAddress/identify/IdentifyStay data/capture/convert/move/convert$n.csv $R $T > data/result/multi/move/old/$n.txt
  done
  if [ $numOfData -eq "1" ]; then
    java evaluation/evaluation/Evaluation100Old $R $T $numOfData > data/result/evaluation/move/old.txt
  else
    java evaluation/evaluation/Evaluation100Old $R $T $numOfData >> data/result/evaluation/move/old.txt
  fi
done
