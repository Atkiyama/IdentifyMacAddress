#!/bin/bash
echo "move or stay?"
read isMove
echo "evaluation.Evaluation.javaを使用して精度を評価します"
for R in {1..20}
do
  for T in {1..20}
  do
    if [ "$R" -eq "1" -a "$T" -eq "1" ]
    then
      java evaluation/Evaluation100_2 $R $T 4 $isMove >data/result/evaluation/$isMove/evaluation4_2.txt
    else
      java evaluation/Evaluation100_2 $R $T 4 $isMove >>data/result/evaluation/$isMove/evaluation4_2.txt
    fi
  done
done

java evaluation/table/EvaluationTable data/result/evaluation/$isMove/evaluation4_2.txt data/result/table/$isMove/evaluationTable4_2.csv
echo "評価結果をdata/result/evaluation.txtに保存しました"
echo "評価結果を表形式でdata/result/evaluationtable.csvに保存しました"
