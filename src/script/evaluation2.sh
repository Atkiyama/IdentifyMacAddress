#bin/bash

#コマンドライン引数
numOfData = $1
for R in {1..20}
do
  for T in {1..20}
  do
    for P in {1..20}
    do
      if [ "$R" -eq "1" -a "$T" -eq "1" -a "$P" -eq "1" ]
      then
        java evaluation/EvaluationMove $R $T $P $numOfData move >data/result/evaluation/move/evaluation$numOfData .txt
      else
        java evaluation/EvaluationMove $R $T $p $numOfData move >>data/result/evaluation/move/evaluation$numOfData .txt
      fi
    done
  done
done
