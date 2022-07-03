#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
numOfData=50
for delay in 0 50 100 150 200 250 300 350
do
  java processed/lineUp/LineUp $numOfData $delay
  R=15
  T=6
  I=5
  #bagginを一旦カット
  ./identifyDistance $R $T $I &
  ./identifyAverage $R $T $I &
  for method in svr linerRegression randomForest
  do
    if [ $method = "randomForest" ]; then
      ./identifyForD_sub.sh $method $numOfData $delay $R $T $I
    else
      ./identifyForD_sub.sh $method $numOfData $delay $R $T $I &
    fi
  done
  ./identifyFalse $R $T $I
  if [ $delay -eq "0" ]; then
    java evaluation/evaluation/EvaluationForLineUp2 $delay old $R $T $I> data/result/evaluation/move/D,old.txt
    java evaluation/evaluation/EvaluationForLineUp2 $delay distance $R $T $I> data/result/evaluation/move/D,distance.txt
    java evaluation/evaluation/EvaluationForLineUp2 $delay false $R $T $I> data/result/evaluation/move/D,false.txt
  else
    java evaluation/evaluation/EvaluationForLineUp2 $delay distance $R $T $I>> data/result/evaluation/move/D,distance.txt
    java evaluation/evaluation/EvaluationForLineUp2 $delay old $R $T $I>> data/result/evaluation/move/D,old.txt
    java evaluation/evaluation/EvaluationForLineUp2 $delay false $R $T $I>> data/result/evaluation/move/D,false.txt

  fi
  #./removeUsedData.sh
  echo "$delay is done"
done
