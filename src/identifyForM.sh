#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
for numOfData in {1..20}
do
    java processed/delay/DelayForM $numOfData
    R=15
    T=6
    I=5
    #旧手法
    
    
    #./identifyDistance $R $T $I &
    #提案手法
    for method in linerRegression svr
    do
        ./identifyForM_sub2.sh $method $numOfData $R $T $I &
    done
    for n in {1..100}
    do
        java identifyMacAddress/identify/IdentifyStay $n $R $I >data/result/multi/move/stay/$n/$R,$T,$I.txt &
    done
    #平均手法
    ./identifyAverage $R $T $I
    #時間差手法
    ./identifyTimeDifference 2 timeDifference $R $T $I
    
    
    #旧手法の評価
    if [ $numOfData -eq "1" ]; then
        java evaluation/evaluation/EvaluationForM $numOfData stay $R $T $I> data/result/evaluation/move/M,stay.txt
    else
        java evaluation/evaluation/EvaluationForM $numOfData stay $R $T $I>> data/result/evaluation/move/M,stay.txt
    fi
    
    #時間差と平均の評価
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
    
    #割り当てなしの線形回帰
    ./identify 2 linerRegression $R $T $I
    #割り当てなし線形回帰の評価
    if [ $numOfData -eq "1" ]; then
        java evaluation/evaluation/EvaluationForM $numOfData linerRegression $R $T $I> data/result/evaluation/move/M,oldLiner.txt
    else
        java evaluation/evaluation/EvaluationForM $numOfData linerRegression $R $T $I>> data/result/evaluation/move/M,oldLiner.txt
    fi
    
    ./removeUsedData.sh
    echo "$numOfData is done"
done
