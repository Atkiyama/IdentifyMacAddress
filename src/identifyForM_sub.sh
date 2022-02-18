#bin/bash

numOfData=$1
R=15
T=6
I=15
method="old"
#コマンドライン引数 1に回帰手法(旧手法の場合はold)
for n in {1..100}
do
    java identifyMacAddress/identify/IdentifyStay data/capture/convert/move/convert$n.csv $R $T > data/result/multi/move/$method/$n.txt
done
if [ $numOfData -eq "1" ]; then
    java evaluation/evaluation/Evaluation100Old $R $T $numOfData > data/result/evaluation/move/$method.txt
else
    java evaluation/evaluation/Evaluation100Old $R $T $numOfData >> data/result/evaluation/move/$method.txt
fi

