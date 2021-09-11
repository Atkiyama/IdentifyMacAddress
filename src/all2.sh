#bin/bash
echo "move or stay?"
read isMove
for numOfData in 5 10 15 20
do
  for n in {1..100}
  do
    java convertMacAddress/ConvertMacAddress data/capture/$isMove/convert/$n,convertData.csv $numOfData
  done
  ./IdentifyMacAddress.sh
  for R in {1..20}
  do
    for T in {1..20}
    do
      if [ "$R" -eq "1" -a "$T" -eq "1" ]
      then
        java evaluation/Evaluation100_2 $R $T  >data/result/evaluation$numOfData _2.txt
      else
        java evaluation/Evaluation100_2 $R $T  >>data/result/evaluation$numOfData _2.txt
      fi
    done
  done

  java evaluation/table/EvaluationTable data/result/evaluation$numOfData _2.txt data/result/table/evaluationTable$numOfData _2.csv
done
  ./graph2.sh
echo "評価結果をdata/result/evaluation.txtに保存しました"
echo "評価結果を表形式でdata/result/以下に保存しました"
