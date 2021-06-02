#!/bin/bash

echo "evaluation.Evaluation.javaを使用して精度を評価します"
for R in {0..20}
do
  for T in {0..10}
  do
    if [ "$R" -eq "0" -a "$T" -eq "0" ]
    then
      java evaluation/Evaluation $R $T 1 >data/result/evaluation.txt
    else
      java evaluation/Evaluation $R $T 1 >>data/result/evaluation.txt
    fi
  done
done

for R in {0..20}
do
  for T in {0..10}
  do
      java evaluation/Evaluation $R $T >>data/result/evaluation.txt
  done
done

java evaluation/table/EvaluationTable
echo "評価結果をdata/result/evaluation.txtに保存しました"
echo "評価結果を表形式でdata/result/evaluationtable.csvに保存しました"
