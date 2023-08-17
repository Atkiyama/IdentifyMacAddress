#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
for numOfData in 1 5 10 15 20 25 30 35 40 45 50 55 60 65 70 75 79
do
    java processed/lineUp/LineUp $numOfData
    R=15
    T=6
    I=5
    ./identifyAverage $R $T $I &
    ./identifyTimeDifference 2 timeDifference $R $T $I &
    ./identifyForC_lineUp_sub2.sh linerRegression $numOfData $R $T $I
    ./identifyForC_lineUp_sub.sh linerRegression $numOfData $R $T $I &
    ./identifyForC_lineUp_sub3.sh random $numOfData $R $T $I &
    ./identifyForC_lineUp_sub2.sh svr $numOfData $R $T $I
    for n in {1..100}
    do
        python assignment.py timeDifference $n $R $T $I
        python assignment.py old $n $R $T $I
    done
    if [ $numOfData -eq "1" ]; then
        java evaluation/evaluation/EvaluationForLineUp2 $numOfData old $R $T $I> data/result/evaluation/move/C,old.txt
        java evaluation/evaluation/EvaluationForLineUp2 $numOfData timeDifference $R $T $I> data/result/evaluation/move/C,timeDifference.txt
    else
        java evaluation/evaluation/EvaluationForLineUp2 $numOfData timeDifference $R $T $I>> data/result/evaluation/move/C,timeDifference.txt
        java evaluation/evaluation/EvaluationForLineUp2 $numOfData old $R $T $I>> data/result/evaluation/move/C,old.txt
    fi
    ./removeUsedData.sh
    echo "$numOfData is done"
done
