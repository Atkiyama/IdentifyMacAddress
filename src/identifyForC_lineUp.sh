#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
for numOfData in 1 5 10 15 20 25 30 35 40 45 50 55 60 70 75 79
do
  java processed/lineUp/LineUp $numOfData
  R=15
  T=6
  I=15
  ./identifyAverage $R $T $I &
  ./identifyDistance $R $T $I &
  for method in svr linerRegression randomForest
  do
    if [ $method = "randomForest" ]; then
      ./identifyForC_lineUp_sub.sh $method $numOfData $R $T $I
    else
      ./identifyForC_lineUp_sub.sh $method $numOfData $R $T $I &
    fi
  done
  if [ $numOfData -eq "1" ]; then
    java evaluation/evaluation/EvaluationForLineUp2 $numOfData old $R $T $I> data/result/evaluation/move/C,old.txt
    java evaluation/evaluation/EvaluationForLineUp2 $numOfData distance $R $T $I> data/result/evaluation/move/C,dsitance.txt
  else
    java evaluation/evaluation/EvaluationForLineUp2 $numOfData distance $R $T $I>> data/result/evaluation/move/C,dsitance.txt
    java evaluation/evaluation/EvaluationForLineUp2 $numOfData old $R $T $I>> data/result/evaluation/move/C,old.txt
  fi
./removeUsedData.sh
echo "$numOfData is done"
done
