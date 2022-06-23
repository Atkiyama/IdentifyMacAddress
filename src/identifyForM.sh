#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
for numOfData in {1..20}
do
  java processed/delay/DelayForM $numOfData
  R=15
  T=6
  I=15
  ./identifyAverage $R $T $I &
  ./identifyDistance $R $T $I &
  for method in approximate svr linerRegression randomForest
  do
    if [ $method = "randomForest" ]; then
      ./identifyForM_sub.sh $method $numOfData $R $T $I
    else
      ./identifyForM_sub.sh $method $numOfData $R $T $I &
    fi
  done
  if [ $numOfData -eq "1" ]; then
    java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I> data/result/evaluation/move/M,old.txt
    java evaluation/evaluation/EvaluationForM $numOfData distance $R $T $I> data/result/evaluation/move/M,distance.txt
  else
    java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I>> data/result/evaluation/move/M,old.txt
    java evaluation/evaluation/EvaluationForM $numOfData distance $R $T $I>> data/result/evaluation/move/M,distance.txt
  fi
#./removeUsedData.sh
echo "$numOfData is done"
done
