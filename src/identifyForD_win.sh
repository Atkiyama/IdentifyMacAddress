#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
numOfData=79
for delay in 310 320 330 340 350
do
  java processed/lineUp/LineUp $numOfData $delay
  R=15
  T=6
  I=15
  #bagginを一旦カット
  ./identifyAverage $R $T $I &
  for method in svr linerRegression randomForest
  do
    if [ $method = "randomForest" ]; then
      ./identifyForD_sub.sh $method $numOfData $delay $R $T $I
    else
      ./identifyForD_sub.sh $method $numOfData $delay $R $T $I &
    fi
  done

  if [ $delay -eq "0" ]; then
    java evaluation/evaluation/EvaluationForLineUp2 $delay old $R $T $I> data/result/evaluation/move/D,old.txt
  else
    java evaluation/evaluation/EvaluationForLineUp2 $delay old $R $T $I>> data/result/evaluation/move/D,old.txt
  fi
  ./removeUsedData.sh
  echo "$delay is done"
done
