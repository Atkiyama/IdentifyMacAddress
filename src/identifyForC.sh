#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
for numOfData in 10 20 30 40 50 60 70 79
do
  java processed/lineUp/LineUp $numOfData
  R=15
  T=6
  I=15
  ./identifyAverage $R $T $I &
  for method in svr linerRegression randomForest
  do
    if [ $method = "randomForest" ]; then
      ./identifyForC_sub.sh $method $numOfData $R $T $I
    else
      ./identifyForC_sub.sh $method $numOfData $R $T $I &
    fi
  done
  if [ $numOfData -eq "1" ]; then
    java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I> data/result/evaluation/move/C,old.txt
  else
    java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I>> data/result/evaluation/move/C,old.txt
  fi
./removeUsedData.sh
echo "$numOfData is done"
done
