
#コマンドライン引数 1に回帰手法(旧手法の場合はold)
method=$1
numOfData=$2
R=$3
T=$4
I=$5
#0はデバッグ用
python $method,2.py $I
#本来は2だがデバッグ用に2に変更
./identifyAllocation 2 $method $R $T $I
for n in {1..100}
do
    python assignment.py $method $n $R $T $I
done
if [ $numOfData -eq "1" ]; then
  java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I> data/result/evaluation/move/M,$method.txt
else
  java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I>> data/result/evaluation/move/M,$method.txt
fi
