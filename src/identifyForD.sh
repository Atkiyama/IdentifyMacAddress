#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
numOfData=79
for delay in {0..30}
do
  java processed/lineUp/LineUp $numOfData $delay
  R=15
  T=6
  I=15
  python regression.py
  for method in svr bagging linerRegression randomForest
  do
    if [ $numOfData -eq "79" ]; then
      ./identify 4 $method
    else
      ./identify 2 $method
    fi
    if [ $numOfData -eq "1" ]; then
      java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I> data/result/evaluation/move/C,$method.txt
    else
      java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I>> data/result/evaluation/move/C,$method.txt
    fi
  done
  for n in {1..100}
  do
    java identifyMacAddress/identify/IdentifyStay data/capture/convert/move/$n,convertData.csv $R $T > data/result/multi/move/old/$n.txt
  done
  if [ $numOfData -eq "1" ]; then
    java evaluation/evaluation/Evaluation100Old $R $T $numOfData > data/result/evaluation/move/C,old.txt
  else
    java evaluation/evaluation/Evaluation100Old $R $T $numOfData >> data/result/evaluation/move/C,old.txt
  fi
done
