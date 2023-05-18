#bin/bash
R=15
T=6
I=5
#コマンドライン引数 1に回帰手法(旧手法の場合はold)
java processed/extract/Extract $I
for numOfData in 2
do
    java processed/delay/StaticDelayForM $numOfData
    #./identifyAverage $R $T $I &
    #./identifyDistance $R $T $I &
    method="svr"
    #linerRegression randomForest
    python $method,2.py $I 0
    #本来は2だがデバッグ用に2に変更
    ./identifyAllocation 4 $method $R $T $I
    python assignment.py $method 0 $R $T $I
    if [ $numOfData -eq "20" ]; then
        java evaluation/evaluation/EvaluationForMSingle $numOfData $method $R $T $I
        #> data/result/evaluation/move/M,$method.txt
    else
        java evaluation/evaluation/EvaluationForMSingle $numOfData $method $R $T $I 
        #>> data/result/evaluation/move/M,$method.txt
    fi
    # if [ $numOfData -eq "1" ]; then
    #   java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I> data/result/evaluation/move/M,old.txt
    #   java evaluation/evaluation/EvaluationForM $numOfData distance $R $T $I> data/result/evaluation/move/M,distance.txt
    # else
    #   java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I>> data/result/evaluation/move/M,old.txt
    #   java evaluation/evaluation/EvaluationForM $numOfData distance $R $T $I>> data/result/evaluation/move/M,distance.txt
    # fi
    #./removeUsedData.sh
    echo "$numOfData is done"
done
