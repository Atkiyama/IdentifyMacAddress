#bin/bash

for numOfData in 5 10 15 20
do
  for n in {1..100}
  do
    java convertMacAddress/ConvertMacAddress data/capture/convert/$numOfData/$n,convertData.csv $numOfData
  done
done


for numOfData in 5 10 15 20
do
  for n in {1..100}
  do
    for R in {1..20}
    do
      for T in {1..20}
      do
        java identifyMacAddress/identify/IdentifyStay data/capture/convert/$numOfData/$n,convertData.csv $R $T >data/result/multi/$numOfData/$n/$R,$T.txt
      done
    done
    sleep 5m
  done
done

echo "評価結果をdata/result/evaluation.txtに保存しました"
echo "評価結果を表形式でdata/result/以下に保存しました"
