#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
method=random
numOfData=$2
R=$3
T=$4
I=$5

./identifyRandom 2 $method $R $T $I
for n in {1..100}
do
    python assignment.py $method $n $R $T $I
done
if [ $numOfData -eq "1" ]; then
    java evaluation/evaluation/EvaluationForLineUp2 $numOfData $method $R $T $I> data/result/evaluation/move/C,$method.txt
else
    java evaluation/evaluation/EvaluationForLineUp2 $numOfData $method $R $T $I>> data/result/evaluation/move/C,$method.txt
fi
