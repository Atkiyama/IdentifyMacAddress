#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
numOfData=50
R=15
T=6
I=5
for delay in 0 10 20 30 40 50 60 70 80 90 100 110 120 130 140 150 160 170 180 190 200 210 220 230 240 250 260 270 280 290 300 310 320 330 340 350
do
    java processed/lineUp/LineUp $numOfData $delay
    ./identifyAverage $R $T $I &
    ./identifyTimeDifference 2 timeDifference $R $T $I &
    ./identifyForD_sub2.sh linerRegression $numOfData $delay $R $T $I
    ./identifyForD_sub.sh linerRegression $numOfData $delay $R $T $I &
    ./identifyForD_sub2.sh svr $numOfData $delay $R $T $I
    ./identifyForD_sub3.sh random $numOfData $delay $R $T $I
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
