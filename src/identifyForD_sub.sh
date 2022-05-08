#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
method=$1
numOfData=$2
delay=$3
R=$4
T=$5
I=$6
python $method,2.py
./identify 2 $method
if [ $delay -eq "0" ]; then
  java evaluation/evaluation/EvaluationForM $delay $method $R $T $I> data/result/evaluation/move/D,$method.txt
else
  java evaluation/evaluation/EvaluationForM $delay $method $R $T $I>> data/result/evaluation/move/D,$method.txt
fi
