#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
method=$1
numOfData=$2
R=$3
T=$4
I=$5
#python $method,2.py $I
./identify 2 $method $R $T $I
if [ $numOfData -eq "1" ]; then
  java evaluation/evaluation/EvaluationForLineUp2 $numOfData $method $R $T $I> data/result/evaluation/move/C,oldLiner.txt
else
  java evaluation/evaluation/EvaluationForLineUp2 $numOfData $method $R $T $I>> data/result/evaluation/move/C,oldLiner.txt
fi
