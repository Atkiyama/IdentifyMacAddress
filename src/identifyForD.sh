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
    ./identify 2 $method
    if [ $numOfData -eq "1" ]; then
      java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I> data/result/evaluation/move/D,$method.txt
    else
      java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I>> data/result/evaluation/move/D,$method.txt
    fi
  done
  for n in {1..100}
  do
    java identifyMacAddress/identify/IdentifyStay data/capture/convert/move/$n,convertData.csv $R $T > data/result/multi/move/old/$n.txt
  done
  if [ $numOfData -eq "1" ]; then
    java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I> data/result/evaluation/move/D,old.txt
  elif [ "$numOfData" -eq "$79" ]
    then
    java evaluation/evaluation/EvaluationForMSingle $numOfData old $R $T $I>> data/result/evaluation/move/D,old.txt
  else
    java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I>> data/result/evaluation/move/D,old.txt
  fi
done
