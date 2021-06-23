#bin/bash
for numOfData in 5 10 15 20
do
  for n in {1..100}
  do
    java convertMacAddress/ConvertMacAddress data/capture/convert/$n,convertData.csv $numOfData
  done
  ./IdentifyMacAddress.sh
  for R in {1..20}
  do
    for T in {1..20}
    do
      if [ "$R" -eq "1" -a "$T" -eq "1" ]
      then
        java evaluation/Evaluation100 $R $T hoge >data/result/evaluation$numOfData.txt
      else
        java evaluation/Evaluation100 $R $T hoge >>data/result/evaluation$numOfData.txt
      fi
    done
  done

  java evaluation/table/EvaluationTable data/result/evaluation$numOfData.txt data/result/evaluationTable$numOfData.csv
done

echo "評価結果をdata/result/evaluation.txtに保存しました"
echo "評価結果を表形式でdata/result/以下に保存しました"
