#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
#線形割り当てをするver
method=$1
numOfData=$2
delay=$3
R=$4
T=$5
I=$6
python $method,2.py $I
./identifyAllocation 2 $method $R $T $I
for n in {1..100}
do
    python assignment.py $method $n $R $T $I
done
if [ $delay -eq "0" ]; then
    java evaluation/evaluation/EvaluationForLineUp2 $delay $method $R $T $I> data/result/evaluation/move/D,$method.txt
else
    java evaluation/evaluation/EvaluationForLineUp2 $delay $method $R $T $I>> data/result/evaluation/move/D,$method.txt
fi
