
#コマンドライン引数 1に回帰手法(旧手法の場合はold)
method=$1
numOfData=$2
R=$3
T=$4
I=$5
python $method,2.py
./identify 2 $method
if [ $numOfData -eq "1" ]; then
  java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I> data/result/evaluation/move/M,$method.txt
else
  java evaluation/evaluation/EvaluationForM $numOfData $method $R $T $I>> data/result/evaluation/move/M,$method.txt
fi
