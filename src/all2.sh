#bin/bash
echo "move or stay?"
read isMove
for numOfData in 4
do
  for n in {1..100}
  do
    java convertMacAddress/ConvertMacAddress data/capture/convert/$isMove/$n,convertData.csv $numOfData data/capture/single/$isMove/
  done
  for n in {1..100}
  do
    for R in {1..20}
    do
      for T in {1..20}
      do
        java identifyMacAddress/IdentifyMacAddress data/capture/convert/$isMove/$n,convertData.csv $R $T >data/result/multi/$isMove/$n/$R,$T.txt
      done
    done
    sleep 5m
  done


  for R in {1..20}
  do
    for T in {1..20}
    do
      if [ "$R" -eq "1" -a "$T" -eq "1" ]
      then
        java evaluation/Evaluation100_2 $R $T data/result/mulit/$isMove/ >data/result/evaluation/$isMove/evaluation$numOfData _2.txt
      else
        java evaluation/Evaluation100_2 $R $T $numOfData $isMove >>data/result/evaluation/$isMove/evaluation$numOfData _2.txt
      fi
    done
  done

  java evaluation/table/EvaluationTable data/result/evaluation/$isMove/evaluation$numOfData _2.txt data/result/table/$isMove/evaluationTable$numOfData _2.csv
done
echo "評価結果をdata/result/evaluation.txtに保存しました"
echo "評価結果を表形式でdata/result/以下に保存しました"
