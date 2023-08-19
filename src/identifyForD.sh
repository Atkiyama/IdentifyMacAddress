#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
numOfData=50
R=15
T=6
I=5
for delay in 0 100 200 300  350
do
    java processed/lineUp/LineUp $numOfData $delay
    for n in {1..100}
    do
        java identifyMacAddress/identify/IdentifyStay $n $R $I >data/result/multi/move/stay/$n/$R,$T,$I.txt &
    done
    ./identifyAverage $R $T $I &
    ./identifyTimeDifference 2 timeDifference $R $T $I &
    ./identifyForD_sub2.sh linerRegression $numOfData $delay $R $T $I
    ./identifyForD_sub.sh linerRegression $numOfData $delay $R $T $I &
    ./identifyForD_sub2.sh svr $numOfData $delay $R $T $I
    #./identifyForD_sub3.sh random $numOfData $delay $R $T $I
    for n in {1..100}
    do
        python assignment.py timeDifference $n $R $T $I
        python assignment.py old $n $R $T $I
    done
    if [ $delay -eq "0" ]; then
        java evaluation/evaluation/EvaluationForLineUp2 $delay old $R $T $I> data/result/evaluation/move/D,old.txt
        java evaluation/evaluation/EvaluationForLineUp2 $delay timeDifference $R $T $I> data/result/evaluation/move/D,timeDifference.txt
        #java evaluation/evaluation/EvaluationForLineUp2 $delay false $R $T $I> data/result/evaluation/move/D,false.txt
    else
        java evaluation/evaluation/EvaluationForLineUp2 $delay timeDifference $R $T $I>> data/result/evaluation/move/D,timeDifference.txt
        java evaluation/evaluation/EvaluationForLineUp2 $delay old $R $T $I>> data/result/evaluation/move/D,old.txt
        #java evaluation/evaluation/EvaluationForLineUp2 $delay false $R $T $I>> data/result/evaluation/move/D,false.txt
        
    fi
    ./removeUsedData.sh
    echo "$delay is done"
done
