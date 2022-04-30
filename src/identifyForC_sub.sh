#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
method=$1
numOfData=$2
R=$3
T=$4
I=$5
python $method,2.py
if [ $numOfData -eq "79" ]; then
  ./identify 4 $method
else
  ./identify 2 $method
fi
if [ $numOfData -eq "1" ]; then
  java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I> data/result/evaluation/move/C,$method.txt
elif [ "$numOfData" -eq "79" ]
then
  java evaluation/evaluation/EvaluationForMSingle $numOfData $method $R $T $I>> data/result/evaluation/move/C,$method.txt
else
  java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I>> data/result/evaluation/move/C,$method.txt
fi
