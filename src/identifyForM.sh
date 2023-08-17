#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
for numOfData in {1..20}
do
    java processed/delay/DelayForM $numOfData
    R=15
    T=6
    I=5
    ./identifyAverage $R $T $I &
    #./identifyDistance $R $T $I &
    for method in linerRegression svr
    do
        ./identifyForM_sub2.sh $method $numOfData $R $T $I &
    done
    ./identifyTimeDifference 2 timeDifference $R $T $I
    ./identifyForM_sub3.sh $method $numOfData $R $T $I
    ./identify 2 linerRegression $R $T $I
    if [ $numOfData -eq "1" ]; then
        java evaluation/evaluation/EvaluationForM $numOfData linerRegression $R $T $I> data/result/evaluation/move/M,oldLiner.txt
    else
        java evaluation/evaluation/EvaluationForM $numOfData linerRegression $R $T $I>> data/result/evaluation/move/M,oldLiner.txt
    fi
    
    for n in {1..100}
    do
        python assignment.py timeDifference $n $R $T $I
        python assignment.py old $n $R $T $I
    done
    if [ $numOfData -eq "1" ]; then
        java evaluation/evaluation/EvaluationForM $numOfData timeDifference $R $T $I> data/result/evaluation/move/M,timeDifference.txt
    else
        java evaluation/evaluation/EvaluationForM $numOfData timeDifference $R $T $I>> data/result/evaluation/move/M,timeDifference.txt
    fi
    
    if [ $numOfData -eq "1" ]; then
        java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I> data/result/evaluation/move/M,old.txt
        #java evaluation/evaluation/EvaluationForM $numOfData distance $R $T $I> data/result/evaluation/move/M,distance.txt
    else
        java evaluation/evaluation/EvaluationForM $numOfData old $R $T $I>> data/result/evaluation/move/M,old.txt
        #java evaluation/evaluation/EvaluationForM $numOfData distance $R $T $I>> data/result/evaluation/move/M,distance.txt
    fi
    ./removeUsedData.sh
    echo "$numOfData is done"
done
