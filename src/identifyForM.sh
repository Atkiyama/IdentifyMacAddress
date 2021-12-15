#bin/bash

#コマンドライン引数 1に回帰手法(旧手法の場合はold)
for numOfData in {1..20}
do
  java delay/DelayForM $numOfData
  R=5
  T=6
  I=15
  python regression.py 15
  for method in old svr bagging linerRegression
  do

    python regression.py
    for n in {1..100}
    do
      if [ $method = "old" ]; then
        java identifyMacAddress/identify/IdentifyStay data/capture/convert/move/convert$n.csv $R $T > data/result/multi/move/$method/$n.txt
      else
        ./identify $R $T $I $numOfData $n $method > data/result/multi/move/$method/$n.txt
      fi
    done
    if [ $numOfData -eq "1" ]; then
      if [ $method = "old" ]; then
        java evaluation/evaluation/Evaluation100Old $R $T $numOfData > data/result/evaluation/move/$method.txt
      else
        java evaluation/evaluation/EvaluationForM $numOfData $method > data/result/evaluation/move/$method.txt
      fi
    else
      if [ $method = "old" ]; then
        java evaluation/evaluation/Evaluation100Old $R $T $numOfData >> data/result/evaluation/move/$method.txt
      else
        java evaluation/evaluation/EvaluationForM $numOfData $method >> data/result/evaluation/move/$method.txt
      fi
    fi

  done
done
