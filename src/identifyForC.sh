#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
numOfData=79
for numOfData in {1..79}
do
  java processed/lineUp/LineUp $numOfData
  R=15
  T=6
  I=15
  for method in bagging svr linerRegression randomForest
  do
    if [ $method = "randomForest" ]; then
      ./identifyForD_sub.sh $method $numOfData $R $T $I
    else
      ./identifyForD_sub.sh $method $numOfData $R $T $I &
    fi
  done
  for n in {1..100}
  do
    if [ $numOfData -eq "79" ]; then
      java identifyMacAddress/identify/IdentifyStay data/capture/convert/move/0,convertData.csv $R $T > data/result/multi/move/old/$n/$R,$T,$I.txt
    else
      java identifyMacAddress/identify/IdentifyStay data/capture/convert/move/$n,convertData.csv $R $T > data/result/multi/move/old/$n/$R,$T,$I.txt
    fi
  done
  if [ $numOfData -eq "1" ]; then
    java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I> data/result/evaluation/move/C,old.txt
  elif [ "$numOfData" -eq "$79" ]
    then
    java evaluation/evaluation/EvaluationForMSingle $numOfData old $R $T $I>> data/result/evaluation/move/C,old.txt
  else
    java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I>> data/result/evaluation/move/C,old.txt
  fi
./removeUsedData.sh
done
