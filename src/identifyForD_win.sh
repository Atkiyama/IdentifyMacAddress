#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
numOfData=50
for delay in 0 10 20 30 40 50 60 70 80 90 100 110 120 130 140 150 160 170 180 190 200 210 220 230 240 250 260 270 280 290 300 310 320 330 340 350
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
