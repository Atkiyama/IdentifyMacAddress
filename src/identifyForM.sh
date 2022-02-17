#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
./identifyForM_sub.sh $numOfData &
for numOfData in {1..20}
do
  java processed/delay/DelayForM $numOfData
  R=15
  T=6
  I=15
  python regression.py
  for method in svr bagging linerRegression
  do
    ./identify 2 $method
    if [ $numOfData -eq "1" ]; then
        java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I> data/result/evaluation/move/$method.txt
    else
        java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I>> data/result/evaluation/move/$method.txt
    fi
  done
done
