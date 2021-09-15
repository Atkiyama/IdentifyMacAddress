#bin/bash

for numOfData in 5 10 15 20
do

#  for n in {1..100}
#  do
#    java convertMacAddress/ConvertMacAddress data/capture/convert/stay/$numOfData/$n,convertData.csv $numOfData  data/capture/single/stay/
#  done


  for n in {1..100}
  do
    for R in {1..20}
    do
      for T in {1..20}
      do
        java identifyMacAddress/identify/IdentifyStay data/capture/convert/stay/$numOfData/$n,convertData.csv $R $T >data/result/multi/stay/$numOfData/$n/$R,$T.txt
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
        java evaluation/Evaluation100_2 $R $T $numOfData stay >data/result/evaluation/stay/evaluation$numOfData _2.txt
      else
        java evaluation/Evaluation100_2 $R $T $numOfData stay >>data/result/evaluation/stay/evaluation$numOfData _2.txt
      fi
    done
  done

  java evaluation/table/EvaluationTable data/result/evaluation/stay/evaluation$numOfData _2.txt data/result/table/stay/evaluationTable$numOfData _2.csv
done
echo "評価結果をdata/result/evaluation.txtに保存しました"
echo "評価結果を表形式でdata/result/以下に保存しました"
