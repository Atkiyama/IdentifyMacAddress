#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
numOfData=79
for delay in {151..250}
do
  java processed/lineUp/LineUp $numOfData $delay
  R=15
  T=6
  I=15
  for method in bagging svr linerRegression randomForest
  do
    if [ $method = "randomForest" ]; then
      ./identifyForD_sub.sh $method $numOfData $delay $R $T $I
    else
      ./identifyForD_sub.sh $method $numOfData $delay $R $T $I &
    fi
  done
  for n in {1..100}
  do
    java identifyMacAddress/identify/IdentifyStay data/capture/convert/move/$n,convertData.csv $R $T > data/result/multi/move/old/$n/$R,$T,$I.txt
  done
  if [ $delay -eq "0" ]; then
    java evaluation/evaluation/EvaluationForM $delay old $R $T $I> data/result/evaluation/move/D,old.txt
  else
    java evaluation/evaluation/EvaluationForM $delay old $R $T $I>> data/result/evaluation/move/D,old.txt
  fi
  ./removeUsedData.sh
done
