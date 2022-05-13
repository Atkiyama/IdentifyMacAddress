#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
for numOfData in 1 5 10 15 20
do
  java processed/delay/DelayForM $numOfData
  R=15
  T=6
  I=15
  ./identifyAverage $R $T $I &
  
  for method in svr linerRegression randomForest
  do
    if [ $method = "randomForest" ]; then
      ./identifyForM_sub.sh $method $numOfData $R $T $I
    else
      ./identifyForM_sub.sh $method $numOfData $R $T $I &
    fi
  done
  if [ $numOfData -eq "1" ]; then
    java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I> data/result/evaluation/move/M,old.txt
  else
    java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I>> data/result/evaluation/move/M,old.txt
  fi
./removeUsedData.sh
echo "$numOfData is done"
done
