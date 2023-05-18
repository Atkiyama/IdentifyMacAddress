#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
for numOfData in {1..79}
do
  java processed/lineUp/LineUp $numOfData
  R=5
  T=6
  I=10
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
    java evaluation/evaluation/EvaluationForLineUp2 $numOfData old $R $T $I> data/result/evaluation/move/C,old.txt
  else
    java evaluation/evaluation/EvaluationForLineUp2 $numOfData old $R $T $I>> data/result/evaluation/move/C,old.txt
  fi
./removeUsedData.sh
echo "$numOfData is done"
done
