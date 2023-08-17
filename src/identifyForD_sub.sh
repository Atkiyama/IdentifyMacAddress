#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
#線形割り当てをしないver
method=$1
numOfData=$2
delay=$3
R=$4
T=$5
I=$6
python $method,2.py $I
./identify 2 $method $R $T $I
if [ $delay -eq "0" ]; then
    java evaluation/evaluation/EvaluationForLineUp2 $delay $method $R $T $I> data/result/evaluation/move/D,oldLiner.txt
else
    java evaluation/evaluation/EvaluationForLineUp2 $delay $method $R $T $I>> data/result/evaluation/move/D,oldLiner.txt
fi
