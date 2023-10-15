
#回帰を用いる場合の線形割り当てがないver
#コマンドライン引数 1に回帰手法(旧手法の場合はold)
method=$1
numOfData=$2
R=$3
T=$4
I=$5
#0はデバッグ用
python $method,2.py $I
#本来は2だがデバッグ用に2に変更
./identify 2 $method $R $T $I
if [ $numOfData -eq "1" ]; then
    java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I> data/result/evaluation/move/M,$method.txt
else
    java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I>> data/result/evaluation/move/M,$method.txt
fi
